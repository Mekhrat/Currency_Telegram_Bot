package com.example.telebotspring;

import com.example.telebotspring.entities.Currency;
import com.example.telebotspring.entities.Users;
import com.example.telebotspring.services.ChangesService;
import com.example.telebotspring.services.CurrencyService;
import com.example.telebotspring.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private ChangesService changesService;

    @Autowired
    private UsersService usersService;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    private final static String[] currencyNames = {"rur", "usd", "cny", "eur", "uah"};


    @Scheduled(fixedRate = 1000 * 60 * 60 * 24) // one day
    private void getInfo() {
        for (String name : currencyNames)
            changesService.setElements(name);
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 12) //2 раза за день будет проверять
    private void checkChanges() {
        String text = "";
        for (String name : currencyNames) {
            switch (name) {
                case "rur":
                    text = "Рубля\uD83C\uDDF7\uD83C\uDDFA";
                    break;
                case "usd":
                    text = "Доллара\uD83C\uDDFA\uD83C\uDDF2";
                    break;
                case "eur":
                    text = "Евро\uD83C\uDDEA\uD83C\uDDFA";
                    break;
                case "cny":
                    text = "Юань\uD83C\uDDE8\uD83C\uDDF3";
                    break;
                case "uah":
                    text = "Гривинь\uD83C\uDDFA\uD83C\uDDE6";
                    break;
            }
            Double percent = changesService.getPercent(name);
            if (percent > 10) {  //При изменении больше 10% бот отправит сообшение
                String count = String.format("%.2f", percent);
                List<Users> users = usersService.getUsers();
                for (Users user : users) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Внимание!!!\nКурс " + text + " изменился на " + count + "% !!!" );
                    sendMessage.setChatId(user.getChatId());
                    sendMessage.setParseMode(ParseMode.MARKDOWN);

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (usersService.getUser(message.getChatId().toString()) == null) {
                usersService.addUser(new Users(null, message.getChatId().toString()));
            }
            if (message.getText().equals("/start") || message.getText().equals("CANCEL")) {
                startHandle(message);
            } else if (message.getText().equals("RUS")) {
                handleMessage(message, "Рубля\uD83C\uDDF7\uD83C\uDDFA", "rur");
            } else if (message.getText().equals("USD")) {
                handleMessage(message, "Доллара\uD83C\uDDFA\uD83C\uDDF2", "usd");
            } else if (message.getText().equals("EUR")) {
                handleMessage(message, "Евро\uD83C\uDDEA\uD83C\uDDFA", "eur");
            } else if (message.getText().equals("CNY")) {
                handleMessage(message, "Юань\uD83C\uDDE8\uD83C\uDDF3", "cny");
            } else if (message.getText().equals("UAH")) {
                handleMessage(message, "Гривинь\uD83C\uDDFA\uD83C\uDDE6", "uah");
            } else if (message.getText().equals("MORE")) {
                more(message);
            } else {
                try {
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Ошибка!\nВведите корректную валюту!").build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void startHandle(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите валюту:");
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> list = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow(); ///
        KeyboardButton k1 = new KeyboardButton();
        k1.setText("USD");
        KeyboardButton k2 = new KeyboardButton();
        k2.setText("RUS");
        KeyboardButton k3 = new KeyboardButton();
        k3.setText("EUR");
        KeyboardButton k4 = new KeyboardButton(); ///
        k4.setText("MORE");
        keyboardRow.add(k1);
        keyboardRow.add(k2);
        keyboardRow.add(k3);
        keyboardRow1.add(k4);
        list.add(keyboardRow);
        list.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(list);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void more(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setText("Выберите другие валюты:");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> list = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow(); ///
        KeyboardButton k1 = new KeyboardButton();
        k1.setText("CNY");
        KeyboardButton k3 = new KeyboardButton();
        k3.setText("UAH");
        KeyboardButton k4 = new KeyboardButton(); ///
        k4.setText("CANCEL");
        keyboardRow.add(k1);
        keyboardRow.add(k3);
        keyboardRow1.add(k4);
        list.add(keyboardRow);
        list.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(list);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Message message, String s, String kod) {
        SendMessage message1 = new SendMessage();
        message1.setChatId(message.getChatId().toString());
        message1.setParseMode(ParseMode.MARKDOWN);
        String res = "Изменение курса " + s + " -> Тенге\uD83C\uDDF0\uD83C\uDDFF\n\nДата        Курс        Изменение\n";
        res += changesService.getELementsByName(kod);
        message1.setText(res);
        try {
            execute(message1);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

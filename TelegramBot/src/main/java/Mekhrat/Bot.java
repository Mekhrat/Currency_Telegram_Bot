package Mekhrat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) throws TelegramApiException {
        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

    @Override
    public String getBotUsername() {
        return "@MekhratCurrencyBot";
    }

    @Override
    public String getBotToken() {
        return "5130015579:AAGRdgNcYasR-NY_CiRXHHiglVntgRkhNcI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            Message message = update.getMessage();
            if (message.getText().equals("/start") || message.getText().equals("CANCEL")){
                startHandle(message);
            }
            else if(message.getText().equals("RUS")){
                handleMessage(message,"Рубля\uD83C\uDDF7\uD83C\uDDFA","rur");
            }
            else if(message.getText().equals("USD")){
                handleMessage(message,"Доллара\uD83C\uDDFA\uD83C\uDDF2","usd");
            }
            else if(message.getText().equals("EUR")){
                handleMessage(message,"Евро\uD83C\uDDEA\uD83C\uDDFA","eur");
            }
            else if(message.getText().equals("CNY")){
                handleMessage(message,"Юань\uD83C\uDDE8\uD83C\uDDF3","cny");
            }
            else if(message.getText().equals("UAH")){
                handleMessage(message,"Гривинь\uD83C\uDDFA\uD83C\uDDE6","uah");
            }
            else if(message.getText().equals("MORE")){
                more(message);
            }
            else{
                try {
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Ошибка!\nВведите корректную валюту!").build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void startHandle(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите валюту: ");
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

    private void more(Message message){
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
    private void handleMessage(Message message,String s,String kod) {
        SendMessage message1 = new SendMessage();
        message1.setChatId(message.getChatId().toString());
        message1.setParseMode(ParseMode.MARKDOWN);
        ArrayList<CurrencyInfo> info = getElements(kod);
        String res = "Изменение курса "+  s + " -> Тенге\uD83C\uDDF0\uD83C\uDDFF\n\nДата        Курс        Изменение\n";
        for (int i = 0; i < 10; i++) {
            String value = String.format("%.2f",info.get(i).getValue());
            String f = String.format("%.2f",Math.abs(info.get(i).getValue()-info.get(i+1).getValue()));
            res += info.get(i).getData() + "       " + value + "тг      "+(info.get(i).getValue()>info.get(i+1).getValue()?"⬆":"⬇")+f+"\n";
        }
        message1.setText(res);
        try {
            execute(message1);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static org.jsoup.nodes.Document getPage(String str) {
        String url = "https://kazkredit.com/kurs/" + str;
        Document page = null;
        try {
            page = Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    public static ArrayList<CurrencyInfo> getElements(String kurs){
        ArrayList<CurrencyInfo> currencies = new ArrayList<>();
        Document document = getPage(kurs);
        Element element = document.select("table[class=kurs-table]").first();
        Elements tr = element.select("tr");

        for (int i = 1; i < 12; i++) {
            currencies.add(new CurrencyInfo(
                    tr.get(i).select("td").get(0).text(),
                    Double.parseDouble(tr.get(i).select("td").get(1).text())));
        }
        return currencies;
    }


}

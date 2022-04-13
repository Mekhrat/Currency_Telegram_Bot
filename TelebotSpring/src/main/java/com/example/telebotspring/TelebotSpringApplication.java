package com.example.telebotspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.TelegramBotInitializer;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
@EnableScheduling
@SpringBootApplication
public class TelebotSpringApplication {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(TelebotSpringApplication.class, args);

    }
}

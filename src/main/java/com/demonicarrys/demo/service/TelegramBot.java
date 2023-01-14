package com.demonicarrys.demo.service;

import com.demonicarrys.demo.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    private void startCommandReceived(long chatId, String userName){
        String answer = "Wassup, " + userName +", nice to hear you buddy ;)";
        sendMessage(chatId, answer);
        sendMessage(chatId, "You can send me only good or bad now");
        sendMessage(chatId, "I'm bit stupid right now but someday i'll understand you better");
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        }catch (TelegramApiException e){

        }

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getFirstName();

            switch (messageText){
                case ("/start") :
                    startCommandReceived(chatId, userName);
                    break;
                case ("good") :
                    sendMessage(chatId, "Nice to hear this maan \nKeep it up");
                    break;
                case ("bad") :
                    sendMessage(chatId, "that's ok fren\nlook what i found for ya");
                    sendMessage(chatId, jokeMethod());
                    break;
                default :
                    sendMessage(chatId, "Idk how to work with this info bro");
                    break;
            }
        }
    }

    public String jokeMethod(){
        int random = (int) (Math.random() * 5);
        List<String> anekdotes = new ArrayList<>();
        anekdotes.add("У сексопатолога:\n" +
                "- Доктор! Я - гей...\n" +
                "Доктор смотрит на значок \"Единой России\" на лацкане пиджака у пациента и говорит:\n" +
                "- Да нет, дорогуша, вы - не гей... У вас все значительно хуже...");
        anekdotes.add("https://giphy.com/gifs/meme-nyan-cat-VGuAZNdkPUpEY");
        anekdotes.add("Покупатель, недовольный качеством навоза, так и не смог подобрать\n" +
                "подходящего эпитета, чтобы охарактеризовать товар.");
        anekdotes.add("Деньги надо зарабатывать кровью и потом. Кровью врагов и потом рабов.");
        anekdotes.add("https://giphy.com/gifs/funny-lol-laughing-fBG7UnBi7QtePFHEnk");
        anekdotes.add("... а хуже всего приходится программистам из Microsoft. Им, бедолагам,\n" +
                "в случае чего и обругать-то некого...");
        return anekdotes.get(random);
    }
}

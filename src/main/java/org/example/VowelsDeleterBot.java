package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class VowelsDeleterBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    public VowelsDeleterBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    String ru_vowels = new String("аеёуюэяиыоАЕЁУЮЭЯИЫО");
    @Override
    public void consume(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            //int iter = 0;
            String msg_text = new String();
            for (char ch : message_text.toCharArray()){
                if (ru_vowels.indexOf(ch) == -1){
                    msg_text += ch;
                }
            }
            if (msg_text.isEmpty()){
                msg_text = "В сообщении все буквы - гласные";
            }
            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chat_id)
                    .text(msg_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}

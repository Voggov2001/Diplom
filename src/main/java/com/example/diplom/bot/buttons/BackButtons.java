package com.example.diplom.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class BackButtons {

    private static final InlineKeyboardButton BACK_BUTTON = new InlineKeyboardButton("Назад");
    private static final InlineKeyboardButton AGAIN_BUTTON = new InlineKeyboardButton("Повторить");


    public static InlineKeyboardMarkup inlineMarkup() {
        BACK_BUTTON.setCallbackData("/back");
        AGAIN_BUTTON.setCallbackData("/repead");

        List<InlineKeyboardButton> rowNavigate = List.of(BACK_BUTTON, AGAIN_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowNavigate);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

}

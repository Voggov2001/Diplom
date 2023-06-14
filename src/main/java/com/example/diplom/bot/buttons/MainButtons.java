package com.example.diplom.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class MainButtons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Топ фильмов");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");
    private static final InlineKeyboardButton NAME_BUTTON = new InlineKeyboardButton("По названию");
    private static final InlineKeyboardButton GENRE_BUTTON = new InlineKeyboardButton("По жанру");
    private static final InlineKeyboardButton RATING_BUTTON = new InlineKeyboardButton("По рейтингу");
    private static final InlineKeyboardButton WATCH_BUTTON = new InlineKeyboardButton("Посмотреть фильм:");



    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/top");
        HELP_BUTTON.setCallbackData("/help");
        NAME_BUTTON.setCallbackData("/name");
        GENRE_BUTTON.setCallbackData("/genre");
        RATING_BUTTON.setCallbackData("/rating");
        WATCH_BUTTON.setCallbackData("/watch");


        List<InlineKeyboardButton> rowHelp = List.of(START_BUTTON, HELP_BUTTON);
        List<InlineKeyboardButton> rowInline = List.of(NAME_BUTTON, GENRE_BUTTON, RATING_BUTTON);
        List<InlineKeyboardButton> rowWatch = List.of(WATCH_BUTTON);

        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline, rowHelp, rowWatch);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

}

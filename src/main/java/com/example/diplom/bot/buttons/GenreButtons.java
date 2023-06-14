package com.example.diplom.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class GenreButtons {

    private static final InlineKeyboardButton DRAMA_BUTTON = new InlineKeyboardButton("Драма");
    private static final InlineKeyboardButton THRILLER_BUTTON = new InlineKeyboardButton("Триллер");
    private static final InlineKeyboardButton CRIME_BUTTON = new InlineKeyboardButton("Криминал");
    private static final InlineKeyboardButton DETECTIVE_BUTTON = new InlineKeyboardButton("Детектив");
    private static final InlineKeyboardButton FANTASTIC_BUTTON = new InlineKeyboardButton("Фантастика");
    private static final InlineKeyboardButton ADVENTURES_BUTTON = new InlineKeyboardButton("Приключения");
    private static final InlineKeyboardButton ACTION_MOVIE_BUTTON = new InlineKeyboardButton("Боевик");
    private static final InlineKeyboardButton COMEDY_BUTTON = new InlineKeyboardButton("Комедия");
    private static final InlineKeyboardButton HORROR_BUTTON = new InlineKeyboardButton("Ужасы");
    private static final InlineKeyboardButton WESTERN_BUTTON = new InlineKeyboardButton("Вестерн");
    private static final InlineKeyboardButton CARTOON_BUTTON = new InlineKeyboardButton("Мультфильм");
    private static final InlineKeyboardButton FAMILY_BUTTON = new InlineKeyboardButton("Семейный");


    public static InlineKeyboardMarkup inlineMarkup() {
        DRAMA_BUTTON.setCallbackData("/drama");
        THRILLER_BUTTON.setCallbackData("/thriller");
        CRIME_BUTTON.setCallbackData("/crime");
        DETECTIVE_BUTTON.setCallbackData("/detective");
        FANTASTIC_BUTTON.setCallbackData("/fantastic");
        ADVENTURES_BUTTON.setCallbackData("/adventure");
        ACTION_MOVIE_BUTTON.setCallbackData("/action_move");
        HORROR_BUTTON.setCallbackData("/horror");
        WESTERN_BUTTON.setCallbackData("/western");
        CARTOON_BUTTON.setCallbackData("/cartoon");
        FAMILY_BUTTON.setCallbackData("/family");
        COMEDY_BUTTON.setCallbackData("/comedy");


        List<InlineKeyboardButton> rowNavigate = List.of(DRAMA_BUTTON, THRILLER_BUTTON, CRIME_BUTTON, DETECTIVE_BUTTON);
        List<InlineKeyboardButton> rowNavigate2 = List.of(FANTASTIC_BUTTON, ADVENTURES_BUTTON, ACTION_MOVIE_BUTTON, HORROR_BUTTON);
        List<InlineKeyboardButton> rowNavigate3 = List.of(WESTERN_BUTTON, CARTOON_BUTTON, FAMILY_BUTTON, COMEDY_BUTTON);

        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowNavigate, rowNavigate2, rowNavigate3);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }


}

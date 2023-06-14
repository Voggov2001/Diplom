package com.example.diplom.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> COMMAND_LIST = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info"),
            new BotCommand("/name", "name search"),
            new BotCommand("/rating", "rating search"),
            new BotCommand("/genre", "genre search"),
            new BotCommand("/back", "back)"),
            new BotCommand("/repead", "repead of search")
    );

    String HELP_TEXT = "Я помогу выбрать фильм для прекрасного времяпрепровождения:)";
}

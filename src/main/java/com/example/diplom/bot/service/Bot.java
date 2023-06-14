package com.example.diplom.bot.service;

import com.example.diplom.bot.buttons.BackButtons;
import com.example.diplom.bot.buttons.BotCommands;
import com.example.diplom.bot.buttons.GenreButtons;
import com.example.diplom.bot.buttons.MainButtons;
import com.example.diplom.bot.config.BotConfig;
import com.example.diplom.bot.enums.SearchParamEnum;
import com.example.diplom.dto.db.FilmDatabaseDto;
import com.example.diplom.dto.genre.GenresDto;
import com.example.diplom.dto.name.FilmsDto;
import com.example.diplom.dto.top.TotalTopFilmsDto;
import com.example.diplom.proxy.FeignKinopoiskApi;
import com.example.diplom.service.FilmService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@Component
@Data
public class Bot extends TelegramLongPollingBot implements BotCommands {

    private final String API_TOKEN = "ce25666c-fbd9-45cb-8466-071170dad302";
    @Autowired
    private final BotConfig botConfig;
    @Autowired
    private FeignKinopoiskApi feignKinopoiskApi;
    @Autowired
    private FilmService service;
    private List<Long> startWait;

    private Map<String, List<Long>> waitCommand;

    public Bot(BotConfig botConfig, FeignKinopoiskApi feignKinopoiskApi) {
        this.botConfig = botConfig;
        waitCommand = new HashMap<>();
        waitCommand.put(SearchParamEnum.RATING.name(), new ArrayList<>());
        waitCommand.put(SearchParamEnum.NAME.name(), new ArrayList<>());
        waitCommand.put(SearchParamEnum.GENRE.name(), new ArrayList<>());
        waitCommand.put(SearchParamEnum.TOP.name(), new ArrayList<>());
        waitCommand.put(SearchParamEnum.WATCH.name(), new ArrayList<>());
        try {
            execute(new SetMyCommands(COMMAND_LIST, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        long userId = 0; //это нам понадобится позже
        String userName = null;
        String receivedMessage;

        //если получено сообщение текстом
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();
            if (waitCommand.get(SearchParamEnum.NAME.name()).contains(update.getMessage().getChatId()) && update.getMessage().hasText()) {
                searchByName(update.getMessage().getChatId(), update.getMessage().getText());
                try {
                    execute(SendMessage.builder()
                            .chatId(String.valueOf(update.getMessage().getChatId()))
                            .text("Выбирите дальнейшее действие:")
                            .replyMarkup(BackButtons.inlineMarkup())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (waitCommand.get(SearchParamEnum.RATING.name()).contains(update.getMessage().getChatId()) && update.getMessage().hasText()) {
                searchByRating(update.getMessage().getChatId(), Integer.valueOf(update.getMessage().getText()));
                try {
                    execute(SendMessage.builder()
                            .chatId(String.valueOf(update.getMessage().getChatId()))
                            .text("Выбирите дальнейшее действие:")
                            .replyMarkup(BackButtons.inlineMarkup())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (waitCommand.get(SearchParamEnum.WATCH.name()).contains(update.getMessage().getChatId()) && update.getMessage().hasText()) {
                watch(chatId, Long.valueOf(update.getMessage().getText()));
                try {
                    execute(SendMessage.builder()
                            .chatId(String.valueOf(update.getMessage().getChatId()))
                            .text("Выбирите дальнейшее действие:")
                            .replyMarkup(BackButtons.inlineMarkup())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                try {
                    botAnswerUtils(receivedMessage, chatId, userName);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            try {
                botAnswerUtils(receivedMessage, chatId, userName);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void botAnswerUtils(String receivedMessage, long chatId, String userName) throws TelegramApiException {
        switch (receivedMessage) {
            case "/start":
                startBot(chatId, userName);
                break;
            case "/help":
                sendHelpText(chatId, HELP_TEXT);
                break;
            case "/name":
                preSearchByName(chatId);
                break;
            case "/back":
                goBack(chatId);
                break;
            case "/repead":
                goRepead(chatId);
                break;
            case "/genre":
                preSearchByGenre(chatId);
                break;
            case "/rating":
                preSearchByRating(chatId);
                break;
            case "/top":
                getTop(chatId);
                break;
            case "/watch":
                preWatch(chatId);
                break;
            case "/drama":
                searchByGenre(chatId, 2L);
                break;
            case "/thriller":
                searchByGenre(chatId, 1L);
                break;
            case "/crime":
                searchByGenre(chatId, 3L);
                break;
            case "/detective":
                searchByGenre(chatId, 5L);
                break;
            case "/fantastic":
                searchByGenre(chatId, 6L);
                break;
            case "/adventure":
                searchByGenre(chatId, 7L);
                break;
            case "/action_move":
                searchByGenre(chatId, 11L);
                break;
            case "/horror":
                searchByGenre(chatId, 17L);
                break;
            case "/western":
                searchByGenre(chatId, 10L);
                break;
            case "/cartoon":
                searchByGenre(chatId, 18L);
                break;
            case "/family":
                searchByGenre(chatId, 19L);
                break;
            case "/comedy":
                searchByGenre(chatId, 13L);
                break;
            default:
                break;
        }
    }

    private void preWatch(Long chatId) {
        waitCommand.get(SearchParamEnum.WATCH.name()).add(chatId);
        SendMessage searchWord = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("Пожалуйста введите ID желаемого фильма(Целое число): ")
                .build();
        try {
            execute(searchWord);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void watch(Long chatID, Long id) {
        FilmDatabaseDto filmById = service.getFilmById(435L);
        try {
            execute(SendMessage.builder()
                    .chatId(String.valueOf(chatID))
                    .text(filmById.toString())
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void startBot(long chatId, String userName) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Приветствую, " + userName + ", я, " + getBotUsername() + ", чем могу помочь?");
        message.setReplyMarkup(MainButtons.inlineMarkup());

        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }

    private void goBack(Long chatId) {
        if (waitCommand.get(SearchParamEnum.NAME.name()).contains(chatId)) {
            waitCommand.get(SearchParamEnum.NAME.name()).remove(chatId);
        } else if (waitCommand.get(SearchParamEnum.RATING.name()).contains(chatId)) {
            waitCommand.get(SearchParamEnum.RATING.name()).remove(chatId);
        } else waitCommand.get(SearchParamEnum.GENRE.name()).remove(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Чем могу помочь?");
        message.setReplyMarkup(MainButtons.inlineMarkup());

        try {
            execute(message);
        } catch (TelegramApiException e) {
        }

    }

    private void goRepead(Long chatId) {
        if (waitCommand.get(SearchParamEnum.NAME.name()).contains(chatId)) {
            preSearchByName(chatId);
        }
        if (waitCommand.get(SearchParamEnum.GENRE.name()).contains(chatId)) {
            preSearchByGenre(chatId);
        }
    }

    private void sendHelpText(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(MainButtons.inlineMarkup());


        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }

    private void getTop(Long chatId) {
        waitCommand.get(SearchParamEnum.TOP.name()).add(chatId);
        TotalTopFilmsDto filmDto = feignKinopoiskApi.getTop(API_TOKEN, (int) (Math.random() * 11));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        System.out.println(filmDto.getFilms().get(0).getNameRu());
        sendMessage.setText(filmDto.toString());
        if (filmDto.getFilms().size() >= 5) {
            filmDto.getFilms().stream().limit(5).map(x -> {
                try {
                    return execute(SendMessage.builder()
                            .chatId(String.valueOf(chatId))
                            .text(x.toString())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(System.out::println);
        } else {
            filmDto.getFilms().stream().map(x -> {
                try {
                    return execute(SendMessage.builder()
                            .chatId(String.valueOf(chatId))
                            .text(x.toString())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(System.out::println);
        }
        try {
            execute(SendMessage.builder()
                    .chatId(String.valueOf(chatId))
                    .text("Выбирите дальнейшее действие:")
                    .replyMarkup(BackButtons.inlineMarkup())
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void preSearchByRating(Long chatId) {
        waitCommand.get(SearchParamEnum.RATING.name()).add(chatId);
        SendMessage searchWord = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("Пожалуйста введите желаемый рейтинг фильма(Целое число): ")
                .build();
        try {
            execute(searchWord);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchByRating(Long chatId, Integer rating) {
        GenresDto ratingDto = feignKinopoiskApi.getByRating(API_TOKEN, rating);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        System.out.println(ratingDto.getItems().get(0).getNameRu());
        sendMessage.setText(rating.toString());
        if (ratingDto.getItems().size() >= 5) {
            ratingDto.getItems().stream().limit(5).map(x -> {
                try {
                    return execute(SendMessage.builder()
                            .chatId(String.valueOf(chatId))
                            .text(x.toString())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(System.out::println);
        } else {
            ratingDto.getItems().stream().map(x -> {
                try {
                    return execute(SendMessage.builder()
                            .chatId(String.valueOf(chatId))
                            .text(x.toString())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(System.out::println);
        }

    }

    private void preSearchByGenre(Long chatId) {
        waitCommand.get(SearchParamEnum.GENRE.name()).add(chatId);
        SendMessage searchGenre = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("Пожалуйста выберите жанр:")
                .replyMarkup(GenreButtons.inlineMarkup())
                .build();
        try {
            execute(searchGenre);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchByGenre(Long chatId, Long id) {
        GenresDto filmDto = feignKinopoiskApi.getByGenre(API_TOKEN, id);
        System.out.println(filmDto.toString());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        System.out.println(filmDto.getItems().get(0).getNameRu());
        sendMessage.setText(filmDto.toString());
        if (filmDto.getItems().size() >= 5) {
            filmDto.getItems().stream().limit(5).map(x -> {
                try {
                    return execute(SendMessage.builder()
                            .chatId(String.valueOf(chatId))
                            .text(x.toString())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(System.out::println);
        } else {
            filmDto.getItems().stream().map(x -> {
                try {
                    return execute(SendMessage.builder()
                            .chatId(String.valueOf(chatId))
                            .text(x.toString())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(System.out::println);
        }
        try {
            execute(SendMessage.builder()
                    .chatId(String.valueOf(chatId))
                    .text("Выбирите дальнейшее действие:")
                    .replyMarkup(BackButtons.inlineMarkup())
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void preSearchByName(Long chatId) {
        waitCommand.get(SearchParamEnum.NAME.name()).add(chatId);
        SendMessage searchWord = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("Пожалуйста введите название фильма:")
                .build();
        try {
            execute(searchWord);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    private void searchByName(Long chatId, String name) {
        FilmsDto filmDto = feignKinopoiskApi.getByName(API_TOKEN, name);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        System.out.println(filmDto.getFilms().get(0).getNameRu());
        sendMessage.setText(filmDto.toString());
        if (filmDto.getFilms().size() >= 5) {
            filmDto.getFilms().stream().limit(5).map(x -> {
                try {
                    return execute(SendMessage.builder()
                            .chatId(String.valueOf(chatId))
                            .text(x.toString())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(System.out::println);
        } else {
            filmDto.getFilms().stream().map(x -> {
                try {
                    return execute(SendMessage.builder()
                            .chatId(String.valueOf(chatId))
                            .text(x.toString())
                            .build());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(System.out::println);
        }
    }
}

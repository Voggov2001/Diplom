package com.example.diplom.bot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BotConfig {

    @Value("${bot.name}")
    private String botUsername;        //TODO refactor String on prop variable
    @Value("${bot.token}")
    private String botToken;



}

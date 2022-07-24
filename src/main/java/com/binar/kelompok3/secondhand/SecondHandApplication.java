package com.binar.kelompok3.secondhand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
//@EnableJpaAuditing
public class SecondHandApplication {

    private static final Logger logger = LoggerFactory.getLogger(SecondHandApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SecondHandApplication.class, args);
        final Logger logger = LoggerFactory.getLogger(SecondHandApplication.class);
        logger.info("Hello World!");
    }
}
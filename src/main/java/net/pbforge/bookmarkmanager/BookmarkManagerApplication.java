package net.pbforge.bookmarkmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
public class BookmarkManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookmarkManagerApplication.class, args);
    }
}

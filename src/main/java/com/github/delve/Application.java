package com.github.delve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.github.delve.dev",
        "com.github.delve.config",
        "com.github.delve.**.repository",
        "com.github.delve.**.service",
        "com.github.delve.**.controller"
})
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

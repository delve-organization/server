package com.github.delve.locale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Properties;

@RestController
@RequestMapping("/public")
public class LocaleController {

    private final CustomReloadableResourceBundleMessageSource messageSource;

    @Autowired
    public LocaleController(final CustomReloadableResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/translations")
    public Properties getTranslations(@RequestParam final String locale) {
        return messageSource.getAllProperties(Locale.forLanguageTag(locale));
    }
}

package dev.alnat.moneykeeper.conf;

import dev.alnat.moneykeeper.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Постобработчик авторизации пользователей
 *
 * Необходим для формирования аудит-лога авторизаций
 *
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        log.info("Авторизован {}", user.getUsername());

        super.onAuthenticationSuccess(request, response, authentication);
    }

}

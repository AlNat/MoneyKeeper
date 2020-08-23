package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Tag(name = "User API",
        description = "REST API для взаимодействия с пользователями")
@RestController
@RequestMapping(value = "/api/user", produces = {"application/json", "application/xml"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



}

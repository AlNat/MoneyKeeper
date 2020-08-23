package dev.alnat.moneykeeper.controller.api;

import dev.alnat.moneykeeper.service.UserGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author AlNat on 16.08.2020.
 * Licensed by Apache License, Version 2.0
 */
@Tag(name = "User Group API",
        description = "REST API для взаимодействия с группами пользователей")
@RestController
@RequestMapping(value = "/api/user/group", produces = {"application/json", "application/xml"})
public class UserGroupController {

    private final UserGroupService userGroupService;

    @Autowired
    public UserGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }




}

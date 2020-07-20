package dev.alnat.moneykeeper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author AlNat on 20.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@RestController("/test")
public class TestController {

    @Operation(summary = "Test",
            description = "Simple test", tags = {"test"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(
            @Parameter(description = "Test", required = true, example = "123")
            @RequestParam(name = "test", required = true)
                             String test) {
        System.out.println(test);
    }

}

package com.api.estudo.controller;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/city")
@Api(tags = "Jogo de tokens")
public class TokensGameController {

    private static final String API_KEY = "b6fa25ff074845bd56da7a06bb02d539";

    @GetMapping
    public ResponseEntity<String> cityy(@RequestParam(defaultValue = API_KEY) String apiKey){

        return ResponseEntity.ok(apiKey);
    }
}

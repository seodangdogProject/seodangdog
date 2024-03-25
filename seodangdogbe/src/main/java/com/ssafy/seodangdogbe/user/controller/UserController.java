package com.ssafy.seodangdogbe.user.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mypages")
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public void getMypage(){

    }

}

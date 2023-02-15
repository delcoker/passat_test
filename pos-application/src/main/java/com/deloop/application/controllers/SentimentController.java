package com.deloop.application.controllers;


import com.deloop.application.models.requests.SentimentAddRequest;
import com.deloop.application.services.SentimentService;
import com.deloop.domain.exceptions.EntityAlreadyExistsException;
import com.deloop.domain.models.SentimentDo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sentiment")
public class SentimentController {
    private final SentimentService sentimentService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SentimentDo>> addSentiment(@RequestBody SentimentAddRequest sentimentAddRequest) throws EntityAlreadyExistsException {

//        if (!sentimentAddRequest.isValid()) {
//            throw new InvalidParameterException("Email or password length invalid!");
//        }

        return ResponseEntity.ok(sentimentService.addSentiments(sentimentAddRequest));
    }


    @GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SentimentDo>> getSentiments(@SpringQueryMap SentimentAddRequest sentimentAddRequest) {

//        if (!sentimentAddRequest.isValid()) {
//            throw new InvalidParameterException("Email or password length invalid!");
//        }

        return ResponseEntity.ok(sentimentService.getSentiments(sentimentAddRequest));
    }

}

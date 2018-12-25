package com.yash.oauth_client.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/resource")
public class ClientController {

    @ApiOperation(value = "Access Documents", nickname = "Access private resources with authorization")
    @RequestMapping(value = {"/getDocuments"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getResources(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) final String oauthToken) {
        String documents = "Congratulations!! Authentication Success";
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }
}

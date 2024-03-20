package com.exportApp.controller.controller_response;

import com.azure.core.http.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Object responseObject, String message, HttpStatus status){

        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("data", responseObject);

        return new ResponseEntity<>(map, status);
    }
}

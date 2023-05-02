package com.example.frontcw.Json;

import lombok.Data;

@Data
public class Token {
    private String token;
    @Override
    public String toString(){
        return token;
    }
}

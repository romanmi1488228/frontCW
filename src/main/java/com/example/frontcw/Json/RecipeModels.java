package com.example.frontcw.Json;

import lombok.Data;

import java.util.List;

@Data
public class RecipeModels {
    private long id;
    private String ingredients;
    private String steps;
    private byte[] recipePicture;
    private List<ReviewModels> reviewModels;
}

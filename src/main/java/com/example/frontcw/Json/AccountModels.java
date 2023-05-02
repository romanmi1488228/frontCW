package com.example.frontcw.Json;

import lombok.Data;

import java.util.List;

@Data
public class AccountModels {
    private long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private List<RecipeModels> recipeModels;
    private List<ReviewModels> reviewModels;
    private boolean enabled;
    private String username;
    private List<Authority> authorities;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
}

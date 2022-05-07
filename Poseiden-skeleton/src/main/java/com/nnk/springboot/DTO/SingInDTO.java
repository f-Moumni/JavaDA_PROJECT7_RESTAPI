package com.nnk.springboot.DTO;

public class SingInDTO {

    private String username;
    private String password;

    public SingInDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

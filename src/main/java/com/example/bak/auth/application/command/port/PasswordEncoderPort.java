package com.example.bak.auth.application.command.port;

public interface PasswordEncoderPort {

    String encryptPassword(String password);

    void matchPassword(String password, String encodedPassword);
}

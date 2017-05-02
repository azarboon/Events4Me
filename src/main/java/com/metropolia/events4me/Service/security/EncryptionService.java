package com.metropolia.events4me.Service.security;


public interface EncryptionService {

    String encryptString(String input);

    boolean checkPassword(String plainPassword, String encryptedPassword);

}

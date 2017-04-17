package com.metropolia.events4me.Service.security;

/**
 * Created by Dmitry on 17.04.2017.
 */

public interface EncryptionService {

    String encryptString(String input);

    boolean checkPassword(String plainPassword, String encryptedPassword);

}

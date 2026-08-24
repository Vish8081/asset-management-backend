// dto/LoginRequest.java
package com.assetmanagement.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
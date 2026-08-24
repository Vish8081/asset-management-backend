// dto/LoginResponse.java
package com.assetmanagement.dto;

import com.assetmanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private User user;
}
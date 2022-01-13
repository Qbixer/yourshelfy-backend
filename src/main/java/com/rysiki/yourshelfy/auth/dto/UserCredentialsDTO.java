package com.rysiki.yourshelfy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCredentialsDTO {

    String email;
    String password;
    String phone;
    String firstname;
    String surname;
}

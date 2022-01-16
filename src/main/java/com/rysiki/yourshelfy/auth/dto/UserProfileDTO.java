package com.rysiki.yourshelfy.auth.dto;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {

    String email;
    String phone;
    String firstname;
    String surname;

    public static UserProfileDTO createUserProfileDTOFromMyUser(MyUser myUser) {
        return UserProfileDTO.builder()
                .email(myUser.getEmail())
                .phone(myUser.getPhone())
                .firstname(myUser.getFirstname())
                .surname(myUser.getSurname())
                .build();
    }
}

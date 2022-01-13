package com.rysiki.yourshelfy.auth.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "my_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyUser {

    @Id
    String email;
    String password;
    String phone;
    String firstname;
    String surname;
    Boolean enabled;



}

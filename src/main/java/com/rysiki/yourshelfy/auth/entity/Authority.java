package com.rysiki.yourshelfy.auth.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "my_user_authority")
@Data
@IdClass(AuthorityId.class)
public class Authority {

    @Id
    String email;
    @Id
    String authority;

}

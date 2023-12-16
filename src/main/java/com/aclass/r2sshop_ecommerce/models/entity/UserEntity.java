package com.aclass.r2sshop_ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "full_Name", length = 100)
    private String fullName;

    @Column(name = "email", length = 100)
    private String email;

    @OneToMany(mappedBy = "user_id")
    private List<AddressEntity> addressEntities;

    @OneToOne(mappedBy = "user_id")
    private CartEntity cart;

    @OneToMany(mappedBy = "user_id")
    private List<RoleUserEntity> roleUserEntities;
}

package com.aclass.r2sshop_ecommerce.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<UserEntity> userEntities = new HashSet<>();
}

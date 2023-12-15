package com.aclass.r2sshop_ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_Date")
    private Date createDate;

    @OneToOne()
    @JoinColumn(name = "User_id", nullable = false, referencedColumnName = "id")// => category's id
    @JsonBackReference
    private UserEntity user;
   /* @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id")// => category's id
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "course")
    private List<AccountCourse> accountCourses;
*/
}

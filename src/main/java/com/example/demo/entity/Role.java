package com.example.demo.entity;

import com.example.demo.common.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(schema = "hanuvi")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    private List<AppUser> users = new ArrayList<>();
}

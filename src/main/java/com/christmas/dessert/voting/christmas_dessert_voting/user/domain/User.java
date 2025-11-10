package com.christmas.dessert.voting.christmas_dessert_voting.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
        @EmbeddedId
        @EqualsAndHashCode.Include
        private UserId id;
        @Column(nullable = false)
        private String name;
        @Column(nullable = false, unique = true)
        private String cpf;
        @Column(nullable = false, unique = true)
        private String email;
        @Column(name = "password", nullable = false)
        private String passwordHash;
        @Column(name = "favorite_sweets")
        private List<String> favoriteSweets;
}

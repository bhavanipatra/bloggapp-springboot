package com.bsp.blogappspringboot.users;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String username;

    @Column(nullable = false)
    @NonNull
    private String password;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = true)
    @Nullable
    private String bio;

    @Column(nullable = true)
    @Nullable
    private String image;
}

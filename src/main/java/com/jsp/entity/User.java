package com.jsp.entity;

import com.jsp.util.NoSpecialChars;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

/**
 * Entity representing a user.
 * Contains user ID, name, email, and password.
 */
@Entity
@Table(name = "users")
@ToString
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @NoSpecialChars(message = "The string must not contain escape characters!")
    private String name;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String gmail;

    private String password;
}

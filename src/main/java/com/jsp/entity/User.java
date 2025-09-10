package com.jsp.entity;

import com.jsp.util.NoSpecialChars;
import jakarta.persistence.*;
import lombok.*;

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
    private String gmail;
    private String password;
}

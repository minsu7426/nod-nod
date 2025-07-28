package nod.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

import static org.springframework.util.Assert.*;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String userId;

    @Column(nullable = false, length = 100)
    private String password;

    public static User create(final String userId, final String password) {
        hasText(userId, "userId must not be empty");
        hasText(password, "password must not be empty");
        User user = new User();
        user.userId = userId;
        user.password = password;
        return user;
    }
}

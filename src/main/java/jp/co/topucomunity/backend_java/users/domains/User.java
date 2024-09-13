package jp.co.topucomunity.backend_java.users.domains;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "users")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String sub;

    @Column(unique = true, nullable = false)
    private String email;

    @Builder
    private User(String email, String sub) {
        this.email = email;
        this.sub = sub;
    }

    public static User of(String sub, String email) {
        return User.builder()
                .sub(sub)
                .email(email)
                .build();
    }
}

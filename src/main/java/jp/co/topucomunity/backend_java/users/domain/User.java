package jp.co.topucomunity.backend_java.users.domain;

import jakarta.persistence.*;
import jp.co.topucomunity.backend_java.recruitments.domain.Position;
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

    private String nickname;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Position position;

    private String affiliation;

    private Boolean isPublicAffiliation;

    private Integer personalHistoryYear;

    @Lob
    private String selfIntroduction;

    private String links;

    @Builder
    public User(String sub, String email, String nickname, Position position, String affiliation, Boolean isPublicAffiliation, Integer personalHistoryYear, String selfIntroduction, String links) {
        this.sub = sub;
        this.email = email;
        this.nickname = nickname;
        this.position = position;
        this.affiliation = affiliation;
        this.isPublicAffiliation = isPublicAffiliation;
        this.personalHistoryYear = personalHistoryYear;
        this.selfIntroduction = selfIntroduction;
        this.links = links;
    }

    public static User of(String sub, String email) {
        return User.builder()
                .sub(sub)
                .email(email)
                .build();
    }

    public static User createFirstLoggedInUser(String email, String nickname, Position position, String affiliation, Boolean isPublicAffiliation, Integer personalHistoryYear, String selfIntroduction, String links) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .position(position)
                .affiliation(affiliation)
                .isPublicAffiliation(isPublicAffiliation)
                .personalHistoryYear(personalHistoryYear)
                .selfIntroduction(selfIntroduction)
                .links(links)
                .build();
    }
}

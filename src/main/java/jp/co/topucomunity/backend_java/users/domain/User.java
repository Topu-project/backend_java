package jp.co.topucomunity.backend_java.users.domain;

import jakarta.persistence.*;
import jp.co.topucomunity.backend_java.recruitments.domain.Position;
import jp.co.topucomunity.backend_java.recruitments.domain.TechStack;
import jp.co.topucomunity.backend_java.users.usecase.in.RegisterUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

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

    private boolean isPublicAffiliation;

    @ColumnDefault(value = "true")
    private boolean isFirstLogin;

    private Integer personalHistoryYear;

    @Lob
    private String selfIntroduction;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<TechStack> techStacks = new ArrayList<>();

    private String links;

    @Builder
    public User(String sub, String email, String nickname, Position position, String affiliation, boolean isPublicAffiliation, boolean isFirstLogin, Integer personalHistoryYear, String selfIntroduction, List<TechStack> techStacks, String links) {
        this.sub = sub;
        this.email = email;
        this.nickname = nickname;
        this.position = position;
        this.affiliation = affiliation;
        this.isPublicAffiliation = isPublicAffiliation;
        this.isFirstLogin = isFirstLogin;
        this.personalHistoryYear = personalHistoryYear;
        this.selfIntroduction = selfIntroduction;
        this.techStacks = techStacks;
        this.links = links;
    }

    public static User of(String sub, String email) {
        return User.builder()
                .sub(sub)
                .email(email)
                .isFirstLogin(true)
                .build();
    }

    public static User createFirstLoggedInUser(String email, String nickname, Position position, String affiliation, boolean isPublicAffiliation, Integer personalHistoryYear, String selfIntroduction, String links, boolean isFirstLogin) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .position(position)
                .affiliation(affiliation)
                .isPublicAffiliation(isPublicAffiliation)
                .isFirstLogin(isFirstLogin)
                .personalHistoryYear(personalHistoryYear)
                .selfIntroduction(selfIntroduction)
                .links(links)
                .build();
    }

    public void registerFirstLoginUserInfo(RegisterUser registerUser) {
        this.nickname = registerUser.getNickname();
        this.position = Position.from(registerUser.getPositionName());
        this.personalHistoryYear = registerUser.getPersonalHistoryYear();
        this.isPublicAffiliation = false;
        this.isFirstLogin = false;
        this.techStacks = registerUser.getTechStackNames().stream()
                .map(TechStack::from)
                .toList();
    }
}

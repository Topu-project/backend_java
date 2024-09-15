package jp.co.topucomunity.backend_java.users.controller.out;

import jp.co.topucomunity.backend_java.users.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class UserResponse {
    private final Long userId;
    private final String email;
    private final String nickname;
    private final String position;
    private final String affiliation;
    private final Boolean isPublicAffiliation;
    private final Integer personalHistoryYear;
    private final String selfIntroduction;
    private final List<String> links;

    @Builder
    private UserResponse(Integer personalHistoryYear, Long userId, String email, String nickname, String position, String affiliation, Boolean isPublicAffiliation, String selfIntroduction, List<String> links) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.position = position;
        this.affiliation = affiliation;
        this.isPublicAffiliation = isPublicAffiliation;
        this.personalHistoryYear = personalHistoryYear;
        this.selfIntroduction = selfIntroduction;
        this.links = links;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .position(user.getPosition().getPositionName())
                .affiliation(user.getAffiliation())
                .isPublicAffiliation(user.getIsPublicAffiliation())
                .personalHistoryYear(user.getPersonalHistoryYear())
                .selfIntroduction(user.getSelfIntroduction())
                .links(Arrays.stream(user.getLinks().split(",")).map(String::trim).toList())
                .build();
    }
}

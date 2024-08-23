package jp.co.topucomunity.backend_java.recruitments.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String position;

    @OneToMany(mappedBy = "position")
    private List<RecruitmentPosition> recruitmentPositions = new ArrayList<>();

    private Position(String position) {
        this.position = position;
    }

    public static Position of(String position) {
        return new Position(position);
    }

    public void makeRelationship(RecruitmentPosition recruitmentPosition) {
        recruitmentPositions.add(recruitmentPosition);
    }
}

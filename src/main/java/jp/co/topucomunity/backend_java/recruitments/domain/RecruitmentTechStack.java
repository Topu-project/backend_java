package jp.co.topucomunity.backend_java.recruitments.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentTechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private TechStack techStack;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recruitment recruitment;

    private RecruitmentTechStack(TechStack techStack, Recruitment recruitment) {
        this.techStack = techStack;
        this.recruitment = recruitment;
    }

    public static RecruitmentTechStack from(TechStack techStack, Recruitment recruitment) {
        return new RecruitmentTechStack(techStack, recruitment);
    }
}

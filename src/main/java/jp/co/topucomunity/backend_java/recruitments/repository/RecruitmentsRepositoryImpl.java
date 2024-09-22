package jp.co.topucomunity.backend_java.recruitments.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jp.co.topucomunity.backend_java.recruitments.domain.QRecruitment;
import jp.co.topucomunity.backend_java.recruitments.domain.Recruitment;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RecruitmentsRepositoryImpl implements RecruitmentsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Recruitment> getList(int page) {
        return jpaQueryFactory.selectFrom(QRecruitment.recruitment)
//                .limit(10)
//                .offset((page - 1) * 10L)
                .fetch();
    }
}

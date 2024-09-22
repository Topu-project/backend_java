package jp.co.topucomunity.backend_java.recruitments.repository;

import jp.co.topucomunity.backend_java.recruitments.domain.Recruitment;

import java.util.List;

public interface RecruitmentRepositoryCustom {

    List<Recruitment> getRecruitmentList(int page);
}

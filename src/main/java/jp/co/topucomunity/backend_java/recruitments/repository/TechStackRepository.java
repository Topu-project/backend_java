package jp.co.topucomunity.backend_java.recruitments.repository;

import jp.co.topucomunity.backend_java.recruitments.domain.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {

    Optional<TechStack> findByTechnologyName(String technologyName);
}

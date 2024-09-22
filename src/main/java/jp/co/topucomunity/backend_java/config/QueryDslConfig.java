package jp.co.topucomunity.backend_java.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    public EntityManager em;

    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}

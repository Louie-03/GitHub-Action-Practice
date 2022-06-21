package louie.dong.airbnb.study;

import static louie.dong.airbnb.domain.QMember.*;
import static org.assertj.core.api.Assertions.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import louie.dong.airbnb.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
class QuerydslTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    void where문에_null을_입력한다면_해당_부분은_쿼리가_생성되지_않는다() {
        queryFactory
            .selectFrom(member)
            .where(
                member.id.eq(1L),
                null
            )
            .fetchOne();
    }

    @Test
    void where문에_null을_입력하지_않는다면_해당_부분은_쿼리가_생성된다() {
        queryFactory
            .selectFrom(member)
            .where(
                member.id.eq(1L),
                member.name.eq("memberA")
            )
            .fetchOne();
    }
}

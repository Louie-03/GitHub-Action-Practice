package louie.dong.airbnb.repository;

import louie.dong.airbnb.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByGitHubIdNot(String gitHubId);

}

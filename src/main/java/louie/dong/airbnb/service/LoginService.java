package louie.dong.airbnb.service;

import lombok.RequiredArgsConstructor;
import louie.dong.airbnb.repository.MemberRepository;
import louie.dong.airbnb.domain.Member;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberRepository memberRepository;

    public void login(Member member) {
        if (memberRepository.existsByGitHubIdNot(member.getGitHubId())) {
            memberRepository.save(member);
        }
    }
}

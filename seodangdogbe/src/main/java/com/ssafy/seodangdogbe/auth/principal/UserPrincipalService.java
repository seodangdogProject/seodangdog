package com.ssafy.seodangdogbe.auth.principal;


import com.ssafy.seodangdogbe.auth.repository.UserRepository;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("*** login loadUserByUsername *** ");
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
        return new UserPrincipal(user);
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    // 이걸로 빼면 왜 안됨 ㅎㅎ ? 개빡친다
//    private UserDetails createUserDetails(Member member) {
//        log.info("*** login createUserDetails *** ");
//        Member createMember = Member.builder()
//                .memberId(member.getMemberId())
//                .password(passwordEncoder.encode(member.getPassword()))
//                .role("Member")
//                .build();
//
//        System.out.println(member);
//        return new MemberPrincipal(createMember);
//    }

}
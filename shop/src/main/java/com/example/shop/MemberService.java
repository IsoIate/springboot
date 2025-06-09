package com.example.shop;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

//@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean signUp (Member member, Map<String, String> data) throws Exception {
        String userName = data.get("username");
        String password = data.get("password");
        String displayName = data.get("displayName");

        if(userName.length() < 4 || password.length() < 4) {
            throw new Exception("아이디가 너무 짧습니다.");
        }

        var result = memberRepository.findByUsername(userName);
        System.out.println(result);
        if(result.isPresent()) {    // optional 값 보려면 result.get().toString() 사용
            throw new Exception("이미 존재하는 아이디입니다.");
        }

        // 패스워드 암호화
        String encoderPassword = passwordEncoder.encode(password);
        data.put("password", encoderPassword);

        member.setUsername(userName);
        member.setPassword(data.get("password"));
        member.setDisplayName(displayName);

        memberRepository.save(member);

        return true;
    }
}

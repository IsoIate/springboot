package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 회원가입
    @GetMapping("/register")
    public String register(Authentication auth) {

        if (auth == null || !auth.isAuthenticated())
            return "register.html";
        else
            return "redirect:/list";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/my-page")
//    @PreAuthorize("isAuthenticated()")    // 로그인 상태 확인
//    @PreAuthorize("isAnonymous()")        // 로그아웃 상태 확인
//    @PreAuthorize("hasAnyAuthority('')")  // 특정 권한 확인
    public String mypage(Authentication auth) {
//        System.out.println(auth);
//        System.out.println(auth.getName());
//        System.out.println(auth.isAuthenticated()); // 로그인 여부
        CustomUser user = (CustomUser) auth.getPrincipal();
        return "mypage.html";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDTO getUser () {
        var a = memberRepository.findById(1);
        var result = a.get();

        return new MemberDTO(result.getUsername(), result.getDisplayName());
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute Member member, @RequestParam Map<String, String> data) throws Exception {

        boolean result = memberService.signUp(member, data);

        if (!result) {
            return "redirect:/error";
        }

        return "redirect:/list";
    }
}

// data transfer object
// DB에서 조회한 데이터를 특정 목적에 맞게 가공하기 위한 클래스
class MemberDTO {
    public String username;
    public String displayName;

    MemberDTO (String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }
}

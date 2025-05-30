package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

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
        return "mypage.html";
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

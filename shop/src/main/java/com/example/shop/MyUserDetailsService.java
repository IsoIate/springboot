package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var result = memberRepository.findByUsername(username);

        if(result.isEmpty()) {
            throw new UsernameNotFoundException("아이디 존재하지 않음");
        }
        var user = result.get();

        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("일반유저"));

        CustomUser customUser = new CustomUser(user.getUsername(), user.getPassword(), authList);
        customUser.userId = user.getId();
        customUser.displayName = user.getDisplayName();

        return customUser;
//        return new User(user.getUsername(), user.getPassword(), authList);
    }

}


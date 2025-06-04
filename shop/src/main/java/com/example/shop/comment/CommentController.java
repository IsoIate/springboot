package com.example.shop.comment;

import com.example.shop.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/commentWrite")
    String commentWrite(@ModelAttribute Comment comment, @RequestBody Map<String, String> data, Authentication auth) {

        CustomUser user = (CustomUser) auth.getPrincipal();
        data.put("username", user.getUsername());

        boolean result = commentService.commentWrite(comment, data);

        if(result)
            ResponseEntity.status(400).body("댓글 작성 오류");

        return "redirect:/detail/" + data.get("contentId");
    }
}

package com.example.shop.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public boolean commentWrite (Comment comment, Map<String, String> data) {

        Long contentId = Long.valueOf(data.get("contentId"));   // 게시글 id
        String insertValue = data.get("comment");               // 댓글
        String username = data.get("username");

        comment.setContentId(contentId);
        comment.setComment(insertValue);
        comment.setUsername(username);

        commentRepository.save(comment);

        return false;
    }
}

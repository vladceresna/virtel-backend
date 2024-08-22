package com.vladceresna.virtel_backend.services;

import com.vladceresna.virtel_backend.models.Comment;
import com.vladceresna.virtel_backend.models.Skill;
import com.vladceresna.virtel_backend.repos.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    public Comment getComment(UUID id) {
        return commentsRepository.findById(id).orElseThrow();
    }
    public List<Comment> getCommentsOfUser(String authorEmail) {
        return commentsRepository.findAllByAuthorEmail(authorEmail);
    }
    public List<Comment> getCommentsOfSkill(UUID skillId) {
        return commentsRepository.findAllBySkillId(skillId);
    }
    public List<Comment> getAllComments() {
        return commentsRepository.findAll();
    }
    public Comment createComment(UUID skillId, String text, String authorEmail) {
        return commentsRepository.save(Comment.builder()
                .skillId(skillId)
                .text(text)
                .authorEmail(authorEmail)
                .createdTime(LocalDateTime.now())
                .build());
    }

    public void deleteComment(UUID id) {
        commentsRepository.deleteById(id);
    }

    public boolean isCommentExist(UUID id) {
        return commentsRepository.findById(id).isPresent();
    }

}

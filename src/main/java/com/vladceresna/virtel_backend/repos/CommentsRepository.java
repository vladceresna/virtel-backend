package com.vladceresna.virtel_backend.repos;

import com.vladceresna.virtel_backend.models.Comment;
import com.vladceresna.virtel_backend.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllBySkillId(UUID skillId);
    List<Comment> findAllByAuthorEmail(String authorEmail);
}

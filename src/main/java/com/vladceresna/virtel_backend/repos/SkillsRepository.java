package com.vladceresna.virtel_backend.repos;

import com.vladceresna.virtel_backend.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface SkillsRepository extends JpaRepository<Skill, UUID> {
    List<Skill> findAllByAuthorEmail(String authorEmail);

}

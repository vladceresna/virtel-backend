package com.vladceresna.virtel_backend.services;

import com.vladceresna.virtel_backend.models.Skill;
import com.vladceresna.virtel_backend.repos.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SkillsService {
    @Autowired
    private SkillsRepository skillsRepository;

    public Skill getSkill(UUID id) {
        return skillsRepository.findById(id).orElseThrow();
    }
    public List<Skill> getSkillsOfUser(String authorEmail) {
        return skillsRepository.findAllByAuthorEmail(authorEmail);
    }
    public List<Skill> getAllSkills() {
        return skillsRepository.findAll();
    }
    public Skill createSkill(String name, String description, String code, String authorEmail) {
        return skillsRepository.save(Skill.builder()
                .code(code)
                .description(description)
                .name(name)
                .authorEmail(authorEmail)
                .build());
    }
    public void deleteSkill(UUID id) {
        skillsRepository.deleteById(id);
    }


    public boolean isSkillExist(UUID id) {
        return skillsRepository.findById(id).isPresent();
    }
}

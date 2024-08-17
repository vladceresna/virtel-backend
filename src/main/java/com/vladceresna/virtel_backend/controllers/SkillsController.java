package com.vladceresna.virtel_backend.controllers;

import com.vladceresna.virtel_backend.models.Skill;
import com.vladceresna.virtel_backend.services.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class SkillsController {
    @Autowired
    private SkillsService skillsService;

    @GetMapping("/skill")
    public ResponseEntity<Skill> getSkill(@RequestParam UUID id){
        return new ResponseEntity<>(skillsService.getSkill(id), HttpStatus.OK);
    }

    @GetMapping("/skills/all")
    public ResponseEntity<List<Skill>> getAllSkills(){
        return new ResponseEntity<>(skillsService.getAllSkills(), HttpStatus.OK);
    }

    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getSkills(@RequestParam String authorEmail){
        return new ResponseEntity<>(skillsService.getSkillsOfUser(authorEmail), HttpStatus.OK);
    }
    @PostMapping("/skill")
    public ResponseEntity<Skill> createSkill(@RequestParam String name,
                                             @RequestParam String description,
                                             @RequestParam String code,
                                             @RequestParam String authorEmail){
        return new ResponseEntity<>(skillsService.createSkill(name, description, code, authorEmail), HttpStatus.OK);
    }
    @DeleteMapping("/skill")
    public void deleteSkill(@RequestParam UUID id, @RequestParam UUID adminToken){
        if (adminToken.equals(UUID.fromString(System.getenv("ADMIN_TOKEN")))) {
            skillsService.deleteSkill(id);
        }
    }

}
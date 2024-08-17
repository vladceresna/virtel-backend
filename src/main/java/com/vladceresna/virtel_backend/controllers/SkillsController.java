package com.vladceresna.virtel_backend.controllers;

import com.vladceresna.virtel_backend.models.Skill;
import com.vladceresna.virtel_backend.services.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class SkillsController {
    @Autowired
    private SkillsService skillsService;

    @Value("${skills.admin.token}")
    private String skills_admin_token;

    @GetMapping("/skill")
    public ResponseEntity<Skill> getSkill(@RequestParam UUID id){
        if(skillsService.isSkillExist(id)){
            return new ResponseEntity<>(skillsService.getSkill(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Object> deleteSkill(@RequestParam UUID id, @RequestParam UUID adminToken){
        if (adminToken.equals(UUID.fromString(skills_admin_token))){
            skillsService.deleteSkill(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
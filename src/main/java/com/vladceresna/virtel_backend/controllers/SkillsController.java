package com.vladceresna.virtel_backend.controllers;

import com.vladceresna.virtel_backend.models.Skill;
import com.vladceresna.virtel_backend.models.SkillDto;
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
    @PostMapping("/skill/like")
    public ResponseEntity<Skill> addLike(@RequestParam UUID id){
        if(skillsService.isSkillExist(id)){
            return new ResponseEntity<>(skillsService.addLike(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/skills/all")
    public ResponseEntity<List<Skill>> getAllSkills(){
        return new ResponseEntity<>(skillsService.getAllSkills(), HttpStatus.OK);
    }

    @GetMapping("/skills/query")
    public ResponseEntity<List<Skill>> getSkillsByQuery(@RequestParam String query){
        List<Skill> skillsByQuery = skillsService.getSkillsByQuery(query);
        if(!skillsByQuery.isEmpty()){
            return new ResponseEntity<>(skillsByQuery, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getSkills(@RequestParam String authorEmail){
        return new ResponseEntity<>(skillsService.getSkillsOfUser(authorEmail), HttpStatus.OK);
    }
    @PostMapping("/skill")
    public ResponseEntity<Skill> createSkill(@RequestBody SkillDto skill){
        return new ResponseEntity<>(skillsService.createSkill(skill.name(), skill.description(), skill.code(), skill.authorEmail()), HttpStatus.OK);
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
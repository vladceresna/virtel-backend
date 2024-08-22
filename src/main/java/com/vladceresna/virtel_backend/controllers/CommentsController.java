package com.vladceresna.virtel_backend.controllers;

import com.vladceresna.virtel_backend.models.Comment;
import com.vladceresna.virtel_backend.models.CommentDto;
import com.vladceresna.virtel_backend.models.Skill;
import com.vladceresna.virtel_backend.models.SkillDto;
import com.vladceresna.virtel_backend.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @Value("${skills.admin.token}")
    private String skills_admin_token;



    @GetMapping("/comments/all")
    public ResponseEntity<List<Comment>> getAllComments(){
        return new ResponseEntity<>(commentsService.getAllComments(), HttpStatus.OK);
    }

    @GetMapping("/comments/filter")
    public ResponseEntity<List<Comment>> getCommentsByAuthor(@RequestParam String authorEmail){
        return new ResponseEntity<>(commentsService.getCommentsOfUser(authorEmail), HttpStatus.OK);
    }
    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getCommentsBySkill(@RequestParam UUID skillId){
        return new ResponseEntity<>(commentsService.getCommentsOfSkill(skillId), HttpStatus.OK);
    }
    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto comment){
        return new ResponseEntity<>(commentsService.createComment(comment.skillId(), comment.text(), comment.authorEmail()), HttpStatus.OK);
    }
    @DeleteMapping("/comment")
    public ResponseEntity<Object> deleteComment(@RequestParam UUID id, @RequestParam UUID adminToken){
        if (adminToken.equals(UUID.fromString(skills_admin_token))){
            commentsService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
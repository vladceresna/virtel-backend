package com.vladceresna.virtel_backend.services;

import com.vladceresna.virtel_backend.models.Skill;
import com.vladceresna.virtel_backend.repos.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                .createdTime(LocalDateTime.now())
                .likes(1)
                .build());
    }
    public Skill addLike(UUID id){
        Skill skill = getSkill(id);
        skill.setLikes(skill.getLikes()+1);
        return skillsRepository.save(skill);
    }

    public void deleteSkill(UUID id) {
        skillsRepository.deleteById(id);
    }


    public boolean isSkillExist(UUID id) {
        return skillsRepository.findById(id).isPresent();
    }
    /**
     * @param query as "name code"
     *              any location
     *              can be spaces everywhere, but not after codename
     * **/
    public List<Skill> getSkillsByQuery(String query) {
        List<Skill> allSkills = getAllSkills();
        Skill[] preResult = new Skill[Short.MAX_VALUE];
        int maxPreResult = Integer.MIN_VALUE;

        String[] fullSearch = query.split(" ");

        for(int x = 0;x<allSkills.size();x++){
            Skill skill = allSkills.get(x);
            int searchLevel = 0;
            for(String contained:fullSearch){
                searchLevel += countOccurrences(skill.getName(),contained)
                        + countOccurrences(skill.getDescription(),contained)
                        + countOccurrences(skill.getCode(),contained)
                        + countOccurrences(skill.getAuthorEmail(),contained);
            }
            skill.setTempSearchLevel(searchLevel);

            preResult[x*searchLevel] = skill;
            if (x*searchLevel>maxPreResult){
                maxPreResult = x*searchLevel;
            }
        }
        List<Skill> result = new ArrayList<>();
        for (int x = maxPreResult;x>-1;x--) {
            if(preResult[x]!=null) {
                result.add(preResult[x]);
            }
        }
        return result;
    }
    public int countOccurrences(String text, String substring) {
        int count = 0, fromIndex = 0;
        while ((fromIndex = text.indexOf(substring, fromIndex)) != -1) {
            count++;
            fromIndex++;
        }
        return count;
    }
}

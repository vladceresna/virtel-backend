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
     * @param query as "name:name description:description code:code creator:creator"
     *              any location
     *              can be spaces everywhere, but not after codename
     * **/
    public List<Skill> getSkillsByQuery(String query) {
        List<Skill> allSkills = getAllSkills();
        List<Skill> result = new ArrayList<>();
        query = query.toLowerCase().trim();

        List<String> name = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> code = new ArrayList<>();
        List<String> authorEmail = new ArrayList<>();
        List<String> fullSearch = new ArrayList<>();


        String lastWord = "";
        String lastCommand = "";
        boolean scanningParameter = true;
        for(int x = 0;x<query.length();x++){
            char c = query.charAt(x);
            if (c == ' '){
                if(lastWord.length() > 0){
                    switch (lastCommand){
                        case "name":
                            name.add(lastWord);
                            break;
                        case "description":
                            description.add(lastWord);
                            break;
                        case "code":
                            code.add(lastWord);
                            break;
                        case "authorEmail":
                            authorEmail.add(lastWord);
                            break;
                        default:
                            fullSearch.add(lastWord);
                            break;
                    }
                    lastWord = "";
                    lastCommand = "";

                }
            } else if (c == ':') {
                lastCommand = lastWord;
            } else {
                lastWord += Character.toString(c);
            }
        }

        for (Skill skill:allSkills) {
            int searchLevel = 0;
            for (String contained:name) if (!skill.getName().contains(contained)) {
                searchLevel++;//lowering in search results
            }
            for (String contained:description) if (!skill.getDescription().contains(contained)) {
                searchLevel++;//lowering in search results
            }
            for (String contained:code) if (!skill.getCode().contains(contained)) {
                searchLevel++;//lowering in search results
            }
            for (String contained:authorEmail) if (!skill.getAuthorEmail().contains(contained)) {
                searchLevel++;//lowering in search results
            }
            for (String contained:fullSearch)
                if (!(skill.getName().contains(contained)
                        || skill.getDescription().contains(contained)
                        || skill.getCode().contains(contained)
                        || skill.getAuthorEmail().contains(contained)
                )) {
                searchLevel++;//lowering in search results
            }
            result.add(searchLevel, skill);
        }

        return result;
    }
}

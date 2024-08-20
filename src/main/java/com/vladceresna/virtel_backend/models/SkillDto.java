package com.vladceresna.virtel_backend.models;

import org.springframework.web.bind.annotation.RequestParam;

public record SkillDto(String name, String description,
                       String code, String authorEmail) {}

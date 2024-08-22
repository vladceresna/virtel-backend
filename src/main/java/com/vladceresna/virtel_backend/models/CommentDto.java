package com.vladceresna.virtel_backend.models;

import java.util.UUID;

public record CommentDto(UUID skillId,
                         String text,
                         String authorEmail) {}

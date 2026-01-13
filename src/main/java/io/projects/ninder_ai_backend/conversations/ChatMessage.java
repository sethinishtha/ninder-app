package io.projects.ninder_ai_backend.conversations;

import java.time.LocalDateTime;

public record ChatMessage(
        String messageText,
        String senderProfileId,
        LocalDateTime localDateTime
) {
}

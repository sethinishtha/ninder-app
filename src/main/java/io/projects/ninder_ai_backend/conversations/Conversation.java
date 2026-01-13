package io.projects.ninder_ai_backend.conversations;

import java.util.List;

public record Conversation(
        String id,
        String receiverProfileId,
        List<ChatMessage> chatMessageList
) {
}

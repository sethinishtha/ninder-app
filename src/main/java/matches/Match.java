package matches;


import io.projects.ninder_ai_backend.profiles.Profile;

public record Match (String id, Profile profile, String conversationId) {
    }


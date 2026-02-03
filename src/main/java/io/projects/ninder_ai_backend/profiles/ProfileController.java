package io.projects.ninder_ai_backend.profiles;

import io.projects.ninder_ai_backend.conversations.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProfileController {

    @Autowired
    public final ConversationRepository conversationRepository;
    @Autowired
    public final ProfileRepository profileRepository;

    public ProfileController(ConversationRepository conversationRepository, ProfileRepository profileRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getProfiles")
    public Map<String, Object> getAllSavedConversations(){

        Map<String, Object> map = new HashMap<>();
        List<Profile> profiles = profileRepository.findAll();

        map.put("Count", profiles.size());
        map.put("Profiles",profiles);

        return map ;

    }
    @CrossOrigin(origins="*")
    @GetMapping("/api/profiles/random")
    public Profile getRandomProfile(){
        return profileRepository.getRandomProfile();
    }

}

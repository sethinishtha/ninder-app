package io.projects.ninder_ai_backend.profiles;

import io.projects.ninder_ai_backend.conversations.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


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

    @CrossOrigin(origins="*")
    @GetMapping("/api/profiles/{id}")
    public Profile getProfile(@PathVariable String id){
        return profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + id
                ));

    }

}

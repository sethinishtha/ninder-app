package io.projects.ninder_ai_backend.conversations;

import io.projects.ninder_ai_backend.profiles.Gender;
import io.projects.ninder_ai_backend.profiles.Profile;
import io.projects.ninder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class ConversationController {

    @Autowired
    public final ConversationRepository conversationRepository;
    @Autowired
    public final ProfileRepository profileRepository;
    @Autowired
    public final ConversationService coversationService;

    @Value("#{${tinderai.character.user}}")
    private Map<String, String> userProfileProperties;

    public ConversationController(ConversationRepository conversationRepository,ProfileRepository profileRepository,ConversationService coversationService) {
        this.conversationRepository=conversationRepository;
        this.profileRepository=profileRepository;
        this.coversationService=coversationService;
    }

    private Profile getUserProfile() {
        return new Profile(
                userProfileProperties.get("id"),
                userProfileProperties.get("firstName"),
                userProfileProperties.get("lastName"),
                Integer.parseInt(userProfileProperties.get("age")),
                userProfileProperties.get("ethnicity"),
                Gender.valueOf(userProfileProperties.get("gender")),
                userProfileProperties.get("bio"),
                userProfileProperties.get("imageUrl"),
                userProfileProperties.get("myersBriggsPersonalityType")
        );
    }

    @CrossOrigin(origins="*")
    @PostMapping("/conversations")
    public Conversation createConverstion(@RequestBody ConversationRequest request){

        Profile profile = profileRepository.findById(request.profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + request.profileId
                ));

        Conversation conversation = new Conversation(
                request.conversationId,
                request.profileId,
                new ArrayList<>()
        );

        conversationRepository.save(conversation);

        return conversation;
    }

    @CrossOrigin(origins="*")
    @GetMapping("/getConversations/{conversationsId}")
    public List<Conversation> getAllSavedConversations(@PathVariable String conversationsId){

        System.out.println(conversationRepository.findAllById(Collections.singleton(conversationsId)));
        return conversationRepository.findAllById(Collections.singleton(conversationsId));

    }

    @CrossOrigin(origins="*")
    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessagetoConversation(@PathVariable String conversationId, @RequestBody ChatMessage chat) {

        System.out.println("Received request to add message to conversation " + conversationId + ": " + chat);


        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find a conversation with ID" + conversationId));

        System.out.println("Conversation " + conversation );
        Profile profile = profileRepository.findById(chat.senderProfileId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + chat.senderProfileId()
                ));

        ChatMessage chatMessageUpdated = new ChatMessage(
                chat.messageText(),
                chat.senderProfileId(),
                LocalDateTime.now()
        );

        Profile user = getUserProfile();

        conversation.messages().add(chatMessageUpdated);
        coversationService.generateProfileResponse(conversation, profile, user);
        conversationRepository.save(conversation);
        System.out.println("Conversation " + conversation );
        return conversation;

    }

        public record ConversationRequest(String profileId, String conversationId){}
}

package io.projects.ninder_ai_backend.conversations;

import io.projects.ninder_ai_backend.profiles.Profile;
import io.projects.ninder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ConversationController(ConversationRepository conversationRepository,ProfileRepository profileRepository){
        this.conversationRepository=conversationRepository;
        this.profileRepository=profileRepository;
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
                UUID.randomUUID().toString(),
                request.profileId,
                new ArrayList<>()
        );

        conversationRepository.save(conversation);
        conversationRepository.findAll().forEach(System.out :: println);

        return conversation;
    }

    @CrossOrigin(origins="*")
    @GetMapping("/getConverstions/{conversationsId}")
    public List<Conversation> getAllSavedConversations(@PathVariable String conversationsId){

        System.out.println(conversationRepository.findAllById(Collections.singleton(conversationsId)));
        System.out.println(conversationRepository.findAll());
        return conversationRepository.findAllById(Collections.singleton(conversationsId));

    }

    @CrossOrigin(origins="*")
    @PostMapping("/conversations/{conversationId}")
    public void addMessagetoConverstion(@PathVariable String conversationId, @RequestBody ChatMessage chat) {

        Profile profile = profileRepository.findById(chat.senderProfileId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + chat.senderProfileId()
                ));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find a conversation with ID" + conversationId));

        ChatMessage chatMessageUpdated = new ChatMessage(
                chat.messageText(),
                chat.senderProfileId(),
                LocalDateTime.now()
        );

        conversation.chatMessageList().add(chat);
        conversationRepository.save(conversation);
    }


        public record ConversationRequest(String profileId){}
}

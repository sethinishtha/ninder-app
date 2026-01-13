package io.projects.ninder_ai_backend;

import io.projects.ninder_ai_backend.conversations.ConversationRepository;
import io.projects.ninder_ai_backend.profiles.ProfileCreationService;
import io.projects.ninder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private ProfileCreationService profileCreationService;

    public static void main(String[] args) {

		SpringApplication.run(NinderAiBackendApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		clearAllData();

//		ChatResponse response = chatModel.call(
//				new Prompt(
//						"Generate the names of 5 famous pirates.",
//						OpenAiChatOptions.builder()
//								.model("gpt-4o-mini")
//								.temperature(0.4)
//								.build()
//				));

		profileCreationService.saveProfilesToDB();
	}

	private void clearAllData() {
		profileRepository.deleteAll();
		conversationRepository.deleteAll();
	}
}

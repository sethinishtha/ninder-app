package io.projects.ninder_ai_backend.conversations;

import com.mongodb.MongoCredential;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepository extends MongoRepository<Conversation,String> {
}

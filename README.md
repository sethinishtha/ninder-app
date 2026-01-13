# Ninder AI Backend

An AI-powered dating app backend that generates realistic dating profiles and enables intelligent conversations with AI personas using OpenAI's GPT models.
The application creates diverse profiles with unique Myers-Briggs personality types, bios, 
and AI-generated images, allowing users to chat with these personas in a contextual manner. 

## 🚀 Features

- **AI Profile Generation**: Automatically creates diverse dating profiles with personality types, bios, and AI-generated images
- **Intelligent Conversations**: Chat with AI personas that respond based on their personality type and bio
- **Image Generation**: Integrates with Stable Diffusion for photorealistic profile pictures
- **MongoDB Storage**: Persistent storage for profiles and conversations
- **Myers-Briggs Personality Types**: Each profile has a unique MBTI personality that influences their communication style

## 🛠️ Tech Stack

- **Java 22** with preview features enabled
- **Spring Boot 3.2.5**
- **Spring AI** (OpenAI & Ollama integration)
- **MongoDB** for data persistence
- **Docker Compose** for container orchestration
- **Maven** for dependency management

## 📋 Prerequisites

- Java 22 or higher
- Maven 3.x
- Docker and Docker Compose
- OpenAI API key
- Stable Diffusion API endpoint (optional, for image generation)

2. **Configure application properties**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.application.name=ninder-ai-backend
   spring.ai.openai.api-key=YOUR_OPENAI_API_KEY
   
   # Set to true to generate initial profiles
   startup-actions.initializeProfiles=true
   
   # Gender preference for generated profiles
   tinderai.lookingForGender=man
   
   # Configure your user profile
   tinderai.character.user={id:'user', firstName:'YourName', lastName:'LastName', age:25, ethnicity:'Indian', gender:'FEMALE', bio:'Your bio here', imageUrl:'', myersBriggsPersonalityType:'INTP'}
   ```
**For now these details are hardcoded but with time, it will be user entered and on the basis of that the conversations will be done.**
 


## 🤖 How It Works

### Profile Generation

1. The system generates diverse profiles with random combinations of:
   - Age (20-35)
   - Ethnicity (White, Black, Asian, Indian, Hispanic)
   - Myers-Briggs personality types (16 types)
   - Gender (configurable based on preference)

2. OpenAI generates realistic bios and profile details using function calling

3. Stable Diffusion API creates photorealistic profile images

4. Profiles are saved to `profiles.json` and MongoDB

### Conversation System

1. When a user sends a message, the system:
   - Retrieves the profile being chatted with
   - Builds conversation history with proper message roles (User/Assistant)
   - Creates a detailed system prompt describing the AI persona
   - Calls OpenAI to generate a contextual response

2. The AI responds as the persona, considering:
   - Age, ethnicity, and gender
   - Bio and interests
   - Myers-Briggs personality type
   - Conversation history and context

## 🏗️ Project Structure

```
src/main/java/io/projects/ninder_ai_backend/
├── NinderAiBackendApplication.java    # Main application entry point
├── Utils.java                          # Helper utilities
├── profiles/
│   ├── Profile.java                    # Profile entity
│   ├── ProfileController.java          # Profile REST endpoints
│   ├── ProfileCreationService.java     # AI profile generation logic
│   ├── ProfileRepository.java          # MongoDB repository
│   └── Gender.java                     # Gender enum
└── conversations/
    ├── Conversation.java               # Conversation entity
    ├── ConversationController.java     # Conversation REST endpoints
    ├── ConversationService.java        # AI conversation logic
    ├── ConversationRepository.java     # MongoDB repository
    └── ChatMessage.java                # Chat message record
```

## Future Enhancements
- Frontend integration for a complete dating app experience
- User authentication and profile management. Using MFA for enhanced security 
- Prompts optimization for better AI responses/engagement
- Enhanced AI persona customization
- More robust error handling and logging

## 🔐 Security Notes

⚠️ **Important**: The current `application.properties` contains an exposed API key. For production:
- Use environment variables for sensitive data
- Never commit API keys to version control
- Use Spring Boot's configuration encryption
- Consider using a secrets management service


## 🧪 Testing

Run tests with:
```bash
./mvnw test
```


## 🙏 Acknowledgments

- Spring AI for AI integration framework
- OpenAI for GPT models
- Stable Diffusion for image generation


# 🔥 Ninder AI Backend

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?style=for-the-badge&logo=springboot)
![Java](https://img.shields.io/badge/Java-22-orange?style=for-the-badge&logo=openjdk)
![MongoDB](https://img.shields.io/badge/MongoDB-Latest-green?style=for-the-badge&logo=mongodb)
![OpenAI](https://img.shields.io/badge/OpenAI-Powered-412991?style=for-the-badge&logo=openai)

*An AI-powered dating app backend that generates realistic profiles and conversations* ✨

[Quick Start](#-quick-start) • [API Reference](#-api-reference) • [Architecture](#-architecture) • [Configuration](#%EF%B8%8F-configuration)

</div>

---

## 🚀 Quick Start

<details open>
<summary><b>Prerequisites</b></summary>

- [ ] Java 22 installed
- [ ] Docker & Docker Compose
- [ ] OpenAI API key

</details>

```bash
# 1️⃣ Start services
docker compose up -d

# 2️⃣ Run the app
./mvnw spring-boot:run -Dmaven.test.skip=true
```

> 💡 **Tip:** The app runs on `http://localhost:8080` by default

---

## 📁 Project Structure

```
src/main/java/io/projects/ninder_ai_backend/
│
├── 🚀 NinderAiBackendApplication.java   # Entry point
├── 🛠️  Utils.java                        # Helpers (MBTI, selfie prompts)
│
├── 👤 profiles/
│   ├── Profile.java                     # Profile record
│   ├── Gender.java                      # MALE | FEMALE | NON_BINARY
│   ├── ProfileController.java           # REST endpoints
│   ├── ProfileRepository.java           # MongoDB repo
│   └── ProfileCreationService.java      # 🤖 AI generation
│
├── 💬 conversations/
│   ├── Conversation.java                # Conversation record
│   ├── ChatMessage.java                 # Message record
│   ├── ConversationController.java      # REST endpoints
│   ├── ConversationRepository.java      # MongoDB repo
│   └── ConversationService.java         # 🤖 AI responses
│
└── ❤️  matches/
    ├── Match.java                       # Match record
    ├── MatchController.java             # REST endpoints
    └── MatchRepository.java             # MongoDB repo
```

---

## 📡 API Reference

### 👤 Profiles

| Method | Endpoint | Description |
|:------:|----------|-------------|
| `GET` | `/getProfiles` | Get all profiles with count |
| `GET` | `/api/profiles/random` | Get a random profile |
| `GET` | `/api/profiles/{id}` | Get profile by ID |

<details>
<summary>📋 <b>Example Response</b></summary>

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "firstName": "Alex",
  "lastName": "Morgan",
  "age": 28,
  "ethnicity": "White",
  "gender": "FEMALE",
  "bio": "Adventure seeker | Coffee addict ☕",
  "imageUrl": "/images/alex.jpg",
  "myersBriggsPersonalityType": "ENFP"
}
```

</details>

### 💬 Conversations

| Method | Endpoint | Description |
|:------:|----------|-------------|
| `POST` | `/conversations` | Create a new conversation |
| `GET` | `/getConversations/{id}` | Get conversation by ID |
| `POST` | `/conversations/{id}` | Send message → Get AI response |

<details>
<summary>📋 <b>Example: Send Message</b></summary>

**Request:**
```bash
curl -X POST http://localhost:8080/conversations/conv-123 \
  -H "Content-Type: application/json" \
  -d '{"messageText": "Hey! How are you?", "senderProfileId": "user"}'
```

**Response:**
```json
{
  "id": "conv-123",
  "receiverProfileId": "profile-456",
  "messages": [
    {"messageText": "Hey! How are you?", "senderProfileId": "user", "localDateTime": "..."},
    {"messageText": "Hey! I'm great 😊 Just got back from hiking!", "senderProfileId": "profile-456", "localDateTime": "..."}
  ]
}
```

</details>

### ❤️ Matches

| Method | Endpoint | Description |
|:------:|----------|-------------|
| `POST` | `/api/matches/create` | Create a new match |
| `GET` | `/api/matches` | Get all matches |
| `GET` | `/api/deleteAllMatches` | Delete all matches |

---

## 🏗️ Architecture

```mermaid
flowchart LR
    subgraph Client
        A[📱 Frontend]
    end
    
    subgraph Backend
        B[🎮 Controllers]
        C[⚙️ Services]
        D[💾 Repositories]
    end
    
    subgraph External
        E[(🍃 MongoDB)]
        F[🤖 OpenAI]
        G[🎨 Stable Diffusion]
    end
    
    A <-->|REST API| B
    B --> C
    C --> D
    D <--> E
    C <-->|Profile & Chat Generation| F
    C <-->|Image Generation| G
```

---

## 📊 Data Models

<details open>
<summary><b>👤 Profile</b></summary>

| Field | Type | Description |
|-------|------|-------------|
| `id` | String | Unique identifier |
| `firstName` | String | First name |
| `lastName` | String | Last name |
| `age` | int | Age (20-35) |
| `ethnicity` | String | White, Black, Asian, Indian, Hispanic |
| `gender` | Gender | MALE, FEMALE, NON_BINARY |
| `bio` | String | Tinder bio |
| `imageUrl` | String | Profile image path |
| `myersBriggsPersonalityType` | String | INTP, ENFJ, etc. |

</details>

<details>
<summary><b>💬 Conversation</b></summary>

| Field | Type | Description |
|-------|------|-------------|
| `id` | String | Unique identifier |
| `receiverProfileId` | String | Matched profile ID |
| `messages` | List\<ChatMessage\> | All messages |

</details>

<details>
<summary><b>📝 ChatMessage</b></summary>

| Field | Type | Description |
|-------|------|-------------|
| `messageText` | String | Message content |
| `senderProfileId` | String | Sender's profile ID |
| `localDateTime` | LocalDateTime | Timestamp |

</details>

---

## ⚙️ Configuration

### 📄 application.properties

```properties
# 🔑 API Keys
spring.ai.openai.api-key=sk-your-key-here

# 🍃 MongoDB
spring.data.mongodb.uri=mongodb://root:secret@localhost:27017/mydatabase?authSource=admin

# 🎮 Features
startup-actions.initializeProfiles=true
tinderai.lookingForGender=man

# 👤 User Profile
tinderai.character.user={id:'user', firstName:'...', ...}
```

### 🐳 Docker Services

| Service | Port | Credentials |
|---------|------|-------------|
| MongoDB | 27017 | root / secret |
| Ollama | 11434 | - |

---

## ✨ Key Features

<table>
<tr>
<td width="50%">

### 🤖 AI Profile Generation
- OpenAI function calling
- 16 Myers-Briggs personality types
- Random demographics (age, ethnicity)
- AI-generated bios

</td>
<td width="50%">

### 🎨 Image Generation
- Stable Diffusion integration
- Photorealistic profile photos
- Multiple selfie poses
- Personality-matched imagery

</td>
</tr>
<tr>
<td width="50%">

### 💬 Smart Conversations
- Context-aware responses
- Personality-driven chat style
- Natural Tinder-like messages
- Conversation history

</td>
<td width="50%">

### 🔧 Tech Stack
- Spring Boot 3.2.5
- Spring AI Framework
- MongoDB persistence
- Java 22 (preview features)

</td>
</tr>
</table>

---

## 🐛 Troubleshooting

<details>
<summary><b>❌ MongoDB Connection Failed</b></summary>

Check the current MongoDB port:
```bash
docker ps | grep mongo
```
Update `application.properties` with the correct port.

</details>

<details>
<summary><b>❌ ClassNotFoundException</b></summary>

Rebuild with Java 22:
```bash
./mvnw clean compile -Dmaven.test.skip=true
```

</details>

<details>
<summary><b>❌ Preview Features Error</b></summary>

Ensure Java 22 is being used:
```bash
java -version  # Should show 22.x.x
```

The `mvnw` script is configured to use the correct Java version automatically.

</details>

---

## 📚 External Services

| Service | Purpose | Required |
|---------|---------|:--------:|
| 🤖 OpenAI API | Profile generation, chat responses | ✅ |
| 🎨 Stable Diffusion | Profile image generation | ⚠️ Optional |
| 🍃 MongoDB | Data persistence | ✅ |
| 🦙 Ollama | Local LLM alternative | ⚠️ Optional |

---

<div align="center">

**Built with ❤️ using Spring Boot & OpenAI**

</div>


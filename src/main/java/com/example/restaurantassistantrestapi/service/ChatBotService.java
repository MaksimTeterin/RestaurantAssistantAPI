package com.example.restaurantassistantrestapi.service;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class ChatBotService {
    private final ChatClient chatClient;

    private final BookingService BookingService;
    private final RestaurantService RestaurantService;
    private final RestaurantTableService RestaurantTableService;
    private final UserService UserService;
    private final BusinessDayService businessDayService;


    public ChatBotService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, BookingService bookingService, RestaurantService restaurantService, RestaurantTableService restaurantTableService, UserService userService, BusinessDayService businessDayService){
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
        this.BookingService = bookingService;
        this.RestaurantService = restaurantService;
        this.RestaurantTableService = restaurantTableService;
        this.UserService = userService;
        this.businessDayService = businessDayService;
    }



    MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
            .maxMessages(20)
            .build();

    public String getResponse(String prompt, int restaurantId, String userEmail){
        System.out.println("Email for response: " + userEmail);
        var response = chatClient.prompt()
                .system("You are an assistant for Restaurant ID " + restaurantId + ". " +
                        "When calling tools, always use this ID.")
                .system("You are a helpful restaurant assistant, that talks to the user and helps them only with their questions about the restaurant. You can also provide information about tables, restaurant and provide booking options and actually create bookings. Give short and concise answers. Do not share other users data to the user or any other data that might be sensitive.")
                .user(prompt)
                .tools(new AIToolService(BookingService, RestaurantTableService, RestaurantService, UserService, businessDayService))
                .toolContext(Map.of("restaurantId", restaurantId, "userEmail", userEmail))
                .options(OpenAiChatOptions.builder()
                        .model("gpt-4o")
                        .temperature(0.0)
                        .toolChoice("auto")
                        .build())
                .call();
        return response.content();
    }

}

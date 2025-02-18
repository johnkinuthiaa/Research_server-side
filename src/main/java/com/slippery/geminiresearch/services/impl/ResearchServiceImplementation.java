package com.slippery.geminiresearch.services.impl;

import com.slippery.geminiresearch.dto.ResearchDto;
import com.slippery.geminiresearch.models.UserRequest;
import com.slippery.geminiresearch.services.ResearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ResearchServiceImplementation implements ResearchService {

    private final WebClient webClient;

    public ResearchServiceImplementation(WebClient.Builder webclientBuilder) {
        this.webClient = webclientBuilder.build();
    }

    @Override
    public ResearchDto processContent(UserRequest userRequest) {
        ResearchDto researchDto =new ResearchDto();
        String createdPrompt =buildPrompt(userRequest);
        Map<String,Object> requestBody = Map.of(
                "contents",new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text",createdPrompt)
                        })
                }
        );
        String response =webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyD0IHwnimrFZkuLfbbXcO26dh3dbvkwmCU")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        researchDto.setMessage(response);
        researchDto.setStatusCode(200);
        return researchDto;
    }

    @Override
    public String buildPrompt(UserRequest userRequest) {
        StringBuilder prompt =new StringBuilder();
        switch (userRequest.getOperation()){
            case "sumarize":
                prompt.append("Provide a clear and concise summary of the following content \n\n");
                break;
            case "suggest":
                prompt.append("Baser on the following content,suggest related topics and format");
                break;
            default:
                throw new IllegalArgumentException("unknown operation" +userRequest.getOperation());
        }
        prompt.append(userRequest.getContent());
        return prompt.toString();
    }
}

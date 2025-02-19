package com.slippery.geminiresearch.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slippery.geminiresearch.dto.ResearchDto;
import com.slippery.geminiresearch.models.UserRequest;
import com.slippery.geminiresearch.services.ResearchService;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
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
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> requestBody = Map.of(
                "contents",new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text",createdPrompt)
                        })
                }
        );
        String response =webClient.post()
                .uri("")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode candidatesNode = rootNode.path("candidates");

            // Iterate through candidates
            for (JsonNode candidateNode : candidatesNode) {
                JsonNode contentNode = candidateNode.path("content");
                JsonNode partsNode = contentNode.path("parts");

                // Iterate through parts
                for (JsonNode partNode : partsNode) {
                    String text = partNode.path("text").asText();
                    researchDto.setMessage(text);
                    researchDto.setStatusCode(200);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return researchDto;
    }

    @Override
    public String buildPrompt(UserRequest userRequest) {
        StringBuilder prompt =new StringBuilder();
        switch (userRequest.getOperation()){
            case "summarize":
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

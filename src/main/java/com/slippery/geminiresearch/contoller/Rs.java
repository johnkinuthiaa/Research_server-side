package com.slippery.geminiresearch.contoller;

import com.slippery.geminiresearch.dto.ResearchDto;
import com.slippery.geminiresearch.models.UserRequest;
import com.slippery.geminiresearch.services.ResearchService;
import com.slippery.geminiresearch.services.impl.ResearchServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/research")
public class Rs {
    private final ResearchService researchService;
    public Rs(ResearchService researchService) {
        this.researchService = researchService;
    }

    @PostMapping("/process")
    public ResponseEntity<ResearchDto> post(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(researchService.processContent(userRequest));
    }
}
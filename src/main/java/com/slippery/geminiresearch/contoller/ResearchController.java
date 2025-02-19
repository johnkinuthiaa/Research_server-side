package com.slippery.geminiresearch.contoller;

import com.slippery.geminiresearch.dto.ResearchDto;
import com.slippery.geminiresearch.models.UserRequest;
import com.slippery.geminiresearch.services.ResearchService;
import com.slippery.geminiresearch.services.impl.ResearchServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/research")
@CrossOrigin("http://localhost:5173/")
public class ResearchController {
    private final ResearchService researchService;

    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
    }

    @PostMapping("/process")
    public ResponseEntity<ResearchDto> post(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(researchService.processContent(userRequest));
    }
}

package com.slippery.geminiresearch.services;

import com.slippery.geminiresearch.dto.ResearchDto;
import com.slippery.geminiresearch.models.UserRequest;

public interface ResearchService {
    ResearchDto processContent(UserRequest userRequest);
    String buildPrompt(UserRequest userRequest);
}

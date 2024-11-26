package ai_studies_emsi.controller;

import ai_studies_emsi.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
class ChatController {
    @Autowired
    private ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<String> getResponse(@RequestBody String question) {
        String response = chatbotService.findResponse(question);
        return ResponseEntity.ok(response);
    }
}

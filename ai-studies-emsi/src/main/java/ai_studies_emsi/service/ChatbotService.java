package ai_studies_emsi.service;

import ai_studies_emsi.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ai_studies_emsi.model.Note;

import java.util.List;
import java.util.stream.Collectors;


@Service
 public class ChatbotService {
    @Autowired
    private NoteRepository notesRepository;

    public String findResponse(String userInput) {
        // Get all notes
        List<Note> notes = notesRepository.getAllNotes();

        String userInputLower = userInput.toLowerCase();

        // Search through each note's content
        for (Note note : notes) {
            String content = note.getContent();  // or however you access content in your Note class
            if (content.toLowerCase().contains(userInputLower)) {
                return extractRelevantResponse(content, userInputLower);
            }
        }

        // Try individual words if no exact match
        String[] userWords = userInputLower.split("\\s+");
        for (String word : userWords) {
            for (Note note : notes) {
                String content = note.getContent();  // or however you access content
                if (content.toLowerCase().contains(word)) {
                    return extractRelevantResponse(content, word);
                }
            }
        }

        return "No matching response found";
    }

    private String extractRelevantResponse(String content, String keyword) {
        String[] sentences = content.split("(?<=[.!?])\\s+");

        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(keyword.toLowerCase())) {
                return sentence.trim();
            }
        }

        // If no specific sentence found, return the start of the content
        int endIndex = content.indexOf(".", content.indexOf(keyword)) + 1;
        if (endIndex > 0) {
            return content.substring(0, endIndex).trim();
        }

        return content;
    }
}
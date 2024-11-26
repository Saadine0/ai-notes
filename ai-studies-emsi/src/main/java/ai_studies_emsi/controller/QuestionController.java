package ai_studies_emsi.controller;




import ai_studies_emsi.model.Note;
import ai_studies_emsi.model.QuestionRequest;
import ai_studies_emsi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private NoteService noteService;

    // Endpoint to add a new note
    @PostMapping("/add-note")
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.addNote(note));
    }

    // POST method for asking a question with JSON input
    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody QuestionRequest questionRequest) {
        String answer = noteService.getAnswer(questionRequest.getQuestion());
        return ResponseEntity.ok(answer);
    }
}

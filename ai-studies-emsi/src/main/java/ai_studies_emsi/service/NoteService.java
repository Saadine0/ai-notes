package ai_studies_emsi.service;

import org.springframework.stereotype.Service;


import ai_studies_emsi.model.Note;
import ai_studies_emsi.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note addNote(Note note) {
        return noteRepository.save(note);  // Save the note to the database
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public String getAnswer(String question) {
        // Example logic for finding a relevant answer
        Optional<Note> note = noteRepository.findById(1L);  // This should be dynamic later
        return note.map(Note::getContent).orElse("No answer found.");
    }

    public String getAnswerToQuestion(String question) {
        List<Note> notes = noteRepository.findAll();
        for (Note note : notes) {
            if (note.getContent().contains(question)) {
                return note.getContent();  // Return the content as an answer
            }
        }
        return "Sorry, I couldn't find an answer to your question.";
    }
}

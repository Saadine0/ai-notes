package ai_studies_emsi.repository;

import ai_studies_emsi.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    default List<Note> getAllNotes() {
        return findAll();
    }
}

package ai_studies_emsi.controller;

import ai_studies_emsi.model.Note;
import ai_studies_emsi.service.NoteService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    // Endpoint for uploading files (PDFs, images, etc.)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if the file is a PDF
            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.endsWith(".pdf")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only PDF files are allowed.");
            }

            // Extract text from the PDF file directly from the input stream
            String extractedText = extractTextFromPDF(file.getInputStream());

            // Create a Note object with the extracted content
            Note note = new Note(fileName, extractedText);
            noteService.addNote(note);  // Save extracted content as a note

            return ResponseEntity.status(HttpStatus.OK).body("File uploaded and text extracted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file.");
        }
    }

    // Helper method to extract text from a PDF InputStream
    private String extractTextFromPDF(InputStream inputStream) throws IOException {
        PDDocument document = PDDocument.load(inputStream);  // Load the PDF from the InputStream
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    // Other CRUD operations for notes (as discussed previously)
    @GetMapping
    public Iterable<Note> getAllNotes() {
        return noteService.getAllNotes();
    }
}

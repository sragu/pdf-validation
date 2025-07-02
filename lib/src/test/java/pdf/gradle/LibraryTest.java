package pdf.gradle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    private String getResourcePath(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        assertNotNull(resource, "Test resource not found: " + fileName);
        return resource.getPath();
    }

    @Test
    @DisplayName("returns false when PDF is encrypted")
    void containsDisallowedActions_givenEncryptedPdf_returnsFalse() {
        String pdfPath = getResourcePath("encrypted.pdf");
        assertFalse(library.containsDisallowedActions(pdfPath), "Encrypted PDF should not be considered compliant");
    }

    @Test
    @DisplayName("returns true when PDF contains a simple disallowed action")
    void containsDisallowedActions_givenPdfWithSimpleDisallowedAction_returnsTrue() {
        String pdfPath = getResourcePath("simple_action.pdf");
        assertTrue(library.containsDisallowedActions(pdfPath), "PDF with a simple disallowed action should be detected");
    }

    @Test
    @DisplayName("returns true when PDF contains multiple disallowed actions")
    void containsDisallowedActions_givenPdfWithMultipleDisallowedActions_returnsTrue() {
        String pdfPath = getResourcePath("multiple_actions.pdf");
        assertTrue(library.containsDisallowedActions(pdfPath), "PDF with multiple disallowed actions should be detected");
    }

    @Test
    @DisplayName("returns false for a compliant PDF with no actions")
    void containsDisallowedActions_givenCompliantPdf_returnsFalse() {
        String pdfPath = getResourcePath("plain.pdf");
        assertFalse(library.containsDisallowedActions(pdfPath), "Compliant PDF should not be flagged");
    }

    @Test
    @DisplayName("throws IllegalArgumentException for a null path")
    void containsDisallowedActions_givenNullPath_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> library.containsDisallowedActions(null));
    }

    @Test
    @DisplayName("throws IllegalArgumentException for an empty path")
    void containsDisallowedActions_givenEmptyPath_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> library.containsDisallowedActions(""));
    }

    @Test
    @DisplayName("throws IllegalArgumentException for a non-existent path")
    void containsDisallowedActions_givenNonExistentPath_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> library.containsDisallowedActions("non_existent_file.pdf"));
    }
}

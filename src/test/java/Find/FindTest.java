package Find;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindTest {
    Find finder = new Find();

    File directoryDefault = new File(new File("").getAbsolutePath());
    File file = new File("NotADirectory");
    File file1 = new File(directoryDefault + "\\Directory1");
    File file2 = new File(file1 + "\\Directory2");
    File file4 = new File("Hello");

    File nameFile1 = new File(file1, "Meow");
    File nameFile2 = new File(file2, "Hehe");
    File nameFile3 = new File(directoryDefault, "PepeTheFrog");

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void fileIsNotExists() {
        assertThrows(FileNotFoundException.class,
                () -> finder.checkingFiles(file4,
                        List.of("PepeTheFrog", "Plan.txt", "Meow", "Hehe", "NotHehe.txt"), true));
    }

    @Test
    void fileIsNotDirectory() {
        assertThrows(IllegalArgumentException.class,
                () -> finder.checkingFiles(file,
                        List.of("PepeTheFrog", "Plan.txt", "Meow", "Hehe", "NotHehe.txt"), true));
    }

    @Test
    void findFiles() throws Exception {
        finder.checkingFiles(directoryDefault, List.of("Meow", "Hehe"), true);
        assertEquals("File path Meow: " + nameFile1.getAbsolutePath() + "\r\n" +
                        "File path Hehe: " + nameFile2.getAbsolutePath(), outputStreamCaptor.toString().trim());
    }

    @Test
    void fileNotFound() throws Exception {
        finder.checkingFiles(directoryDefault, List.of("15.txt"), true);
        assertEquals("File 15.txt not found", outputStreamCaptor.toString().trim());
    }

    @Test
    void onlyDefaultDirectory() throws Exception{
        finder.checkingFiles(directoryDefault, List.of("PepeTheFrog"), false);
        assertEquals("File path PepeTheFrog: " + nameFile3.getAbsolutePath(),
                outputStreamCaptor.toString().trim());
    }

//    @Test
//    void fileFromAnotherDirectory() throws Exception {
//        finder.checkingFiles(file2, List.of("Hehe", "NotHehe.txt"), false);
//        assertEquals("File path Hehe: " + nameFile2.getAbsolutePath() + "\r\n" +
//                        "File NotHehe.txt not found",
//                outputStreamCaptor.toString().trim());
//    }
}

package Find;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindTest {
    Find finder = new Find();

    File file = new File("NotADirectory");
    File file1 = new File("Directory1");
    File file2 = new File(file1 + "\\Directory2");
    File file3 = new File(file2 + "\\Directory3");
    File file4 = new File("Hello");
    File directoryDefault = new File(new File("").getAbsolutePath());

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
    void findAllFiles() throws Exception {
        String text = tapSystemOut(() -> {
            finder.checkingFiles(directoryDefault,
                    List.of("NotHehe.txt", "Hehe", "Meow", "Plan.txt", "NotADirectory", "PepeTheFrog"), true);
        });
        String name = "NotHehe.txt\r\nHehe\r\nMeow\r\nPlan.txt\r\nNotADirectory\r\nPepeTheFrog";
        assertEquals(name, text.trim());
    }

    @Test
    void oneDirectory() throws Exception {
        String text = tapSystemOut(() -> {
            finder.checkingFiles(file1,
                    List.of("Meow", "Plan.txt"), false);
        });
        String name = "Meow\r\nPlan.txt";
        assertEquals(name, text.trim());
    }

    @Test
    void fileNotFound() throws Exception {
        String text = tapSystemOut(() -> {
            finder.checkingFiles(directoryDefault,
                    List.of("15.txt"), true);
        });
        String name = "Файл 15.txt не найден.";
        assertEquals(name, text.trim());
    }

    @Test
    void onlyDefaultDirectory() throws Exception{
        String text = tapSystemOut(() -> {
            finder.checkingFiles(directoryDefault,
                    List.of("NotADirectory", "PepeTheFrog"), true);
        });
        String name = "NotADirectory\r\nPepeTheFrog";
        assertEquals(name, text.trim());
    }

    @Test
    void fileFromAnotherDirectory() throws Exception {
        String text = tapSystemOut(() -> {
            finder.checkingFiles(file2,
                    List.of("Hehe", "NotHehe.txt"), false);
        });
        String name = "Hehe\r\nФайл NotHehe.txt не найден.";
        assertEquals(name, text.trim());
    }
}

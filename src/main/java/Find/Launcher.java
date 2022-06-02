package Find;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Launcher {

    //Искать ли в поддиректориях?
    @Option(name = "-r", metaVar = "subdirectory", usage = "Search in subdirectories")
    private boolean subdirectory;

    //Задаваемая директория
    @Option(name = "-d", metaVar = "directory", usage = "Directory search")
    private File directory;

    //Список искомых файлов
    @Argument(metaVar = "fileName", required = true, usage = "Input file name")
    private List<String> fileNames;

    //Запускает функцию launch
    public static void main(String[] args) {
        new Launcher().launch(args);
    }

    private void launch(String[] args) {
        //Разборщик аргументов командной строки
        CmdLineParser parser = new CmdLineParser(this);
        File directoryDefault = new File(new File("").getAbsolutePath());

        try {
            //Разбирает аргументы по полям (subdirectory, directory, fileNames)
            parser.parseArgument(args);

            //Если аргументы не те или их вовсе нет, то эта функция бросает исключение
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Command Line: -r -d directory filename.txt");
            parser.printUsage(System.err);
            return;
        }

        Find finder = new Find();
        try {
            finder.checkingFiles(Objects.requireNonNullElse(directory, directoryDefault), fileNames, subdirectory);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

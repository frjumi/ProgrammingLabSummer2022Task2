package Find;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Find {
    public void fileSearch(File directory, ArrayList<String> fileNames, boolean subdirectory) throws FileNotFoundException {

        if (!directory.exists()) throw new FileNotFoundException("This directory does not exist");
        if (!directory.isDirectory()) throw new IllegalArgumentException("There is no such directory");

        //Добавляем все файлы этой директории в список
        File[] directoryFiles = directory.listFiles();
        if (directoryFiles != null) {
            for (File name : directoryFiles) {

                //Если разрешён поиск в поддиректориях
                if (subdirectory) {
                    if (name.isDirectory()) {
                        fileSearch(name, fileNames, subdirectory);
                    }
                }
                //Если файл - File и есть в списке, то выводим в консоль
                if (name.isFile() && fileNames.contains(name.getName())) {
                    //System.out.println("Путь файла " + name.getName() + ": " + name.getAbsolutePath());
                    returnName(name);
                    fileNames.remove(name.getName());
                }

            }
        }
    }

    //Вывод ненайдённых файлов
    public void checkingFiles(File directory, List<String> fileNames, boolean subdirectory) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>(fileNames);
        fileSearch(directory, list, subdirectory);
        for (String str: list) {
            System.out.println("Файл " + str + " не найден.");
        }
    }

    private void returnName(File file){
        String name = file.getName();
        System.out.println(name);
    }


}

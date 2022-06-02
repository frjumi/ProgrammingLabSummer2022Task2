package Find;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Find {
    public void fileSearch(File directory, List<String> fileNames, ArrayList<File> list, boolean subdirectory) throws FileNotFoundException {

        if (!directory.exists()) throw new FileNotFoundException("This directory does not exist");
        if (!directory.isDirectory()) throw new IllegalArgumentException("There is no such directory");

        //Добавляем все файлы этой директории в список
        File[] directoryFiles = directory.listFiles();
        if (directoryFiles != null) {
            for (File name : directoryFiles) {

                //Если разрешён поиск в поддиректориях
                if (subdirectory) {
                    if (name.isDirectory()) {
                        fileSearch(name, fileNames, list, subdirectory);
                    }
                }
                //Если файл - File и есть в списке, то выводим в консоль
                if (name.isFile() && fileNames.contains(name.getName())) {
                    list.add(name);
                }

            }
        }
    }

    //Вывод ненайдённых файлов
    public void checkingFiles(File directory, List<String> fileNames, boolean subdirectory) throws FileNotFoundException {
        ArrayList<File> list = new ArrayList<>();
        fileSearch(directory, fileNames, list, subdirectory);
        comparison(list, fileNames);
    }

    private void comparison(ArrayList<File> new_list, List<String> list) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (File file: new_list) {
            hashMap.put(file.getName(), file.getAbsolutePath());
        }
        for (String str : list) {
            if (hashMap.containsKey(str)) System.out.println("File path " + str + ": " + hashMap.get(str));
            else System.out.println("File " + str + " not found");
        }
    }
}

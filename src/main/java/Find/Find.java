package Find;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Find {
    ArrayList<File> list = new ArrayList<>();

    public void fileSearch(File directory, List<String> fileNames, boolean subdirectory) throws FileNotFoundException {
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
                //Если файл - File и есть в списке, то добавляем в список
                if (name.isFile() && fileNames.contains(name.getName())) {
                    list.add(name);
                }

            }
        }
    }

    //Окончательный вывод
    public void checkingFiles(File directory, List<String> fileNames, boolean subdirectory) throws FileNotFoundException {
        fileSearch(directory, fileNames, subdirectory);
        comparison(list, fileNames);
    }

    //Сравнение списков
    private void comparison(ArrayList<File> newList, List<String> list) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (File file: newList) {
            hashMap.put(file.getName(), file.getAbsolutePath());
        }
        //Если файл из основного списка есть в new_list, тогда он найден, иначе нет.
        for (String str : list) {
            if (hashMap.containsKey(str)) System.out.println("File path " + str + ": " + hashMap.get(str));
            else System.out.println("File " + str + " not found");
        }
    }
}

package bay.university.resmon.utils;

import bay.university.resmon.model.Archive;
import bay.university.resmon.model.Measurement;
import bay.university.resmon.repository.ArchiveRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
public class ArchiveUtil {

    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ArchiveRepository archiveRepository;

    public void saveDataToZipArchive(List<Measurement> measurements, String archiveName) throws IOException {
        String folderPathInContainer = "/app/data";
        Path path = Paths.get(folderPathInContainer);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Archive archive = new Archive();
        archive.setArchiveName(archiveName);
        archive.setCreatedAt(new Date());
        try {
            archive.setArchivePath(path + "\\" + archiveName.replace(" ", "_") + ".zip");
            // Создание zip-архива
            FileOutputStream fos = new FileOutputStream(path + "\\" + archiveName.replace(" ", "_") + ".zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            // Создание объекта Gson
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Сериализация объекта в JSON строку
            String json = gson.toJson(measurements);

            // Добавление JSON строки в zip-архив
            ZipEntry jsonEntry = new ZipEntry("data.json");
            zipOut.putNextEntry(jsonEntry);
            zipOut.write(json.getBytes());
            zipOut.closeEntry();

            // Закрытие ZipOutputStream
            zipOut.close();
            archiveRepository.save(archive);
        } catch (IOException e) {
            System.out.println("Ошибка при записи JSON и создании архива: " + e.getMessage());
        }
    }

    public boolean deleteArchive(String archivePath) {
        String filePath = archivePath; // Укажите путь к вашему архиву и его имя
        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Архив успешно удален: " + archivePath);
                return true;
            } else {
                System.out.println("Не удалось удалить архив: " + archivePath);
                return false;
            }
        } else {
            System.out.println("Файл архива не существует: " + archivePath);
            return false;
        }
    }
}

package bay.university.resmon.controller;

import bay.university.resmon.model.Archive;
import bay.university.resmon.model.Measurement;
import bay.university.resmon.repository.ArchiveRepository;
import bay.university.resmon.utils.ArchiveUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArchiveController {

    @Autowired
    ArchiveRepository archiveRepository;
    @Autowired
    ArchiveUtil archiveUtil;

    @GetMapping("/archives")
    public List<Archive> getAllArchives() {
        return archiveRepository.findAll();
    }

    @PostMapping("/archive/{archiveName}")
    public void createArchive(@PathVariable String archiveName, @RequestBody List<Measurement> measurements) {
        try {
            System.out.println("createArchive");
            archiveUtil.saveDataToZipArchive(measurements, archiveName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/downloadArchive")
    public ResponseEntity<FileSystemResource> downloadArchive(@RequestBody Archive archive, HttpServletResponse response) {
        String filePath = archive.getArchivePath(); // Укажите путь к вашему архиву и его имя
        File file = new File(filePath);

        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archive.getArchiveName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(org.springframework.http.MediaType.parseMediaType("application/zip"))
                    .body(new FileSystemResource(file));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/deleteArchive/{id}")
    public void deleteArchive(@PathVariable String id) {
        archiveUtil.deleteArchive(archiveRepository.findFirstById(id).getArchivePath());
    }
}

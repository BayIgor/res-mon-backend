package bay.university.resmon.controller;

import bay.university.resmon.model.Measurement;
import bay.university.resmon.repository.MeasurementRepository;
import bay.university.resmon.utils.ExcelUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MeasurementController {

    @Autowired
    private MeasurementRepository mRepository;
    @Autowired
    private ExcelUtil excelUtil;

    @GetMapping("/measurements")
    public ResponseEntity getMeasurements(){
        return ResponseEntity.ok(new HttpEntity<>(mRepository.findAll()));
    }

    @PostMapping("/measurements")
    public Measurement createMeasurement(@RequestBody Measurement measurement){
        return mRepository.save(measurement);
    }

    @PostMapping("/measurements/excel")
    public void createMeasurementFromExcel(@RequestParam("file") MultipartFile file){
        excelUtil.readExcelFile(file);
    }

    @PostMapping("/report/{reportName}")
    public ResponseEntity<FileSystemResource> createReport(
            @PathVariable String reportName,
            @RequestBody List<Map<String, Object>> reportData) {
        String path = excelUtil.createReport(reportName, reportData);
        String filePath = path;
        File file = new File(filePath);

        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx");

            ResponseEntity<FileSystemResource> responseEntity = ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(org.springframework.http.MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new FileSystemResource(file));

            return responseEntity;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/measurements/{id}")
    public void deleteMeasurement(@PathVariable String id){
        mRepository.deleteById(id);
    }
}

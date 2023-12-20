package bay.university.resmon.utils;

import bay.university.resmon.model.Measurement;
import bay.university.resmon.repository.MeasurementRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ExcelUtil {

    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    private MeasurementRepository measurementRepository; // Предполагается, что у вас есть репозиторий для работы с Measurement

    public void readExcelFile(MultipartFile multipartFile) {
        try {
            Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Measurement measurement = new Measurement();

                measurement.setMeterId(String.valueOf((int) row.getCell(1).getNumericCellValue()));

                String dateString = String.valueOf(row.getCell(2).getDateCellValue());
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                try {
                    Date date = dateFormat.parse(dateString);
                    measurement.setMeasDateTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                measurement.setHour(String.valueOf((int) row.getCell(3).getNumericCellValue()));
                measurement.setCoolantFlowV1(String.valueOf(row.getCell(4).getNumericCellValue()));
                measurement.setCoolantFlowV2(String.valueOf(row.getCell(5).getNumericCellValue()));
                measurement.setCoolantTemperatureT1(String.valueOf(row.getCell(6).getNumericCellValue()));
                measurement.setCoolantTemperatureT2(String.valueOf(row.getCell(7).getNumericCellValue()));
                measurement.setInstantConsumptionG1(String.valueOf(row.getCell(8).getNumericCellValue()));
                measurement.setInstantConsumptionG2(String.valueOf(row.getCell(9).getNumericCellValue()));
                measurement.setHeatConsumptionQ1(String.valueOf(row.getCell(10).getNumericCellValue()));
                measurement.setHeatConsumptionQ2(String.valueOf(row.getCell(11).getNumericCellValue()));
                measurement.setTimerWorkHour(String.valueOf(row.getCell(12).getNumericCellValue()));
                measurement.setConsumption(String.valueOf(row.getCell(13).getNumericCellValue()));

                measurementRepository.save(measurement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createReport(String reportName,
                             List<Map<String, Object>> reportData){
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Report Data");

            Set<String> keys = reportData.get(0).keySet();

            Row headerRow = sheet.createRow(0);
            int headerCellNum = 0;
            for (String key : keys) {
                if (!key.equalsIgnoreCase("id")) {
                    Cell headerCell = headerRow.createCell(headerCellNum++);
                    headerCell.setCellValue(key);
                }
            }

            int rowNum = 1;
            for (Map<String, Object> row : reportData) {
                Row sheetRow = sheet.createRow(rowNum++);
                int cellNum = 0;
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    if (!entry.getKey().equalsIgnoreCase("id")) {
                        Cell cell = sheetRow.createCell(cellNum++);
                        if (entry.getValue() instanceof String) {
                            cell.setCellValue((String) entry.getValue());
                        } else if (entry.getValue() instanceof Double) {
                            cell.setCellValue((Double) entry.getValue());
                        }
                    }
                }
            }

            String folderPathInContainer = "/app/data";
            Path path = Paths.get(folderPathInContainer);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String filePath = path + "/report.xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
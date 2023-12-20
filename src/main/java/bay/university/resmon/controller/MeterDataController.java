package bay.university.resmon.controller;

import bay.university.resmon.dto.StatisticsResponse;
import bay.university.resmon.model.MeterData;
import bay.university.resmon.repository.MeasurementRepository;
import bay.university.resmon.repository.MeterDataRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MeterDataController {

    @Autowired
    private MeterDataRepository meterDataRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    @GetMapping("/meters")
    public ResponseEntity getMeterData(){
        return ResponseEntity.ok(new HttpEntity<>(meterDataRepository.findAll()));
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> getStatistics(){
        try{
            return ResponseEntity.ok(new StatisticsResponse(measurementRepository.count(),
                    meterDataRepository.count()));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/meters")
    public MeterData createMeterData(@RequestBody MeterData meterData){
        return meterDataRepository.save(meterData);
    }

    @DeleteMapping("/meters/{id}")
    public void deleteMeterData(@PathVariable String id){
        meterDataRepository.deleteById(id);
    }
}

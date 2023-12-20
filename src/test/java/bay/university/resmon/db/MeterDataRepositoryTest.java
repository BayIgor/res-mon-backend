package bay.university.resmon.db;

import bay.university.resmon.model.MeterData;
import bay.university.resmon.repository.MeterDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
public class MeterDataRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MeterDataRepository meterDataRepository;

    @Test
    public void shouldCreateContext(){
        MeterData data = new MeterData();
        data.setMeterId(1);
        data.setInstallationDate(new Date());
        data.setAccuracy(true);
        meterDataRepository.save(data);

        assertNotNull(mongoTemplate);
        assertNotNull(meterDataRepository);
    }
}

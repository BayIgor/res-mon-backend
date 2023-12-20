package bay.university.resmon.repository;

import bay.university.resmon.model.MeterData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterDataRepository extends MongoRepository<MeterData, String> {
}

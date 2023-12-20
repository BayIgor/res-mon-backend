package bay.university.resmon.repository;

import bay.university.resmon.model.Archive;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArchiveRepository extends MongoRepository<Archive, String> {

    Archive findFirstById(String id);
}

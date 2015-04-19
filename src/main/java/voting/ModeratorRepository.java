package voting;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by davchen on 3/27/15.
 */

public interface ModeratorRepository extends MongoRepository<Moderator, String> {

//    public Moderator findById(String id);
}



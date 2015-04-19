package voting;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.ArrayList;

/**
 * Created by davchen on 3/27/15.
 */
public interface PollRepository extends MongoRepository<Poll, String>{

    @Query (fields = "{'moderatorId' : 0}")
    public ArrayList<Poll> findByModeratorId(String id);

    @Query (fields = "{'results' : 0}")
    public Poll findById(String id);
}

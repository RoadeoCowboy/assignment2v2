package voting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import kafka.*;



/**
 * Created by davchen on 3/27/15.
 */

@RestController
@RequestMapping(value="/api/v1/")
public class Controller {
    @Autowired
    private ModeratorRepository modRepo;
    @Autowired
    private PollRepository pollRepo;

    @RequestMapping(value = "/moderators", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public
    @ResponseBody
    Moderator createModerator(
            @Valid
            @RequestBody Moderator moderator) {

        int id = (int) (Math.random() * 999999) + 100000;
        String id_string = Integer.toString(id);

        moderator.setCreated_at(new Date());
        moderator.setId(id_string);
        modRepo.save(moderator);
        return moderator;
    }

    @RequestMapping(value = "moderators/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Moderator getModerator(@PathVariable String id) {
        Moderator mod = modRepo.findOne(id);

        if (mod != null) {
            System.out.println("Key Found!");
            return mod;
        } else {
            System.out.println("Key not found!");
            return null;
        }
    }

    @RequestMapping(value = "/moderators/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Moderator setModerator(
            @PathVariable(value = "id") String id,
            @Valid
            @RequestBody Moderator moderator
    ) {
        Moderator updateModerator = modRepo.findOne(id);
        updateModerator.setEmail(moderator.getEmail());
        updateModerator.setPassword(moderator.getPassword());
        modRepo.save(updateModerator);
        return updateModerator;
    }

    @RequestMapping(value = "/moderators/{id}/polls", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public
    @ResponseBody
    Poll createPoll(
            @PathVariable(value = "id") String id,
            @Valid
            @RequestBody Poll poll
    ) {
        Moderator moderator = modRepo.findOne(id);
        String moderatorId = moderator.getId();

        int id_int = (int) (Math.random() * 999999) + 100000;
        System.out.println("Base 10 id_string is: " + id_int);
        String id_string = Integer.toString(id_int, 36);
        System.out.println("Base 36 id_string is: " + id_string);

        poll.setId(id_string);
        poll.setModeratorId(moderatorId);
        pollRepo.save(poll);
        poll.setModeratorId(null);
        return poll;
    }

    @RequestMapping(value = "/polls/{poll_id}", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Poll getPollNoResult(@PathVariable String poll_id) {
        Poll poll = pollRepo.findById(poll_id);
        poll.setModeratorId(null);
        return poll;
    }

    @RequestMapping(value = "/polls/{poll_id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public Poll vote(
            @PathVariable String poll_id,
            @Valid
            @RequestParam(value = "choice", required = true) int choice_index) {
        Poll pollVote;

        System.out.println("Start");

        if (pollRepo.exists(poll_id)) {
            System.out.println("Step 1");
            pollVote = pollRepo.findOne(poll_id);
//            int[] resultArray = new int[pollVote.getResults().length];
            System.out.println("Step 2");
            int[] resultArray;
            resultArray = pollVote.getResults();
            System.out.println("Step 3");
            resultArray[choice_index] = resultArray[choice_index] + 1;
            System.out.println("Step 4");
            pollRepo.save(pollVote);
            System.out.println("Step 5");
            return pollVote;
        } else {
            System.out.println("poll_id not found!");
            return null;
        }
    }

    @RequestMapping(value = "/moderators/{moderator_id}/polls/{poll_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Poll getPollwithResult(
            @PathVariable String moderator_id,
            @PathVariable String poll_id
    ) {
        Poll poll = new Poll(); //new poll instance to return.
        ArrayList<Poll> pollList = pollRepo.findByModeratorId(moderator_id);
        if (modRepo.exists(moderator_id) && pollRepo.exists(poll_id)) {
            for (int i = 0; i < pollList.size(); i++) {
                if ((poll_id).equals(pollList.get(i).getId())) {
                    poll = pollList.get(i);
                }
            }
        }
        return poll;
    }

    @RequestMapping(value = "/moderators/{moderator_id}/polls", method = RequestMethod.GET)
    public
    @ResponseBody
    ArrayList<Poll> getAllPoll(
            @PathVariable String moderator_id
    ) {
        ArrayList<Poll> pollList = pollRepo.findByModeratorId(moderator_id);
        return pollList;
    }

    @RequestMapping(value = "/moderators/{moderator_id}/polls/{poll_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePoll(
            @PathVariable String moderator_id,
            @PathVariable String poll_id
    ) {
        if (modRepo.exists(moderator_id)) {
            pollRepo.delete(poll_id);
            System.out.println("Successfully Deleted: " + "moderator " + moderator_id + " poll " + poll_id);
        }
    }

    @Scheduled(fixedRate = 5000*60) //for 5 minutes.
    public void pollLookup() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date currentDate = new Date();
        System.out.println("Current Date in pollLookup is: " + dateFormat.format(currentDate));


        List<Poll> currentPolls = pollRepo.findAll();
//        for (Poll temp : currentPolls) {
//            Date tempDate = dateformat.parse(temp.getExpired_at()); //Assigning a Date object tempDate
//            if (tempDate.compareTo(currentDate) < 0) {
//                System.out.println("Sent to Kafka!");
//                //Send to Kafka.
//            }
//        }

        Iterator<Poll> PollIterator = currentPolls.iterator();
        while(PollIterator.hasNext()){
            Poll pollCheck = PollIterator.next();
            Date tempDate = dateFormat.parse(pollCheck.getExpired_at()); //Assigning a Date object tempDate
            if (tempDate.compareTo(currentDate) < 0) {
                System.out.println("Sent to Kafka!");
                System.out.println("Expired Poll: " + pollCheck.getExpired_at());
                Moderator moderator = modRepo.findOne(pollCheck.getModeratorId());  //Mapping between Moderator and Poll
                String email = moderator.getEmail();
                int[] result = pollCheck.getResults();
                String[] choice = pollCheck.getChoice();

                String message = email + ":" + "005519839:Poll Result [" + choice[0] + "="
                        + result[0] + "," + choice[1] + "=" + result[1] + "]";

                System.out.println(message);
                String topic = "cmpe273-topic";
//                SimpleProducer producer = new SimpleProducer();
                SimpleProducer.sendMessage(topic, message);
                //Send to Kafka.
            }
        }

    }
}


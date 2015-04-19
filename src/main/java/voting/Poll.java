package voting;

/**
 * Created by davchen on 3/28/15.
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Poll {
    String id;
//    @NotNull

    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)   //Connects to Poll Repository to hide moderatorId data.
    String moderatorId;
//    @NotNull
    String question;
//    @NotNull
    String started_at;
//    @NotNull
    String expired_at;
//    @NotNull
    String[] choice;

    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)   //Connects to Poll Repository to hide result data.
    public int[] results;

//    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss");

    Date date;

    public Poll(){

    }

    public Poll(String id, String moderatorId, String question, String started_at, String expired_at, String[] choice, int[] results){
        //overloaded constructor
        this.id = id;
        this.moderatorId = moderatorId;
        this.question = question;
        this.started_at = started_at;
        this.expired_at = expired_at;
        this.choice = choice;
        //this.results = results;
        setArray(choice.length);
    }

    public Poll(Poll poll){
        this.id = poll.id;
        this.question = poll.question;
        this.started_at = poll.started_at;
        this.expired_at = poll.expired_at;
        this.choice = poll.choice;
        this.results = poll.results;
        this.moderatorId = poll.moderatorId;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getModeratorId() { return moderatorId; }

    public void setModeratorId(String moderatorId) { this.moderatorId = moderatorId; }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getStarted_at(){
        return started_at;
    }

    public void setStarted_at(Date date){
        this.started_at = started_at;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss");
        this.started_at = dateFormat.format(date);
//        started_at = start;
    }

    public String getExpired_at(){
        return expired_at;
    }

//    public void setExpired_at(Date date){ //Commented out for Assignment 2 Kafka
//        this.expired_at = expired_at;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss");
//        this.expired_at = dateFormat.format(date);
//    }

    public void setExpired_at(String expire){
        expired_at = expire;
    }

    public String[] getChoice(){
        return choice;
    }

    public void setChoice(String[] choice){
        this.choice = choice;
        setArray(choice.length);
    }

    public int[] getResults(){
        return results;
    }

    public void setResults(int[] results){
        this.results = results;
    }

    public void setArray(int size){
        results = new int[size];
    }

}



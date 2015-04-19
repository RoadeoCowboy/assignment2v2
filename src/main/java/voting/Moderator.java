package voting;

/**
 * Created by davchen on 3/27/15.
 */

import java.time.Instant;
import java.util.Date;
public class Moderator {

    private String id;
    private String created_at;
    private String name;
    private String email;
    private String password;
    //private Date date;


    //setters
    public void setId(String id) {
        this.id = id;
    }

    public void setCreated_at(Date date) {
        Date data = date;
        Instant s = Instant.ofEpochMilli(data.getTime());
        this.created_at = s.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //getters
    public String getId() {
        return id;
    }
    public String getCreated_at() {
        return created_at;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }


}


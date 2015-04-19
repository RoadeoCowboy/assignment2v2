package voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by davchen on 3/27/15.
 */

@SpringBootApplication
@EnableScheduling
public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World");
        SpringApplication.run(Test.class, args);
    }
}
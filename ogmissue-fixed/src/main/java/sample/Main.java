package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;

/**
 * Created by buchgeher on 05.09.2016.
 */
@SpringBootApplication(exclude = Neo4jDataAutoConfiguration.class)
public class Main {

    public static void main(String args[]) {

        SpringApplication.run(Main.class, args);



    }

}

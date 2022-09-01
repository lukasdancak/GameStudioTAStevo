package sk.tuke.gamestudio.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.SpringClient;
import sk.tuke.gamestudio.minesweeper.PlaygroundJPA;
import sk.tuke.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tuke.gamestudio.service.*;

import javax.persistence.Entity;

@SpringBootApplication
@EntityScan(basePackages = "sk.tuke.gamestudio.entity")
public class GameStudioServer {
    public static void main(String[] args) {

        SpringApplication.run(GameStudioServer.class);
    }


    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
        //return new ScoreServiceJDBC();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();

    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();

    }

    @Bean
    public CountryService countryService() {
        return new CountryServiceJPA();

    }

    @Bean
    public OccupationService occupationService() {
        return new OccupationServiceJPA();
    }

    @Bean
    public PlayerService playerService() {
        return new PlayerServiceJPA();
    }


}


/*
import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }
}
*/


/*
import org.springframework.boot.SpringApplication;
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }
}
*/
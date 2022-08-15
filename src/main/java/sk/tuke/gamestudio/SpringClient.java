package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tuke.gamestudio.minesweeper.PlaygroundJPA;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
public class SpringClient {
    public static void main (String[] args){

       SpringApplication.run(SpringClient.class);
        // new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runnerJPA(PlaygroundJPA console){
        return s -> console.play();
    }

//    @Bean
//    public CommandLineRunner runner(ConsoleUI console){
//        return s -> console.play();
//    }

    @Bean
    public PlaygroundJPA consoleJPA(){return new PlaygroundJPA();}

    @Bean
    public ConsoleUI console(){
        return new ConsoleUI();
    }

    @Bean
    public ScoreService scoreService(){ return new ScoreServiceJPA();    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService(){
        return new RatingServiceJPA();
    }
    @Bean
    public PlayerService playerService() {return new PlayerServiceJPA();}

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

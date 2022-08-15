package sk.tuke.gamestudio.minesweeper;

import sk.tuke.gamestudio.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional // toto hovori, ze operacie v ramci jednej metody tvoria jednu transakciu
public class PlaygroundJPA {

    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    @PersistenceContext
    private EntityManager entityManager;

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void play(){
        System.out.println("Opening JPA playground.");



        //naplnenie tabuliek occupation a country - funguje - naplnlo tabulky s datami
    /*
        //naplnenei tabulky occupations
        ArrayList<String> listOfOccupations =
                new ArrayList<>(Arrays.asList(new String[]{"ziak", "student", "zamestnanec", "zivnostnik",
                        "nezamestnany", "dochodca", "invalid"}));
        for (String s: listOfOccupations){
            entityManager.persist(new Occupation(s));
        }

        //naplnenie tabulky country
        ArrayList<String> listOfCountries =
                new ArrayList<>(Arrays.asList(new String[]{"Slovakia", "Hungary", "Poland", "Czechia",
                        "Ukraine", "Austria"}));
        for (String s: listOfCountries){
            entityManager.persist(new Country(s));
        }
        */






        // veci z hodiny:
      /*  String firstName= zadajFirstName();
        String lastName = zadajLastName();
        int group = zadajGroup();

        List<StudyGroup> studyGroups= entityManager.createQuery("select g from StudyGroup g")
                .getResultList();

        int noOfGroups = studyGroups.size();

        for(int i=0;i<noOfGroups;i++){
            System.out.println(i+" "+studyGroups.get(i));
        }

        entityManager.persist(new Student(firstName,lastName,studyGroups.get(group)));

        List<Student> students= entityManager.createQuery("select s from Student s")
                .getResultList();

        System.out.println(students); */




//        entityManager.persist(new StudyGroup("zakladna"));
//        entityManager.persist(new StudyGroup("mierne pokrocila"));
//        entityManager.persist(new StudyGroup("pokrocila"));

//        String game = "minesweeper";
//        String user = "Jozef";
//        String user2 = "Igor";
//        int ratingValue = 1;

//        entityManager.persist(new Rating(game, user, ratingValue, new Date() ));

//        Rating ratingWrite = null;
//
//
//        try {
//            ratingWrite = (Rating) entityManager.createQuery("select r from Rating r where r.username=:user and r.game=:game")
//                    .setParameter("user", user)
//                            .setParameter("game", game)
//                                    .getSingleResult();
//            ratingWrite.setRating(3);
//            ratingWrite.setRatedOn(new Date());
//        } catch (NoResultException e) {
//           // e.printStackTrace();
//            Rating rating2 = new Rating(game, user2, ratingValue, new Date() );
//            entityManager.persist(rating2);
//            rating2.setRating(5);
//        }


//        entityManager.persist(new Score("minesweeper", "Stevo", 10, new Date()) );
//        entityManager.persist(new Score("minesweeper", "Stevo2", 10, new Date()) );

//        String game = "minesweeper";
//        List<Score> bestScores = entityManager
//                .createQuery("select s from Score s where s.game = :mygame order by s.points desc")
//                .setParameter("mygame", game).getResultList()
//                ;
//        System.out.println(bestScores);


        System.out.println("Closing JPA playground.");
    }

    private int zadajGroup() {
        System.out.println("Zadaj cislo skupiny (1, 2 alebo 3): ");
        String s= this.readLine();
        if(s.equals("1") || s.equals("2") || s.equals("3")){
            return Integer.parseInt(s);
        } else {
            System.out.println("Zly vstup.");
            return this.zadajGroup();
        }

    }

    private String zadajLastName() {
        System.out.println("Zadaj svoje priezvisko (1-64 znakov): ");
        String s= this.readLine();
        if(s.length()>0 && s.length()<=64){
            return s;
        } else {
            System.out.println("Zly vstup.");
            return this.zadajLastName();
        }

    }

    private String zadajFirstName() {
        System.out.println("Zadaj svoje krstne meno (1-64 znakov): ");
        String s= this.readLine();
        if(s.length()>0 && s.length()<=64){
            return s;
        } else {
            System.out.println("Zly vstup.");
            return this.zadajFirstName();
        }
    }

}

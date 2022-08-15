package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(uniqueConstraints =
        {@UniqueConstraint(name = "UniqueUserNameAndFullName", columnNames = { "userName", "fullName" })})
public class Player implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long ident;

    @Column(nullable = false, length=32)
    private String userName;

    @Column(nullable = false, length=128)
    private String fullName;

    @Column(columnDefinition = "INT CHECK(rating BETWEEN 1 AND 10) NOT NULL")
    private int selfEvaluation;

    @ManyToOne
    @JoinColumn(name = "Country.ident")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "Occupation.ident")
    private Occupation occupation;




    @Column(nullable = false, length=64)
    private String game;

    @Column(nullable = false, length=64)
    private String username;

    @Column(columnDefinition = "INT CHECK(rating BETWEEN 1 AND 5) NOT NULL")
    private int rating;

    @Column(nullable = false)
    private Date ratedOn;

    public Occupation getOccupation() {
        return occupation;
    }

    public Country getCountry() {
        return country;
    }

    public Player() {}
    public Player(String game, String username, int rating, Date ratedOn) {
        this.game = game;
        this.username = username;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }



    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", username='" + username + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }


    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }
}

package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints =
        {@UniqueConstraint(name = "UniqueUserNameAndFullName", columnNames = { "userName", "fullName" })})
public class Player implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) // riesenie ID nechavam na databazu, aby som mohol pripadne
    private long ident;                                  // pouzit aj JDBC service


    @Column(nullable = false, length=32)
    private String username;

    @Column(nullable = false, length=128)
    private String fullname;

    @Column(columnDefinition = "INT CHECK(selfevaluation BETWEEN 1 AND 10) NOT NULL")
    private int selfevaluation;

    @ManyToOne
    @JoinColumn(name = "Country.ident")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "Occupation.ident")
    private Occupation occupation;








    public Player() {}

    public Player(String userName, String fullName, int selfEvaluation, Country country, Occupation occupation) {
        this.username = userName;
        this.fullname = fullName;
        this.selfevaluation = selfEvaluation;
        this.country = country;
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Player{" +
                "ident=" + ident +
                ", userName='" + username + '\'' +
                ", fullName='" + fullname + '\'' +
                ", selfEvaluation=" + selfevaluation +
                ", country=" + country +
                ", occupation=" + occupation +
                '}';
    }

    public long getIdent() {
        return ident;
    }

    public void setIdent(long ident) {
        this.ident = ident;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getSelfevaluation() {
        return selfevaluation;
    }

    public void setSelfevaluation(int selfevaluation) {
        this.selfevaluation = selfevaluation;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public Country getCountry() {
        return country;
    }
}

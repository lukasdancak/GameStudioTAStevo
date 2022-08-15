package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(uniqueConstraints =
        {@UniqueConstraint(name = "UniqueUserNameAndFullName", columnNames = { "userName", "fullName" })})
public class Player implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) // riesenie ID nechavam na databazu, aby som mohol pripadne
    private long ident;                                  // pouzit aj JDBC service


    @Column(nullable = false, length=32)
    private String userName;

    @Column(nullable = false, length=128)
    private String fullName;

    @Column(columnDefinition = "INT CHECK(selfEvaluation BETWEEN 1 AND 10) NOT NULL")
    private int selfEvaluation;

    @ManyToOne
    @JoinColumn(name = "Country.ident")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "Occupation.ident")
    private Occupation occupation;






    public Occupation getOccupation() {
        return occupation;
    }

    public Country getCountry() {
        return country;
    }

    public Player() {}

    public Player(String userName, String fullName, int selfEvaluation, Country country, Occupation occupation) {
        this.userName = userName;
        this.fullName = fullName;
        this.selfEvaluation = selfEvaluation;
        this.country = country;
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Player{" +
                "ident=" + ident +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", selfEvaluation=" + selfEvaluation +
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(int selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}

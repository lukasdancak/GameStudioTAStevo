package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Occupation implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) /// riesenie ID nechavam na databazu, aby som mohol pripadne
    private long ident;                                    // pouzit aj JDBC service

    @Column(nullable = false, length=32, unique=true)
    private String occupation;

    public Occupation() {
    }

    public Occupation(String occupation) {
        this.occupation = occupation;
    }

    public long getIdent() {
        return ident;
    }

    public void setIdent(long ident) {
        this.ident = ident;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}

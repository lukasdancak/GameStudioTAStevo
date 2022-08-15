package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Country implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) //riesenie ID nechavam na databazu, aby som mohol pripadne
    private long ident;                                 // pouzit aj JDBC service

    @Column(nullable = false, length=128, unique=true)
    private String country;


    public Country() { }

    public Country(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return country;
    }

    public long getIdent() {
        return ident;
    }

    public void setIdent(long ident) {
        this.ident = ident;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Occupation implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long ident;

    @Column(nullable = false, length=32)
    private String occupation;

}

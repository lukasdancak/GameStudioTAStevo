package sk.tuke.gamestudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue// (strategy = GenerationType.IDENTITY)
    private long ident;

    private String game;
    private String username;

    @Column(length=1000)
    private String comment;

    private Date commentedOn;

    public Comment() { }

    public Comment(String game, String username, String comment, Date commentOn) {
        this.game = game;
        this.username = username;
        this.comment = comment;
        this.commentedOn = commentOn;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "game='" + game + '\'' +
                ", username='" + username + '\'' +
                ", comment=" + comment +
                ", commentOn=" + commentedOn +
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }
}

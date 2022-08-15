package sk.tuke.gamestudio.entity;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private long ident;

    private String firstName;
    private String LastName;

    @ManyToOne
    @JoinColumn(name = "StudyGroup.ident", nullable = false)
    private StudyGroup studyGroup;

    public Student(){

    }

    public Student(String firstName, String lastName, StudyGroup studyGroup) {
        this.firstName = firstName;
        LastName = lastName;
        this.studyGroup = studyGroup;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ident=" + ident +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", studyGroup=" + studyGroup +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
}

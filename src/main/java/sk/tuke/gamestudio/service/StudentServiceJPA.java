package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class StudentServiceJPA {

    @PersistenceContext
    private EntityManager entityManager;

    public void addStudent(Student student) {
        entityManager.persist(student);
    }

    public Student getStudentById(int id) {
        try {
            return (Student) entityManager.createQuery("select g from Student g where ident=:id")
                    .setParameter("id",id)
                    .getSingleResult();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public void updateStudent(int id, Student studentWithUpdatedData) {
        Student studentForUpdate = getStudentById(id);
        studentForUpdate.setFirstName(studentWithUpdatedData.getFirstName());
        studentForUpdate.setLastName(studentWithUpdatedData.getLastName());
        studentForUpdate.setStudyGroup(studentWithUpdatedData.getStudyGroup());
        entityManager.merge(studentForUpdate);

    }

    public void deleteStudent(int id){
        entityManager.remove(getStudentById(id));
    }

    public List<Student> getAllStudents() {
       return  entityManager.createQuery("select g from Student g")
                .getResultList();
    }

    public void reset(){
        entityManager.createNativeQuery("DELETE FROM student").executeUpdate();
    }

}
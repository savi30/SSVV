package student;

import domain.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

public class StudentServiceTest {
    public static Service service;

    @Before
    public void before() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "src/test/resources/fisiere/Studenti.xml";
        String filenameTema = "src/test/resources/fisiere/Teme.xml";
        String filenameNota = "src/test/resources/fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator,
                notaXMLRepository, notaValidator);
    }

    @Test(expected = ValidationException.class)
    public void addStudentInvalidId() {
        Student student = new Student(null, "Jon Doe", 900, "test@mail.com");
        service.addStudent(student);
    }

    @Test
    public void addStudentValidId() {
        Student student = new Student("200", "Jon Doe", 900, "test@mail.com");
        Student result = service.addStudent(student);
        Assert.assertNull(result);
        Student added = service.findStudent("200");
        Assert.assertEquals(student.getID(), added.getID());
        service.deleteStudent("200");
    }

    @Test(expected = ValidationException.class)
    public void addStudentIdEmpty() {
        Student student = new Student("", "Jon Doe", 900, "test@mail.com");
        Student result = service.addStudent(student);
        Assert.assertEquals(student.getID(), result.getID());
    }

    @Test(expected = ValidationException.class)
    public void addStudentNameNull() {
        Student student = new Student("300", null, 900, "test@mail.com");
        Student result = service.addStudent(student);
        Assert.assertEquals(student.getID(), result.getID());
    }

    @Test(expected = ValidationException.class)
    public void addStudentNameEmpty() {
        Student student = new Student("300", "", 900, "test@mail.com");
        Student result = service.addStudent(student);
        Assert.assertEquals(student.getID(), result.getID());
    }

    @Test
    public void addStudentNameValidValue() {
        Student student = new Student("300", "Jon Doe", 900, "test@mail.com");
        Student result = service.addStudent(student);
        Assert.assertEquals(student.getID(), result.getID());
    }

    @Test(expected = ValidationException.class)
    public void addStudentEmailNull() {
        Student student = new Student("300", "Jon Doe", 900, null);
        Student result = service.addStudent(student);
        Assert.assertEquals(student.getID(), result.getID());
    }

    @Test(expected = ValidationException.class)
    public void addStudentEmailEmpty() {
        Student student = new Student("300", "Jon Doe", 900, "");
        Student result = service.addStudent(student);
        Assert.assertEquals(student.getID(), result.getID());
    }

    @Test
    public void addStudentEmailValidValue() {
        Student student = new Student("300", "Jon Doe", 900, "test@mail.com");
        Student result = service.addStudent(student);
        Assert.assertEquals(student.getID(), result.getID());
    }

    @Test
    public void addStudentGroupValid() {
        Student student = new Student("300", "Jon Doe", 900, "test@mail.com");
        Student result = service.addStudent(student);
        Assert.assertEquals(student.getID(), result.getID());
    }

    @Test(expected = ValidationException.class)
    public void addStudentGroupInvalid() {
        Student student = new Student("300", "Jon Doe", -45, "test@mail.com");
        Student result = service.addStudent(student);
        Assert.assertEquals(student.getID(), result.getID());
    }

}

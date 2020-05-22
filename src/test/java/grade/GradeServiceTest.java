package grade;

import domain.Nota;
import domain.Student;
import domain.Tema;
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

import java.time.LocalDate;

public class GradeServiceTest {
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

    @Test
    public void addGradeValid() {
        Nota nota = new Nota("12345", "45", "1", 10, LocalDate.of(2018, 10, 22));
        double result = service.addNota(nota, "test");

        Assert.assertEquals(7.5, result, 0);
        service.deleteNota("12345");
    }

    @Test
    public void addGradeIntegration(){
        Student student = new Student("200", "Jon Doe", 900, "test@mail.com");
        Student result = service.addStudent(student);
        Assert.assertNull(result);

        Tema assignment = new Tema("200", "testValid", 4, 3);
        Tema result1 = service.addTema(assignment);
        Assert.assertNull(result1);


        Nota nota = new Nota("12345", "45", "1", 10, LocalDate.of(2018, 10, 22));
        double result2 = service.addNota(nota, "test");
        Assert.assertEquals(7.5, result2, 0);

        Student added = service.findStudent("200");
        Tema added1 = service.findTema("200");

        Assert.assertEquals(student.getID(), added.getID());
        Assert.assertEquals(added1.getID(), assignment.getID());

        service.deleteStudent("200");
        service.deleteTema("200");
        service.deleteNota("12345");
    }
}

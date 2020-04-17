package assignment;

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
import validation.ValidationException;

public class AssignmentServiceTest {
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
    public void addAssignmentInvalidId() {
        Tema assignment = new Tema(null, "testValid", 4, 3);
        service.addTema(assignment);
    }

    @Test
    public void addAssignmentValidId() {
        Tema assignment = new Tema("200", "testValid", 4, 3);
        Tema result = service.addTema(assignment);
        Assert.assertNull(result);
        Tema added = service.findTema("200");
        Assert.assertEquals(added.getID(), assignment.getID());
        service.deleteTema("200");
    }

    @Test(expected = ValidationException.class)
    public void addAssignmentIdEmpty() {
        Tema assignment = new Tema("", "testValid", 4, 3);
        service.addTema(assignment);
    }

    @Test(expected = ValidationException.class)
    public void addAssignmentDescriptionEmpty() {
        Tema assignment = new Tema("200", "", 4, 3);
        service.addTema(assignment);
    }

    @Test(expected = ValidationException.class)
    public void addAssignmentDeadlineLow() {
        Tema assignment = new Tema("200", "test", 0, 3);
        service.addTema(assignment);
    }

    @Test(expected = ValidationException.class)
    public void addAssignmentDeadlineHigh() {
        Tema assignment = new Tema("200", "test", 20, 3);
        service.addTema(assignment);
    }

    @Test(expected = ValidationException.class)
    public void addAssignmentTurninLow() {
        Tema assignment = new Tema("200", "test", 3, 0);
        service.addTema(assignment);
    }

    @Test(expected = ValidationException.class)
    public void addAssignmentTurninHigh() {
        Tema assignment = new Tema("200", "test", 3, 32);
        service.addTema(assignment);
    }

    @Test
    public void addAssignmentExisting() {
        Tema assignment = new Tema("1", "test", 3, 4);
        Tema result = service.addTema(assignment);
        Assert.assertNotNull(result);
    }
}

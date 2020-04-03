package assignment;

import domain.Tema;
import org.junit.Assert;
import org.junit.BeforeClass;
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
    private static Service service;

    @BeforeClass
    public static void before() {
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
        Assert.assertEquals(assignment.getID(), result.getID());
        service.deleteTema("200");
    }

}

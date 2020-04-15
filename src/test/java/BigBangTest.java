import assignment.AssignmentServiceTest;
import grade.GradeServiceTest;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import student.StudentServiceTest;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

public class BigBangTest {
    private GradeServiceTest gradeServiceTest = new GradeServiceTest();
    private AssignmentServiceTest assignmentServiceTest = new AssignmentServiceTest();
    private StudentServiceTest studentServiceTest = new StudentServiceTest();
    private Service service;

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
        GradeServiceTest.service = service;
        AssignmentServiceTest.service = service;
        StudentServiceTest.service = service;
    }

    @Test
    public void integrationTest() {
        gradeServiceTest.addGradeValid();
        assignmentServiceTest.addAssignmentValidId();
        studentServiceTest.addStudentValidId();
    }
}

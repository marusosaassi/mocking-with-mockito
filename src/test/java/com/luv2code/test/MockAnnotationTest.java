package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class) //we haveto do this because our packages names are different
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @Mock //mock for the DAO => DAO test double
    private ApplicationDao applicationDao;

    @InjectMocks
    private ApplicationService applicationService;
    //this annotation injects the mock dependencies
    //and will only inject dependencies annotated with @Mock or @Spy

    @BeforeEach
    public void beforeEach() {
        studentOne.setFirstname("Maru");
        studentOne.setLastname("Sosa");
        studentOne.setEmailAddress("soassi@outlook.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("When & Verify")
    @Test
    public void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass(
            studentGrades.getMathGradeResults()
        )).thenReturn(90.00);

        assertEquals(90.00, applicationService
            .addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults()
            ));

        //verify that this method (the DAO method) was called during this whole testing process
        /*verify(applicationDao).addGradeResultsForSingleClass(
            studentGrades.getMathGradeResults()
        );*/

        //we can ask how many times this method was called
        // was this method called 3 times during this given test?
        // with 3, the test should fail because we are calling this method just once
        verify(applicationDao, times(1))
            .addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults()
            );
    }
}

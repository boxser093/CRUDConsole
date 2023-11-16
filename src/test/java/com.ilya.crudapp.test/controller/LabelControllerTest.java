package com.ilya.crudapp.test.controller;

import com.ilya.crudapp.controller.LabelController;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.jdbc.JDBCLabelRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class LabelControllerTest {
    private JDBCLabelRepositoryImpl labelRepository = mock(JDBCLabelRepositoryImpl.class);
    private LabelController labelController = new LabelController(labelRepository);
    private int testId;
    private Label label;
    private Label label1;
    Label labelUpdateTest;
    @BeforeEach
    void setUp(){
        testId = 1;
        label = new Label(1L,"#Sport",Status.ACTIVE);
        label1 = new Label(2L,"#Politic",Status.ACTIVE);
        labelUpdateTest  = new Label(1L,"#News",Status.ACTIVE);
    }
    @Test
    void findByIdTest(){
        when(labelRepository.getById(1L)).thenReturn(new Label(1l,"#Sport",Status.ACTIVE));
        assertEquals(labelController.findById(testId),label);
    }
    @Test
    void findAllTest(){
        List<Label> labels = List.of(label,label1);
        when(labelRepository.getAll()).thenReturn(labels);
        assertEquals(labelController.findAll(),List.of(label,label1));
    }

    @Test
    void saveLabelTest(){
        when(labelRepository.save(new Label(null,"#Sport",Status.ACTIVE))).thenReturn(label);
        assertEquals(labelController.saveLabel("#Sport"),label);
    }

    @Test
    void deleteByIdTest(){
        when(labelRepository.deleteById(1L)).thenReturn(true);
        Assertions.assertTrue(labelController.deleteById(1L), "true");
    }
    @Test
    void updateLabel(){
        Label label2 = Label.builder().id(1L).name("#News").build();
        when(labelRepository.update(label2)).thenReturn(labelUpdateTest);
        assertEquals(labelController.updateLabel(1L,"#News"),labelUpdateTest);
    }


}

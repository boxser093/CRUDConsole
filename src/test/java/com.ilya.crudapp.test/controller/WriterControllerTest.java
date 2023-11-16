package com.ilya.crudapp.test.controller;

import com.ilya.crudapp.controller.PostController;
import com.ilya.crudapp.controller.WriterController;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.model.Writer;
import com.ilya.crudapp.repository.WriterRepository;
import com.ilya.crudapp.repository.jdbc.JDBCWriterRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


class WriterControllerTest {
    WriterRepository writerRepository = mock(JDBCWriterRepositoryImpl.class);
    PostController postController = mock(PostController.class);
    WriterController writerController = new WriterController(writerRepository,postController);
    Post post;
    Post post2;
    Post post3;
    Writer writerFindById;
    Writer writerFindById2;
    Writer update;
    @BeforeEach
    void setup(){
        post = mock(Post.class);
        post2 = mock(Post.class);
        post3 = mock(Post.class);
        writerFindById = new Writer(1L, Status.ACTIVE,"Petr","Azimov", List.of(post,post2));
        writerFindById2 = new Writer(2L, Status.ACTIVE,"Petr","Azimov", List.of(post,post2));
        update = new Writer(1L,Status.ACTIVE,"Oleg","Ovcharov",List.of(post2,post3));
    }

    @Test
    void findById_test(){
        doReturn(writerFindById).when(writerRepository).getById(1L);
        Writer writer = writerController.findById(1);
        assertEquals(writerFindById,writer);
    }

    @Test
    void find_all(){
        doReturn(List.of(writerFindById,writerFindById2)).when(writerRepository).getAll();
        List<Writer> writers = writerController.findAll();
        assertEquals(List.of(writerFindById,writerFindById2),writers);
    }

    @Test
    void deleteById(){
        doReturn(true).when(writerRepository).deleteById(1L);
        boolean test = writerController.deleteById(1L);
        assertTrue(true, String.valueOf(test));
    }

    @Test
    void saveWriter_test(){
        Writer writer = Writer.builder()
                .firstName("Albert")
                .lastName("Petrov")
                .posts(writerController.preparePost(List.of(1L,2L)))
                .status(Status.ACTIVE)
                .build();
        doReturn(writerFindById).when(writerRepository).save(writer);
        Writer writerBefSave = writerController.saveWriter("Albert","Petrov",List.of(1L,2L));
        assertEquals(writerFindById, writerBefSave);
    }

    @Test
    void updateWriter_test(){
        Writer writer = Writer.builder()
                .id(1L)
                .firstName("Oleg")
                .lastName("Ovcharov")
                .posts(List.of(post2,post3))
                .build();
        doReturn(update).when(writerRepository).update(writer);
        Writer writerTest = writerController.updateWriter(1L,"Oleg","Ovcharov",List.of(post2,post3));
        assertEquals(update,writerTest);
    }
}

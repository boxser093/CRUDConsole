package com.ilya.crudapp.test.controller;

import com.ilya.crudapp.controller.LabelController;
import com.ilya.crudapp.controller.PostController;
import com.ilya.crudapp.model.Label;
import com.ilya.crudapp.model.Post;
import com.ilya.crudapp.model.Status;
import com.ilya.crudapp.repository.PostRepository;
import com.ilya.crudapp.repository.jdbc.JDBCPostRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostControllerTest {
    private  PostRepository postRepository;
    private  PostController postController;
    private Post post;
    private Post post1;

    @BeforeEach
    void setUp() {
        LabelController labelController = mock(LabelController.class);
        postRepository = mock(JDBCPostRepositoryImpl.class);
        postController = new PostController(postRepository,labelController);
        Label label = mock(Label.class);
        Label label2 = mock(Label.class);
        post = new Post(1L, "Some text of Post", new Date(), null, Status.ACTIVE, List.of(label, label2));
        post1 = new Post(2L, "Some text of Post", new Date(), null, Status.ACTIVE, List.of(label, label2));
    }

    @Test
    void findById_test() {
        when(postRepository.getById(1L)).thenReturn(post);
        assertEquals(postController.findById(1L), post);
    }

    @Test
    void findAll_test() {
        when(postRepository.getAll()).thenReturn(List.of(post, post1));
        assertEquals(postController.findAll(), List.of(post, post1));
    }

    @Test
    void deleteById_test() {
        when(postRepository.deleteById(1L)).thenReturn(true);
        Assertions.assertTrue(postController.deleteById(1L), "true");
    }
    @Test
    void saveNewPost_test() {
        Post postWithId = Post.builder()
                .content("Some text of Post")
                .labels(postController.prepareLabels(List.of(1L,2L)))
                .status(Status.ACTIVE)
                .created(new Date())
                .build();
        doReturn(postWithId).when(postRepository).save(any(Post.class));
        Post post2 = postController.savePost("Some text of Post",List.of(1L,2L));
        assertEquals(postWithId, post2);
    }

    @Test
    void updatePost_test(){
        Post postBeforeUpdate = Post.builder()
                        .id(1L).content("New Text of Post").created(new Date()).update(new Date()).status(Status.ACTIVE).labels(postController.prepareLabels(List.of(1L,2L))).build();
        doReturn(postBeforeUpdate).when(postRepository).update(any(Post.class));
        Post postToUpdate = postController.updatePost(1L,"New Text of Post",postController.prepareLabels(List.of(1L,2L)));
        assertEquals(postBeforeUpdate,postToUpdate);
        verify(postRepository,times(1)).update(any(Post.class));
    }
}

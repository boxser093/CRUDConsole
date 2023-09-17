package com.ilya.crudapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Writer {
    private Long id;
    private PostStatus status;
    private String firstName;
    private String lastName;
    private List<Post> posts;
}

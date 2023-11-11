package com.ilya.crudapp.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private Long id;
    private String content;
    private Date created;
    private Date update;
    private Status status;
    private List<Label> labels;
}

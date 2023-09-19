package com.ilya.crudapp.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private Long id;
    private String content;
    @Getter
    private boolean created;
    @Getter
    private boolean update;
    private Status status;
    private List<Label> labels;
}

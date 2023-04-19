package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private Long noteId;
    private String noteTitle;
    @Size(max=1000)
    private String noteDescription;
    private Integer userId;
}

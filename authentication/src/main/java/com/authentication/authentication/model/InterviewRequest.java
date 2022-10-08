package com.authentication.authentication.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class InterviewRequest {
    @Size(min = 11, max = 11)
    private String pesel;
    private boolean passed;
}

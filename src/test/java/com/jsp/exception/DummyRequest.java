package com.jsp.exception;

import jakarta.validation.constraints.NotBlank;

public class DummyRequest {
    @NotBlank(message = "dModel cannot be blank")
    public String dModel;
}

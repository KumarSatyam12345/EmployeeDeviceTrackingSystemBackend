package com.jsp.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NoEscapeChars is a custom annotation to validate that a field does not contain escape characters.
 * It is used to mark fields that should not contain escape characters.
 * The validation is done by the NoEscapeCharsValidator class.
 */
@Constraint(validatedBy = NoSpecialCharsValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpecialChars {
    String message() default "String contains escape characters!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

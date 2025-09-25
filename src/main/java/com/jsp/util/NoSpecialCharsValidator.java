package com.jsp.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class NoSpecialCharsValidator implements ConstraintValidator<NoSpecialChars, String> {

    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[\r\f\b\t\"\n!'@#$%^&*\\\\]");

    /**
     * Initializes the validator in preparation for isValid calls.
     * This method is called once after the creation of the validator.
     *
     * @param constraintAnnotation the annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(NoSpecialChars constraintAnnotation) {
        // Initialization code if needed
    }

    /**
     * Implements the validation logic. The state of value must not be altered.
     *
     * @param value   the object to validate
     * @param context context in which the constraint is evaluated
     * @return false if value contains special characters, true otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are valid, use @NotNull for null checks
        }

        try {
            return !SPECIAL_CHAR_PATTERN.matcher(value).find();
        } catch (Exception e) {
            log.error("Error validating string: {}", e.getMessage());
            return false; // Return false if an exception occurs
        }
    }
}
package com.jsp.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NoSpecialCharsValidatorTest {

    private NoSpecialCharsValidator validator;
    //http://localhost:8080/device-api/v1//facilities/bb5a7e7c-86bb-4a27-89bb-fe16df44734c/devices
//http://localhost:8080/device-api/v1//facilities/bb5a7e7c-86bb-4a27-89bb-fe16df44734c/devices
    @BeforeEach
    void setUp() {
        validator = new NoSpecialCharsValidator();
        validator.initialize(null); // Initialize if needed
    }

    @Test
    void testNullValue() {
        assertTrue(validator.isValid(null, null), "Null value should be valid");
    }

    @Test
    void testEmptyString() {
        assertTrue(validator.isValid("", null), "Empty string should be valid");
    }

    @Test
    void testStringWithoutEscapeChars() {
        assertTrue(validator.isValid("HelloWorld", null), "String without escape characters should be valid");
    }

    @Test
    void testStringWithNewline() {
        assertFalse(validator.isValid("Hello\nWorld", null), "String with newline should be invalid");
    }

    @Test
    void testStringWithTab() {
        assertFalse(validator.isValid("Hello\tWorld", null), "String with tab should be invalid");
    }

    @Test
    void testStringWithCarriageReturn() {
        assertFalse(validator.isValid("Hello\rWorld", null), "String with carriage return should be invalid");
    }

    @Test
    void testStringWithFormFeed() {
        assertFalse(validator.isValid("Hello\fWorld", null), "String with form feed should be invalid");
    }

    @Test
    void testStringWithBackspace() {
        assertFalse(validator.isValid("Hello\bWorld", null), "String with backspace should be invalid");
    }

    @Test
    void testStringWithBackslash() {
        assertFalse(validator.isValid("Hello\\World", null), "String with backslash should be invalid");
    }

    @Test
    void testStringWithSingleQuote() {
        assertFalse(validator.isValid("Hello'World", null), "String with single quote should be invalid");
    }

    @Test
    void testStringWithDoubleQuote() {
        assertFalse(validator.isValid("Hello\"World", null), "String with double quote should be invalid");
    }

    @Test
    void testStringWithSpecialChars() {
        assertFalse(validator.isValid("Hello@World", null), "String with special characters should be invalid");
    }
}

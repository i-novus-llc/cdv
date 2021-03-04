package ru.i_novus.components.cdv.core.service;

import org.junit.Assert;
import org.junit.Test;
import ru.i_novus.components.cdv.core.model.ValidationException;

import java.util.Collections;

public class ValidationServiceTest {

    @Test
    public void testValidationException() {
        Validation someTestValidation = new Validation() {
            @Override
            public Object validate(Object o) {
                throw new ArithmeticException("some test exception");
            }

            @Override
            public String getCode() {
                return "testCode";
            }
        };

        try {
            new ValidationServiceImpl(input->input, parsedValue -> Collections.singletonList(someTestValidation))
                    .validate(new Object());
            Assert.fail("expected ValidationException");
        } catch (ValidationException e) {
            Assert.assertEquals("validation: testCode failed, details: some test exception", e.getMessage());
        }

    }
}

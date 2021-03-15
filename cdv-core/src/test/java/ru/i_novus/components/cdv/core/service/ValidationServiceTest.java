package ru.i_novus.components.cdv.core.service;

import org.junit.Assert;
import org.junit.Test;
import ru.i_novus.components.cdv.core.model.ValidationException;

import java.util.Collections;
import java.util.List;

@SuppressWarnings({"rawtypes","unchecked"})
public class ValidationServiceTest {

    @Test
    public void testValidationException() {

        Validation someValidation = new Validation() {
            @Override
            public Object validate(Object o) {
                throw new ArithmeticException("some test exception");
            }

            @Override
            public String getCode() {
                return "testCode";
            }
        };

        ValidationRepository someValidationRepository = new ValidationRepository() {

            @Override
            public String getAllowedLanguage() {
                return "some language";
            }

            @Override
            public List<Validation> getValidations(Object o) {
                return Collections.singletonList(someValidation);
            }
        };

        try {
            new ValidationServiceImpl(input -> input, someValidationRepository)
                    .validate(new Object());
            Assert.fail("expected ValidationException");

        } catch (ValidationException e) {
            Assert.assertEquals("validation: testCode failed, details: some test exception", e.getMessage());
        }
    }
}

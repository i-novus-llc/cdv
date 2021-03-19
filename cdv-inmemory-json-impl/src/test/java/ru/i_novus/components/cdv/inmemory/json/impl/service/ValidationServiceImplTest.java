package ru.i_novus.components.cdv.inmemory.json.impl.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.i_novus.components.cdv.core.model.StatusEnum;
import ru.i_novus.components.cdv.core.service.ValidationService;
import ru.i_novus.components.cdv.inmemory.json.impl.TestConfig;
import ru.i_novus.components.cdv.inmemory.json.impl.model.ValidationResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ValidationServiceImplTest {

    private String jsonData;

    public void setJsonData(String fileName) {
        InputStream jsonStream = this.getClass().getResourceAsStream(fileName);
        jsonData = new BufferedReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));
    }

    @Autowired
    List<ValidationService<String, ValidationResult>> validationServices;

    /**
     * see src/test/resources/db/changelog/test_data.xml
     */
    @Test
    public void testSpelValidate() {
        setJsonData("/test.json");
        ValidationService<String, ValidationResult> validationService = validationServices.get(0);
        assertNotNull(validationService);
        List<ValidationResult> results = validationService.validate(jsonData);

        String message = String.format("Assert for %s", validationService.getClass().getSimpleName());
        assertEquals(message, 1, results.size());
        assertEquals(message, "TEST1", results.get(0).getCode());
    }

    @Test
    public void testGroovyValidateIncorrect() {
        setJsonData("/groovy_incorrect.json");
        ValidationService<String, ValidationResult> validationService = validationServices.get(1);
        assertNotNull(validationService);
        List<ValidationResult> results = validationService.validate(jsonData);

        String message = String.format("Assert for %s", validationService.getClass().getSimpleName());
        assertEquals(message, 2, results.size());
        assertEquals(message, "TEST1", results.get(0).getCode());
        assertEquals(StatusEnum.ERROR, results.get(0).getStatus());
        assertEquals(message, "TEST4", results.get(1).getCode());
        assertEquals(StatusEnum.ERROR, results.get(1).getStatus());
    }

    @Test
    public void testGroovyValidateCorrect() {
        setJsonData("/groovy_correct.json");
        ValidationService<String, ValidationResult> validationService = validationServices.get(1);
        assertNotNull(validationService);
        List<ValidationResult> results = validationService.validate(jsonData);

        String message = String.format("Assert for %s", validationService.getClass().getSimpleName());
        assertEquals(message, 2, results.size());
        assertEquals(message, "TEST1", results.get(0).getCode());
        assertEquals(StatusEnum.SUCCESS, results.get(0).getStatus());
        assertEquals(message, "TEST4", results.get(1).getCode());
        assertEquals(StatusEnum.SUCCESS, results.get(1).getStatus());
    }
}

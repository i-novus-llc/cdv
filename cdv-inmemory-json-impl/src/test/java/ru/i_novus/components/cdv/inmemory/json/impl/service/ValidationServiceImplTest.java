package ru.i_novus.components.cdv.inmemory.json.impl.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
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
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class ValidationServiceImplTest {

    private String jsonData;

    @Before
    public void setUp() {
        InputStream jsonStream = this.getClass().getResourceAsStream("/test.json");
        jsonData = new BufferedReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));
    }

    @Autowired
    List<ValidationService<String, ValidationResult>> validationServices;

    /**
     * see src/test/resources/db/changelog/test_data.xml
     */
    @Test
    public void testValidate() {

        assertFalse(CollectionUtils.isEmpty(validationServices));
        validationServices.forEach(this::testValidate);
    }

    private void testValidate(ValidationService<String, ValidationResult> validationService) {

        List<ValidationResult> results = validationService.validate(jsonData);

        String message = String.format("Assert for %s", validationService.getClass().getSimpleName());
        assertEquals(message, 1, results.size());
        assertEquals(message, "TEST1", results.get(0).getCode());
    }
}

package ru.i_novus.components.cdv.inmemory.json.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.i_novus.components.cdv.core.api.ValidationService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class ValidationServiceITest {

    private String jsonData;

    @Before
    public void setUp() throws Exception {
        jsonData = new BufferedReader(
                new InputStreamReader(this.getClass().getResourceAsStream("/test.json"), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));
    }

    @Autowired
    ValidationService<String, ValidationResult> validationService;


    @Test
    /**
     * see src/test/resources/db/changelog/test_data.xml
     */
    public void testValidationService() {
        List<ValidationResult> results = validationService.validate(jsonData);
        Assert.assertTrue(results.size() == 1);
        Assert.assertEquals("TEST1", results.get(0).getCode());

    }
}

package ru.i_novus.components.cdv.inmemory.json.impl.util;

import com.jayway.jsonpath.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class JsonPathUtils {

    private JsonPathUtils() {
        // Nothing to do.
    }

    public static <T> T evaluate(Object json, String jsonPath, Predicate... predicates) throws IOException {
        Configuration configuration = Configuration.defaultConfiguration();
        configuration = configuration.addOptions(Option.SUPPRESS_EXCEPTIONS).addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        ParseContext context = JsonPath.using(configuration);
        if (json instanceof String) {
            return context.parse((String) json).read(jsonPath, predicates);
        }
        else if (json instanceof byte[]) {
            return context.parse(new ByteArrayInputStream((byte[]) json)).read(jsonPath, predicates);
        }
        else if (json instanceof File) {
            return context.parse((File) json).read(jsonPath, predicates);
        }
        else if (json instanceof URL) {
            return context.parse(((URL) json).openStream()).read(jsonPath, predicates);
        }
        else if (json instanceof InputStream) {
            return context.parse((InputStream) json).read(jsonPath, predicates);
        }
        else {
            return context.parse(json).read(jsonPath, predicates);
        }

    }
}

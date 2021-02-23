package ru.i_novus.components.cdv.inmemory.json.impl;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.Predicate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class JsonPathUtils {
    static {
        Configuration.builder().options(Option.DEFAULT_PATH_LEAF_TO_NULL).build();
    }
    public static <T> T evaluate(Object json, String jsonPath, Predicate... predicates) throws IOException {
        if (json instanceof String) {
            Configuration configuration = Configuration.defaultConfiguration();
            configuration = configuration.addOptions(Option.SUPPRESS_EXCEPTIONS);
            return JsonPath.using(configuration).parse((String) json).read(jsonPath, predicates);
        }
        else if (json instanceof byte[]) {
            return JsonPath.read(new ByteArrayInputStream((byte[]) json), jsonPath, predicates);
        }
        else if (json instanceof File) {
            return JsonPath.read((File) json, jsonPath, predicates);
        }
        else if (json instanceof URL) {
            return JsonPath.read(((URL) json).openStream(), jsonPath, predicates);
        }
        else if (json instanceof InputStream) {
            return JsonPath.read((InputStream) json, jsonPath, predicates);
        }
        else {
            return JsonPath.read(json, jsonPath, predicates);
        }

    }

    private JsonPathUtils() {
    }

}

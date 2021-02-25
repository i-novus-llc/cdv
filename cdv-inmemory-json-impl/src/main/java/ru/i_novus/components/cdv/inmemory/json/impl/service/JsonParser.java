package ru.i_novus.components.cdv.inmemory.json.impl.service;

import ru.i_novus.components.cdv.core.service.Parser;

public class JsonParser  implements Parser<String, String> {

    @Override
    public String parse(String input) {
        return input;
    }
}

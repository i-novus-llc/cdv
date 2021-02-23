package ru.i_novus.components.cdv.inmemory.json.impl;

import ru.i_novus.components.cdv.core.api.Parser;

public class JsonParser  implements Parser<String, String> {

    @Override
    public String parse(String input) {
        return input;
    }
}

package io.bayberry.aloha.config;

import java.io.InputStream;

public interface PropertySourceReader {

    PropertySource read(InputStream inputStream);
}
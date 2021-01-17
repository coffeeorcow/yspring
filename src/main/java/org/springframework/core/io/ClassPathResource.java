package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

public class ClassPathResource implements Resource {

    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
        if (in == null) {
            throw new NoSuchFileException("文件找不到，filePath => " + path);
        }

        return in;
    }
}

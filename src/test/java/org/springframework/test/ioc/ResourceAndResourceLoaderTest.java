package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResourceAndResourceLoaderTest {

    @Test
    public void testResourceLoader() {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        // classpath
        Resource resource = resourceLoader.getResource("classpath:hello.txt");
        printContent(resource);

        // url
        resource = resourceLoader.getResource("http://www.baidu.com");
        printContent(resource);

        // fileSystem
        resource = resourceLoader.getResource("/Users/junyiwu/workspace/temp/temp.txt");
        printContent(resource);
    }

    public void printContent(Resource resource) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

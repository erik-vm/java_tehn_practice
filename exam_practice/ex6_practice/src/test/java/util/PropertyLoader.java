package util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class PropertyLoader {

    public static Properties loadApplicationProperties(String fileName) {

        String contents = FileUtil.readFileFromClasspath(fileName);

        Properties properties = new Properties();

        try {

            properties.load(new StringReader(contents));

            return properties;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

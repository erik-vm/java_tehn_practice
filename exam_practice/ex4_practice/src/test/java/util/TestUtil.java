package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class TestUtil {

    public static String removeWhitespace(String result) {
        return result.replaceAll(" ", "").replaceAll("[\n\r]", "");
    }

    public static int getChecksum(String path) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get("src/main/java/" + path));

            String contents = new String(bytes, StandardCharsets.UTF_8);

            return calculateChecksum(removeWhitespace(contents));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int calculateChecksum(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return Long.valueOf(crc32.getValue()).intValue();
    }

}

package apl.udc.global.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileDownloader {

    public void downloadFile(String fileUrl, String savePath) throws IOException {
        URL url = new URL(fileUrl);

        try (InputStream inputStream = url.openStream()) {
            Path path = Paths.get(savePath);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
package com.tdonuk.passwordmanager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.CharSet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtils {
    public static String readContent(String path) throws Exception {
        return Files.readString(Paths.get(path));
    }

    public static void writeContent(String path, String content) throws Exception {
        Path pathToWrite = Path.of(path);
        if(Files.isWritable(pathToWrite)) {
            Files.write(pathToWrite, content.getBytes(Charset.availableCharsets().getOrDefault("UTF-8", Charset.defaultCharset())));
        }
        else throw new Exception("File not found");
    }
}

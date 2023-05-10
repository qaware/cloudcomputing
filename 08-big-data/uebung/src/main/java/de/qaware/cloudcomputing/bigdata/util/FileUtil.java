package de.qaware.cloudcomputing.bigdata.util;

import de.qaware.cloudcomputing.bigdata.WordCountMapReduce;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    public static String readFileFromResources(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("fileName must not be empty!");
        }

        try {
            URL url = WordCountMapReduce.class.getClassLoader().getResource(fileName);
            return IOUtils.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream readFileFromResourcesAsStream(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("fileName must not be empty!");
        }

        return WordCountMapReduce.class.getClassLoader().getResourceAsStream(fileName);
    }

}

package store.common.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
    public static List<String> readMarkdownFile(String fileName) {
        List<String> list = new ArrayList<>();
        try (InputStream inputStream = FileParser.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) { list.add(line); }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}

package com.rom;

import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Utils {

    public static String resource(String fileName) {
        try {
            URL url = Resources.getResource(fileName);
            return Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

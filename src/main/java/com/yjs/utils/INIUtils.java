package com.yjs.utils;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;

public class INIUtils {

    private static Ini iniFile = new Ini();

    static {
        File file = new File("config.ini");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            iniFile.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String sec, String key){
        Profile.Section section = iniFile.get(sec);
        return section.get(key);
    }

    public static int getNumberValue(String sec, String key){
        Profile.Section section = iniFile.get(sec);
        return section.get(key, Integer.class);
    }
}

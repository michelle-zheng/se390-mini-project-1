package com.example.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties defaultProps = new Properties();
    private static Config INSTANCE = new Config();
    private Config() {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("application.properties");
            defaultProps.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public String getProperty(String key) {
        return defaultProps.getProperty(key);
    }
}

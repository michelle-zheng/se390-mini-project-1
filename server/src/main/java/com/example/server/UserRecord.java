package com.example.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class UserRecord {

    // local userRecord filename
    private static final String USER_FILE = "users.json";

    // singleton instance
    private static UserRecord instance;

    public static void init() {
        instance = load();
    }

    public static class User {
        public String number;

    }

    public List<User> users;

    private UserRecord() {
    }


    private static File getFile()
            throws FileNotFoundException {
        return ResourceUtils.getFile(
                "classpath:" + USER_FILE);
    }

    /**
     * Load user data to the singleton instance.
     *
     * @throws RuntimeException "Failed to read user data"
     */
    public static UserRecord load() {
        try {
            File file = getFile();

            ObjectMapper jsonMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return jsonMapper.readValue(file, UserRecord.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read user data", e);
        }
    }


    /**
     * Return true if input number exists in users.json;
     * false otherwise;
     *
     * @return boolean
     */
    public static boolean isValidUser(String number) {
        for (User user : instance.users) {
            if (number.equals(user.number)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add user record into singleton instance
     */
    public static void add(String number) {
        User newUser = new User();
        newUser.number = number;
        instance.users.add(newUser);
    }

//    /**
//     * Return singleton instance
//     *
//     * @return UserRecord instance
//     */
//    public static UserRecord get()
//    {
//        return instance;
//    }


    /**
     * Save user data to the json file.
     *
     * @throws RuntimeException "Error when writing to user record file"
     */
    public static void save() {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            jsonMapper.writeValue(getFile(), instance);

        } catch (Exception e) {
            throw new RuntimeException("Error when writing to user record file", e);
        }
    }

}

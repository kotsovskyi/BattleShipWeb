package com.kotsovskyi.service;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static Map<String, String> users;

    static {
        users = new HashMap<String, String>();
        users.put("admin", "admin");
        users.put("roman", "roman");
        users.put("stas", "stas");
        users.put("yarik", "yarik");
        users.put("nazar", "nazar");
    }

    public String getUser(String login, String password) {
        if (users.containsKey(login) && password.equals(users.get(login))) {
            return login;
        }

        return null;
    }
}

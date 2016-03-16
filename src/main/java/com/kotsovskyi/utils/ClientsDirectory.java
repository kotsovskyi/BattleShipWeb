package com.kotsovskyi.utils;

import com.kotsovskyi.server.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientsDirectory {

    public static Map<String, Client> clientMap = new HashMap<String, Client>();

    public static void addClient(String name, Client client) {
        clientMap.put(name, client);
    }

    public static Client getClient(String name) {
        Client client = clientMap.get(name);
        return client;
    }
}

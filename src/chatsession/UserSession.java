package chatsession;

import server.Client;

import java.net.Socket;

public class UserSession {
    private static String username = "default";
    private static Client client = null;
    private static Socket socket = null;

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        UserSession.client = client;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserSession.username = username;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        UserSession.socket = socket;
    }

    public static void print(){
        System.out.println("username: " + username);
        System.out.println("client: " + client);
        System.out.println("socket: " + socket);
    }

}

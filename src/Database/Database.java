package Database;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    private static HashMap<String,String> users = new HashMap<>() {{
        put("a","123");
        put("b","123");
        put("c","123");
        put("d","123");
        put("e","123");
    }
    };

    private static HashMap<Integer,String> onlineUsers = new HashMap<>();

    public Database(){

    }
    public static boolean checkValidLogin(String username, String password){

        if(username == null || password ==null){
            JOptionPane.showMessageDialog(null, "Please fill username and password","Login fail",JOptionPane.ERROR_MESSAGE);
        }
        else if(users.get(username) == null){
            return false;
        }
        else if(users.get(username).equals(password)){
            return true;
        }
        return false;
    }
    public static boolean isExistUser(String username){
        return users.get(username) != null;
    }
    public static void addUser(String username,String password){
        users.put(username,password);
    }
    public static void addOnlineUser(Integer port, String username){onlineUsers.put(port,username);}
    public static void printOnlineUser(){
        for(int port :onlineUsers.keySet()){
            System.out.println("port " + port);
        }

    }

}

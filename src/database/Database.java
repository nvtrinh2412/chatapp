package database;

import server.ClientHandler;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    private static HashMap<String,String> users = new HashMap<>();

//    private static HashMap<Integer,String> onlineUsers = new HashMap<>();

    static ArrayList<String> onlineUsers = new ArrayList<>();
    public Database(){
        try {
            getUserFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkValidLogin(String username, String password){

//        System.out.println(onlineUsers.toString());
        if(username == null || password ==null){
            JOptionPane.showMessageDialog(null, "Please fill username and password","Login fail",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(users.get(username) == null){
            return false;
        }
        else if(ClientHandler.isOnline(username)){
            JOptionPane.showMessageDialog(null, "You are already logged in","Login fail",JOptionPane.ERROR_MESSAGE);
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
    public static void addOnlineUser(String username){onlineUsers.add(username);}
    public static void removeOnlineUser(String username){onlineUsers.remove(username);}
    public boolean isOnline(String username){
        return onlineUsers.contains(username);
    }

    public void getUserFromFile() throws IOException
    {
//        if(users == null)
//            users = new HashMap<>();

        BufferedReader br ;
        try
        {
            br = new BufferedReader(new FileReader("user.txt"));
        }
        catch(FileNotFoundException exc)
        {
            System.out.println("File Not Found");
            return;
        }

        int UserCount=0;
        try {
            UserCount = Integer.parseInt(br.readLine());
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(int i=0;i<UserCount;i++)
        {
            String ID;
            String Password;
            ID = (String) br.readLine();
            Password = (String) br.readLine();
            users.put(ID, Password);
        }
        br.close();

    }
    public static void writeFile() throws IOException
    {

        FileOutputStream fout;

        fout = new FileOutputStream("user.txt");

        try
        {

            String out= Integer.toString(users.size())+"\n";
            for(String i : users.keySet())
            {
                out += i +"\n" + users.get(i) + "\n";
            }
            fout.write(out.getBytes());

        }
        catch(IOException exc)
        {
            System.out.println("Error occur when write to file");
        }
        fout.flush();
        fout.close();
    }

    public static HashMap<String,String> getUsers(){
        return users;
    }
}

package database;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;

public class Database {

    private static HashMap<String,String> users = new HashMap<>();

    private static HashMap<Integer,String> onlineUsers = new HashMap<>();

    public Database(){
        try {
            getUserFromFile("src/database/user.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkValidLogin(String username, String password){

        if(username == null || password ==null){
            JOptionPane.showMessageDialog(null, "Please fill username and password","Login fail",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(users.get(username) == null){
            return false;
        }
        else if(false){
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
    public static void addOnlineUser(Integer port, String username){onlineUsers.put(port,username);}
    public static void printOnlineUser(){
        for(int port :onlineUsers.keySet()){
            System.out.println("port " + port);
        }

    }

    public void getUserFromFile(String filename) throws IOException
    {
//        if(users == null)
//            users = new HashMap<>();
        BufferedReader br ;
        try
        {
            br = new BufferedReader(new FileReader(filename));
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
        System.out.println(UserCount);
        for(int i=0;i<UserCount;i++)
        {
            String ID;
            String Password;
            ID = (String) br.readLine();
            Password = (String) br.readLine();
            users.put(ID, Password);
            System.out.println(ID + " " + Password);
        }
        br.close();

    }
    public static void writeFile(String filename) throws IOException
    {

        FileOutputStream fout;

        fout = new FileOutputStream(filename);

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

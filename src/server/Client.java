package server;
import database.Database;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

// A client sends messages to the server, the server spawns a thread to communicate with the client.
// Each communication with a client is added to an array list so any message sent gets sent to every other client
// by looping through it.

public class Client {

    // A client has a socket to connect to the server and a reader and writer to receive and send messages respectively.
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {

            this.socket = socket;
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {
            // Gracefully close everything.
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }

    // Sending a message isn't blocking and can be done without spawning a thread, unlike waiting for a message.
    public void sendMessage(String message) {
        try {
            // While there is still a connection with the server, continue to scan the terminal and then send the message.
            if (socket.isConnected()) {
                bufferedWriter.write(username + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("You send: "+ message);
            }
        } catch (IOException e) {
            // Gracefully close everything.
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    // Listening for a message is blocking so need a separate thread for that.
    public void listenForMessage(JTextArea textArea) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat = "default";
                // While there is still a connection with the server, continue to listen for messages on a separate thread.
                while (socket.isConnected()) {
                    try {
                        // Get the messages sent from other users and print it to the console.
                        msgFromGroupChat = bufferedReader.readLine();
                        JTextField textField = new JTextField();
                        textField.setText(msgFromGroupChat);
                        textArea.append(msgFromGroupChat + "\n");
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        // Close everything gracefully.
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    // Helper method to close everything so you don't have to repeat yourself.
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendUsername() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendFile(File selectedFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                bufferedWriter.write(String.valueOf(buffer), 0, bytesRead);
                bufferedWriter.flush();
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

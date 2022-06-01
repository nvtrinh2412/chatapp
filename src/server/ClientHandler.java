package server;
// 1. Open a socket.
// 2. Open an input stream and output stream to the socket.
// 3. Read from and write to the stream according to the server's protocol.
// 4. Close the streams.
// 5. Close the socket.

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler implements Runnable {

    // Array list of all the threads handling clients so each message can be sent to the client the thread is handling.
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    // Id that will increment with each new client.

    // Socket for a connection, buffer reader and writer for receiving and sending data respectively.
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    // Creating the client handler from the socket the server passes.
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // When a client connects their username is sent.

            // read from sendUsername()
            this.clientUsername = bufferedReader.readLine();
            // Add the new client handler to the array so they can receive messages from others.
            clientHandlers.add(this);
            sendMessageToClient("ALL","User: " + clientUsername+ " has entered the chat!");
        } catch (IOException e) {
            // Close everything more gracefully.
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    // Everything in this method is run on a separate thread. We want to listen for messages
    // on a separate thread because listening (bufferedReader.readLine()) is a blocking operation.
    // A blocking operation means the caller waits for the callee to finish its operation.
    @Override
    public void run() {

        // Continue to listen for messages while a connection with the client is still established.
        while (socket.isConnected()) {
            try {
                String messageFromClient;
                // Read what the client sent and then send it to every other client.
                messageFromClient = bufferedReader.readLine();
                System.out.println(messageFromClient);
                sendMessageToClient("ALL",messageFromClient);
            } catch (IOException e) {
                // Close everything gracefully.
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    // Send a message through each client handler thread so that everyone gets the message.
    // Basically each client handler is a connection to a client. So for any message that
    // is received, loop through each connection and send it down it.
    public void sendMessageToClient(String receiver, String messageToSend) {

        if (receiver.equals("ALL")) {
            for (ClientHandler clientHandler : clientHandlers) {
                if (!clientHandler.clientUsername.equals(clientUsername)) {

                    System.out.println("broadcastMessage: " + messageToSend);
                    try {
                        clientHandler.bufferedWriter.write(messageToSend);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        } else {
            for (ClientHandler clientHandler : clientHandlers) {
                if (clientHandler.clientUsername.equals(receiver)) {
                    System.out.println("send to "+ receiver+" : "+ messageToSend);
                    try {
                        clientHandler.bufferedWriter.write(messageToSend);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    public void privateMessage(String receiver, String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                // You don't want to broadcast the message to the user who sent it.
                if (clientHandler.clientUsername.equals(receiver)) {
                    System.out.println("broadcastMessage: " + messageToSend);
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                // Gracefully close everything.
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    // If the client disconnects for any reason remove them from the list so a message isn't sent down a broken connection.
    public void removeClientHandler() {
        clientHandlers.remove(this);
        sendMessageToClient("ALL", "User: " + clientUsername + " has left the chat!");
    }

    // Helper method to close everything so you don't have to repeat yourself.
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        removeClientHandler();
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

    public static void getClientHandlers() {
        System.out.println("Online users: " + clientHandlers.size());
        for (ClientHandler clientHandler : clientHandlers) {
            System.out.println("\tUsername " + clientHandler.clientUsername);
        }
    }


    public static boolean isOnline(String username) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.clientUsername.equals(username)) {
                return true;
            }
        }
        return false;
    }

}

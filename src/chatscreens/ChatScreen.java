package chatscreens;

import chatsession.UserSession;
import server.Client;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;


public class ChatScreen extends JDialog {
    private JTextField message;
    private JButton sendButton;
    private JPanel chatPanel;
    private JScrollPane scrollPanel;
    private JTextArea chatMessage;
    private final Client client = UserSession.getClient();

    public ChatScreen() throws IOException {

        createUIComponents();
        Socket socket = UserSession.getSocket();
        // Infinite loop to read and send messages.
        client.sendUser();
        client.listenForMessage(chatMessage);
        //handle thread for listening to server
        handleActionListener();




    }

    private void handleActionListener() {
        sendButton.addActionListener(e -> {

            String messageToSend = message.getText();
            client.sendMessage(messageToSend);
            chatMessage.append(messageToSend + "\n");
            message.setText("");

        });

        message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String messageToSend = message.getText();
                    client.sendMessage(messageToSend);
                    chatMessage.append(messageToSend + "\n");
                    message.setText("");
                }
            }
        });
    }

    private void createUIComponents() {
        ConfigurationDialog.installBasicConfiguration(this, "ChatScreen", chatPanel);
    }
}

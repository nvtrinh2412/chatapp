package chatscreens;

import chatsession.UserSession;
import server.Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;


public class ChatScreen extends JFrame {
    private JTextField message;
    private JButton sendButton;
    private JPanel chatPanel;
    private JScrollPane scrollPanel;
    private JTextArea chatMessage;
    private JLabel fileIcon;
    private final Client client = UserSession.getClient();

    public ChatScreen() throws IOException {

        createUIComponents();
        Socket socket = UserSession.getSocket();
        // Infinite loop to read and send messages.
        client.listenForMessage(chatMessage);
        //handle thread for listening to server
        handleActionListener();


        fileIcon.addComponentListener(new ComponentAdapter() {
        });
        fileIcon.addContainerListener(new ContainerAdapter() {
        });
        fileIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose a file");
                int result = fileChooser.showOpenDialog(null);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    client.sendFile(fileChooser.getSelectedFile());
//                }
            }
        });
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
        setTitle("User: " + UserSession.getUsername());
        setSize(ConfigurationDialog.WIDTH, ConfigurationDialog.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(chatPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

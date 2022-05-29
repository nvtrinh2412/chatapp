package chatscreens;

import chatsession.UserSession;
import server.Client;
import server.ClientHandler;

import javax.swing.*;

public class MainScreen extends JFrame {
    private JPanel mainPanel;

    public MainScreen(String title){
        Client client = UserSession.getClient();
        System.out.println(client);
        createUIComponents(title);
    }

    private void createUIComponents(String title) {
        setTitle(title);
        setSize(ConfigurationDialog.WIDTH, ConfigurationDialog.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

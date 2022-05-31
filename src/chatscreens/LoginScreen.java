package chatscreens;

import database.Database;
import chatsession.UserSession;
import server.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class LoginScreen extends JFrame {
    private JTextField username;
    private JPasswordField password;
    private JButton btnLogin;
    private JButton btnSignUp;
    private JPanel loginPanel;

    public LoginScreen() {
        setLocationRelativeTo(null);
        createUIComponents();
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsername = username.getText();
                String inputPassword = password.getText();
                try {
                    handleLogin(inputUsername,inputPassword);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new SignUpScreen();
            }
        });
    }

    private void createUIComponents() {
        setTitle("Login");
        setSize(ConfigurationDialog.WIDTH, ConfigurationDialog.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(loginPanel);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void handleLogin(String username, String password) throws IOException {
        if(Database.checkValidLogin(username,password)){

            Socket socket =  new Socket("localhost", 1234);
            Client client = new Client(socket,username);
            client.sendUsername();

            UserSession.setUsername(username);
            UserSession.setClient(client);
            UserSession.setSocket(socket);
//
            new ChatScreen();


            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Username or Password is incorrect!","Login fail",JOptionPane.ERROR_MESSAGE);
        }
    }

}

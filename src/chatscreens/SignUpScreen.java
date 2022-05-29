package chatscreens;

import Database.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpScreen extends JDialog{
    private JTextField username;
    private JPasswordField password;
    private JButton signUpButton;
    private JPanel signUpPanel;

    public SignUpScreen(){
        ConfigurationDialog.installBasicConfiguration(this,"Sign up",signUpPanel);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsername = username.getText();
                String inputPassword = password.getText();
                if(inputUsername.equals("") || inputPassword.equals("")){
                    JOptionPane.showMessageDialog(null, "Please fill in your account information");
                }
                else if(Database.isExistUser(inputUsername)){
                    JOptionPane.showMessageDialog(null, inputUsername+" is already existed");
                }
                else{
                    Database.addUser(inputUsername,inputPassword);
                    JOptionPane.showMessageDialog(null, "Succeed","Succeed",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
    }
}

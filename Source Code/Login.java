import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

/**
 * a class used to sign a user in
 */

public class Login extends JFrame {
    //components
    private JLabel labelUserName;
    private JLabel labelPassword;
    private JTextField textUserName;
    private JPasswordField textPassword;
    private JButton buttonLogin;
    private JButton buttonCancel;

    //other objects
    private ArrayList<User> users;
    private MainWindow mainWindow;

    public Login(MainWindow parentWindow, ArrayList<User> users){
        //setup frame
        setLayout(null);
        this.setTitle("Login");
        this.setBounds(0,0,350,150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.users = users;
        this.mainWindow = parentWindow;

        createComponents();
        addComponents();
        setComponentBounds();
        createActionListeners();

        this.setVisible(true);
    }

    //check user has valid log in, return logged-in user if successful
    private User checkUser(String username, char[] password) throws LoginException{
        for (User user : users){
            if (user.checkLogin(username, String.valueOf(password))){
                return user;
            }
        }
        //login failed...
        throw new LoginException();
    }

    // upon successful login, modify main frame to show relevant components
    private void loginSuccess(User loggedInUser){
        mainWindow.setLoginSuccess(true, loggedInUser);
        mainWindow.setEnabled(true);
        mainWindow.setVisible(true);
        dispose();
    }

    //upon unsuccessful log in, reset text fields and display error
    private void loginFail(String message){
        textUserName.setText("");
        textPassword.setText("");
        JOptionPane.showMessageDialog(getParent(), message);
    }

    private void createComponents(){
        labelUserName = new JLabel("Username:");
        labelPassword = new JLabel("Password:");
        textUserName = new JTextField();
        textPassword = new JPasswordField();
        buttonLogin = new JButton("Login");
        buttonCancel = new JButton("Cancel");
    }

    private void addComponents(){
        this.add(labelUserName);
        this.add(labelPassword);
        this.add(textUserName);
        this.add(textPassword);
        this.add(buttonLogin);
        this.add(buttonCancel);
    }

    private void setComponentBounds(){
        labelUserName.setBounds(10,20,100,20);
        labelPassword.setBounds(10,40,100,20);
        textUserName.setBounds(100,20,200,20);
        textPassword.setBounds(100,40,200,20);
        buttonLogin.setBounds(100, 70, 80, 30);
        buttonCancel.setBounds(200,70, 80,30 );
    }

    private void createActionListeners(){
        buttonLogin.addActionListener(e -> {
            if (buttonLogin.isEnabled()){
                try{
                    User loggedInUser = checkUser(textUserName.getText(), textPassword.getPassword());
                    loginSuccess(loggedInUser);
                }
                catch (LoginException l){
                    loginFail(l.getMessage());
                }
            }
        });

        buttonCancel.addActionListener(e -> {
            if (buttonCancel.isEnabled()){
                mainWindow.setEnabled(true);
                mainWindow.setVisible(true);
                dispose();
            }
        });

        //if user presses X to close window
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                mainWindow.setEnabled(true);
                mainWindow.setVisible(true);
                e.getWindow().dispose();
            }
        });
    }

    static class LoginException extends Exception{
        public LoginException(){
            super("Invalid login details entered");
        }
    }
}

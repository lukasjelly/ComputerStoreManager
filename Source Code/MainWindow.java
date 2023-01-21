import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Displays the main GUI window
 * This class holds all computer data and users
 */


public class MainWindow extends JFrame {
    private boolean loginSuccess;
    private final Store computerStore;
    private final JButton signInButton;
    private UpdatePanel updatePanel;
    private JTabbedPane tabbedPane;
    private User loggedInUser;

    public MainWindow(String frameTitle) {
        //set frame parameters
        this.setTitle(frameTitle);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.loginSuccess = false;

        //create new store
        this.computerStore = new Store("Computer Store");

        //add users to store
        computerStore.addUser("Staff 1", "p1", "p1", "Salesperson");
        computerStore.addUser("Staff 2", "p2", "p2", "Salesperson");
        computerStore.addUser("Staff 3", "p3", "p3", "Salesperson");
        computerStore.addUser("Staff 4", "m1", "m1", "Manager");
        computerStore.addUser("Staff 5", "m2", "m2", "Manager");

        //load computer data into store object
        computerStore.addComputers("computers.txt");

        //setup login button
        signInButton = new JButton("Click to login", new ImageIcon("computerStore.jpg"));
        signInButton.setHorizontalAlignment(SwingConstants.CENTER);
        signInButton.setFont(new Font("helvetica", Font.PLAIN ,40));

        //add action listener for sign in button click
        signInButton.addActionListener(e -> {
            if (signInButton.isEnabled() && !loginSuccess){
                new Login(this,computerStore.getUsers());
                this.setEnabled(false);
                //login success determined by login class
                //dialog would have been better but had already implemented login Jframe class
            }
            if (signInButton.isEnabled() && loginSuccess){
                setLoginSuccess(false, null);
            }
        });

        //create login panel
        JPanel loginPanel = new JPanel();
        loginPanel.add(signInButton);
        this.add(loginPanel, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

    //sets status of logged-in user
    public void setLoginSuccess(boolean login, User loggedInUser) {
        if (login) {
            this.loginSuccess = true;
            this.loggedInUser = loggedInUser; //used by updatePanel to determine if user can edit or not.
            signInButton.setText("Click to Log out");
            loggedInComponents(true);

        }
        if (!login) {
            this.loginSuccess = false;
            this.loggedInUser = loggedInUser; //will be null
            signInButton.setText("Click to login");
            loggedInComponents(false);

        }
    }

    //setup components after user is logged in
    private void loggedInComponents(boolean enable){
        if (enable){
            //create panels
            ComputerTablePanel computerTablePanel = new ComputerTablePanel(computerStore, this);
            this.updatePanel = new UpdatePanel(computerStore, this);

            //add to tabbed pane
            this.tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Browse Products", computerTablePanel);
            tabbedPane.addTab("Check/Update Product Details", updatePanel);

            //add to main frame
            this.add(tabbedPane, BorderLayout.NORTH);
        }
        if (!enable){
            this.remove(tabbedPane);
        }
        this.repaint();
        this.pack();
    }

    //load "check/update product details" panel/tab with computer selected in ComputerTable
    public void loadUpdatePanel(Computer computer){
        this.updatePanel.setSelectedComputer(computer);
        this.updatePanel.loadComputer();
        this.updatePanel.checkUserAccess();
        tabbedPane.setSelectedComponent(updatePanel);
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }
}

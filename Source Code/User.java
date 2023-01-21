/**
 * user class which represents a user.
 * Is used to log in a user.
 * Can determine access rights depending on the role property.
 * It was deemed unnecessary to create a base class as the properties for a "manager" and "salesperson" are the same
 * If there were unique properties for different types of users, a base class would be created.
 * Please advise if I have misunderstood something or there is a better way of doing this!
 */

public class User {
    private String name;
    private String username;
    private String password;
    private String role;

    public User(String name, String userName, String password, String role){
        this.name = name;
        this.username = userName;
        this.password = password;
        this.role = role;
    }

    //used to determine if log in details are valid
    public boolean checkLogin(String username, String password){
        if (this.username.equals(username) && this.password.equals(password)){
            return true;
        }
        return false;
    }

    //used to determine what access rights a user has
    public String getRole(){
        return role;
    }
}

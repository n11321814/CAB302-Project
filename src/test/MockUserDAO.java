import QUT.CAB302.fortunecookie.User;
import QUT.CAB302.fortunecookie.UserDAO;

import java.util.ArrayList;
import java.util.List;

// Mock implementation of the Database using array lists for the unit tests
public class MockUserDAO implements UserDAO {

    // Creates users as an array list
    private List<User> users = new ArrayList<>();

    // Registers user to the list
    @Override
    public boolean registerUser(String username, String password) {
        for (User user: users){
            if(user.getUsername().equals(username)){
                return false; //Username taken
            }
        }

        users.add(new User(username, password));
        return true;
    }

    // Authenticates users against the array list
    @Override
    public User loginUser(String username,String password){
        for(User user : users){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}

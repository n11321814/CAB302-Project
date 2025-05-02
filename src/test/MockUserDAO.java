import QUT.CAB302.fortunecookie.User;
import QUT.CAB302.fortunecookie.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements UserDAO {
    private List<User> users = new ArrayList<>();

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

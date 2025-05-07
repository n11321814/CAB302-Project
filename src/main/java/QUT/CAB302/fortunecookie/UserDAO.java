package QUT.CAB302.fortunecookie;

public interface UserDAO {
    boolean registerUser(String username, String password, String email, String phone);
    User loginUser(String username, String password);
}

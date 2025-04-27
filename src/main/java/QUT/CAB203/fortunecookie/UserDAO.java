package QUT.CAB203.fortunecookie;

public interface UserDAO {
    boolean registerUser(String username, String password);
    User loginUser(String username, String password);
}

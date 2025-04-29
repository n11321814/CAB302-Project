package QUT.CAB203.fortunecookie;

public class UserDAOInstance {
    private static UserDAO instance = new UserDAODatabase();

    public static UserDAO getInstance() {
        return instance;
    }
}

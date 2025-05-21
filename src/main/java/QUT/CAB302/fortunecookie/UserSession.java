package QUT.CAB302.fortunecookie;

public class UserSession {
    private static int userId;

    public static void setUserId(int id) {
        userId = id;
    }
    public static int getUserId() {
        return userId;
    }
}

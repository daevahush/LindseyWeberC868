package Model;

public class User {

    private static String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public static String getUserName() {
        return userName;
    }

}

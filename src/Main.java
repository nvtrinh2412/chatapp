import database.Database;
import chatscreens.LoginScreen;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Database database = new Database();
       new LoginScreen();
    }
}

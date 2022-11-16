import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbConnectionTest {

    @Test
    public void testConnect() throws SQLException {
        //preparation des donnees
        DBConnection db1, db2;
        db1 = DBConnection.getInstance();
        db2 = DBConnection.getInstance();
        java.sql.Connection c1;
        Connection c2;
        //methode à tester
         c1 = DBConnection.getConnect();
        c2 = DBConnection.getConnect();
        //verification
        assertEquals(c1, c2, "ça devrait etre la meme connection");
    }


}

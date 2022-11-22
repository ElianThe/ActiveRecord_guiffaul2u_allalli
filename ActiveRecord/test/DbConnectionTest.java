import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    @Test
    public void testSetConnection() throws SQLException{
        //preparation des donnees
        DBConnection db1;
        db1 = DBConnection.getInstance();
        Connection c1, c2;
        c1 = DBConnection.getConnect();
        db1.setNomDB("saes3");
        //methode à tester
        c2 = DBConnection.getConnect();
        //verification
        assertNotEquals(c1, c2, "une connection vers saes3 devrait etre fait");
    }

}

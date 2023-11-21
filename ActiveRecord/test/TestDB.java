import activeRecord.DBConnexion;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestDB {
    @Test
    public void testDB() throws SQLException {
        Connection connect = DBConnexion.getInstance();
        Connection connect2 = DBConnexion.getInstance();
        assertEquals(connect, connect2);
    }
    @Test
    public void testNomDB() throws SQLException {
        String nomBase = DBConnexion.getNomDB();
        DBConnexion.setNomDB("test");
        String nomBase2 = DBConnexion.getNomDB();
        assertNotEquals(nomBase, nomBase2);
    }
}

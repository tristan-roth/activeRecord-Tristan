package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personne {
    private int id;
    private String nom;
    private String prenom;
    public Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        /*Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("SELECT * FROM personne");

         */
        return null;
    }
}

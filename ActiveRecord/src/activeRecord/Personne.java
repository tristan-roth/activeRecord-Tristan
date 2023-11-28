package activeRecord;

import java.sql.*;
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
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("SELECT * FROM personne");
        ResultSet rs = p.executeQuery();
        ArrayList<Personne> personnes = new ArrayList<>();
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            personnes.add(new Personne(nom, prenom));
        }
        return personnes;
    }
    public static Personne findById(int ID) throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("SELECT * FROM personne where id=?");
        p.setInt(1, ID);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            Personne pers = new Personne(rs.getString("nom"), rs.getString("prenom"));
            pers.id = rs.getInt("id");
            return pers;
        }
        return null;
    }
    public static ArrayList<Personne> findByName(String nom) throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("SELECT * FROM personne where nom=?");
        p.setString(1, nom);
        ResultSet rs = p.executeQuery();
        ArrayList<Personne> personnes = new ArrayList<>();
        while (rs.next()) {
            Personne pers = new Personne(nom, rs.getString("prenom"));
            pers.id = rs.getInt("id");
            personnes.add(pers);
        }
        return personnes;
    }
    public static void createTable() throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("CREATE TABLE personne (id INT PRIMARY KEY AUTO_INCREMENT, nom VARCHAR(40), prenom VARCHAR(40))");
        p.execute();
    }
    public static void deleteTable() throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("DROP TABLE personne");
        p.execute();
    }
    public void save () throws SQLException {
        Connection connect = DBConnexion.getInstance();
        if (this.id == -1){
            PreparedStatement p = connect.prepareStatement("INSERT INTO personne (nom, prenom) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            p.setString(1, this.nom);
            p.setString(2, this.prenom);
            p.execute();
            ResultSet rs = p.getGeneratedKeys();
            rs.next();
            this.id = rs.getInt(1);
        }
        else {
            PreparedStatement p = connect.prepareStatement("UPDATE personne SET nom=?, prenom=? WHERE id=?");
            p.setString(1, this.nom);
            p.setString(2, this.prenom);
            p.setInt(3, this.id);
            p.execute();
        }
    }
    public void delete () throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("DELETE FROM personne WHERE id=?");
        p.setInt(1, this.id);
        this.id = -1;
        p.execute();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
      this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}

package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Film {
    String titre;
    int id;
    int id_real;
    public Film(String titre, Personne rea){
        this.titre = titre;
        this.id = -1;
        this.id_real = rea.getId();
    }
    private Film(ArrayList<Integer> ids, String titre){
        //pas encore compris
    }

    public static Film findById(int id) throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("SELECT * FROM film where id=?");
        p.setInt(1, id);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            Film film = new Film(rs.getString("titre"), Personne.findById(rs.getInt("id_real")));
            film.id = rs.getInt("id");
            return film;
        }
        return null;
    }

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    public static void createTable() throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("CREATE TABLE film (id INT PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(40), id_real INT, FOREIGN KEY (id_real) REFERENCES personne(id))");
        p.execute();
    }
    public static void deleteTable() throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("DROP TABLE film");
        p.execute();
    }

    public void save() throws SQLException, RealisateurAbsentException {
        Connection connect = DBConnexion.getInstance();
        if (this.id_real == -1){
            throw new RealisateurAbsentException("Le realisateur n'est pas dans la base de donnees mon gros");
        }
        PreparedStatement p = connect.prepareStatement("INSERT INTO film (titre, id_real) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        p.setString(1, this.titre);
        p.setInt(2, this.id_real);
        p.executeUpdate();
        ResultSet rs = p.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }
    public void delete() throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("DELETE FROM film WHERE id=?");
        p.setInt(1, this.id);
        p.executeUpdate();
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public static ArrayList<Film> findByRealisateur(Personne realisateur) throws SQLException {
        Connection connect = DBConnexion.getInstance();
        PreparedStatement p = connect.prepareStatement("SELECT * FROM film where id_real=?");
        p.setInt(1, realisateur.getId());
        ResultSet rs = p.executeQuery();
        ArrayList<Film> films = new ArrayList<>();
        while (rs.next()) {
            Film film = new Film(rs.getString("titre"), realisateur);
            film.id = rs.getInt("id");
            films.add(film);
        }
        return films;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}

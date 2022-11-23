import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Film {
    private String titre;

    private int id, id_real;

    public Film(String titre, Personne real){
        this.titre = titre;
        this.id_real = real.getId();
        this.id = -1;
    }

    public Film(String titre, int id, int id_real){
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

    public static Film findById(int id) throws SQLException {
        Connection connect = DBConnection.getConnect();
    String statement = "SELECT TITRE, ID_REA from film where ID = ?";
        PreparedStatement ps = connect.prepareStatement(statement);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        Film film = null;
        while (rs.next()){
            String titre = rs.getString(1);
            int id_rea = rs.getInt(2);
            film = new Film(titre, id, id_rea);
        }
        return film;
    }

    public Personne getRealiateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public int getId_real() {
        return id_real;
    }
}

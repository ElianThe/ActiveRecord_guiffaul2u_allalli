import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public class TestPersonne {

    @BeforeEach
    public void initTable() throws SQLException {
        // Utilisation de la méthode statique createTable
        Personne.createTable();
        // On crée des nouvelles Personne qu'on ajoute à la table
        (new Personne("Spielberg", "Steven")).save();
        (new Personne("Scott", "Ridley")).save();
        (new Personne("Kubrick","Stanley")).save();
        (new Personne("Fincher", "David")).save();
    }

    @AfterEach
    public void deleteTable() throws SQLException {
        // Utilisation de la méthode statique deleteTable
        Personne.deleteTable();
    }
}


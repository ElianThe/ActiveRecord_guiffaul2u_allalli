import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPersonne {

    Personne personne;

    @BeforeEach
    public void initTable() throws SQLException {
        DBConnection db = DBConnection.getInstance();
        // Utilisation de la méthode statique createTable
        Personne.createTable();
        // On crée des nouvelles Personne qu'on ajoute à la table
        personne = new Personne("Spielberg", "Steven");
        personne.save();
        (new Personne("Scott", "Ridley")).save();
        (new Personne("Kubrick","Stanley")).save();
        (new Personne("Fincher", "David")).save();
    }

    @Test
    public void testFindAll() throws SQLException {
        //methode testee
        ArrayList<Personne> personnes = Personne.findAll();

        //verification des resultats
        assertEquals(4, personnes.size(), "la taille total de l'²arraylist doit etre de");
    }


    @Test
    public void testFindByName() throws SQLException {
        //methode testee
        (new Personne("Spielberg", "Stivi")).save();
        ArrayList<Personne> personnes = Personne.findByName("Spielberg");

        //verification des resultats
        assertEquals(2, personnes.size(), "La taille total de l'arraylisr doit etre de 2");
    }


    @Test
    public void testFindById() throws SQLException {
        //methode testee
        Personne pers =  Personne.findById(1);

        //verification des resultats
        assertEquals(personne, pers, "ça devrait etre le meme objet");
    }

    @Test
    public void testdelete() throws SQLException {
        //methode testee
        personne.delete();
        Personne pers = Personne.findById(1);

        //verification des resultats
        assertThrows(NullPointerException.class, (Executable) pers);
    }

    @Test
    public void testSaveUpdate() throws SQLException {
        //preparation des donnees
        personne.setNom("SpielChange");
        personne.setPrenom("Dimeh");

        //methode testee
        personne.save();

        //verification des resultats
        assertEquals("SpielChange", personne.getNom(), "Le nom devrait etre SpielChange");
        assertEquals("Dimeh", personne.getPrenom(), "Le prenom devrait etre Dimeh");
    }

    @Test
    public void testSaveNew () throws SQLException {
        //preparation des donnees
        Personne personne1 = new Personne("trackala", "Anas");

        //methode testee
        personne1.save();

        //verification
        assertEquals("Anas", personne1.getPrenom(), "Le prenom devrait etre Anas");
        assertEquals("trackala", personne1.getNom(), "le nom devrait etre trackala");

    }

    @AfterEach
    public void deleteTable() throws SQLException {
        // Utilisation de la méthode statique deleteTable
        Personne.deleteTable();
    }
}


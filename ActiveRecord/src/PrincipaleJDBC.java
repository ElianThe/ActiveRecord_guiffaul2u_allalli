import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class PrincipaleJDBC {

	// IL FAUT PENSER A AJOUTER MYSQLCONNECTOR AU CLASSPATH

	public static void main(String[] args) throws SQLException {

		DBConnection dbConnection = DBConnection.getInstance();
		Connection connect = DBConnection.getConnect();

		{
			// creation de la table Personne
			Personne.createTable();
		}

		{
			Personne personne = new Personne("Spielberg", "Steven");
			personne.save();
			System.out.println("2) ajout Steven Spielberg\n");
		}


		{
			Personne personne2 = new Personne("Scott","Ridley");
			personne2.save();
			System.out.println("3) ajout Ridley Scott");
			System.out.println();
		}


		{
			//findAll
			System.out.println("4) Recupere les personnes de la table Personne");
			ArrayList<Personne> personnes = Personne.findAll();
		}

		{
			Personne personne = Personne.findById(1);
			personne.delete();
		}

		{
			System.out.println("6) Recupere personne d'id 2");
			//findById
			System.out.println("findById");
			Personne.findById(2);
			System.out.println();
		}


		{
			Personne personne = Personne.findById(2);
			personne.setNom("S_c_o_t_t");
			personne.setPrenom("R_i_d_l_e_y");
			personne.save();
			System.out.println();
		}

		// recuperation de la seconde personne + affichage
		{
			System.out.println("8) Affiche Personne id 2 apres modification");
			Personne pers = Personne.findById(2);
			System.out.println();
		}


		{
			Personne.deleteTable();
		}

	}

}

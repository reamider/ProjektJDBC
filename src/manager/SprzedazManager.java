package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.Sprzedaz;
import main.Klient;
import main.Bilet;

public class SprzedazManager {
	private static Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/";
	private String createTableSprzedaz = "CREATE TABLE Sprzedaz(id_klient int references Klient(id_klient), id_bilet int references Bilet(id_bilet))";

	private static PreparedStatement DodajSprzedaz;
	private static PreparedStatement usunSprzedaz;
	private static PreparedStatement UsunSprzedaze;
	private static PreparedStatement PobierzSprzedaz;
	private static PreparedStatement EdytujSprzedaz;
	private static PreparedStatement PobierzSprzedazPoKliencie;

	private Statement statement;
	
	public SprzedazManager(){
		try{
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while(rs.next()){
				if("Sprzedaz".equalsIgnoreCase(rs.getString("TABLE_NAME"))){
					tableExists = true;
					break;
					
				}
			}
			
			if(!tableExists)
				statement.executeUpdate(createTableSprzedaz);
			
			DodajSprzedaz = connection.prepareStatement("INSERT INTO Sprzedaz(id_klient, id_bilet) VALUES (?, ?)");
			UsunSprzedaze = connection.prepareStatement("DELETE FROM Sprzedaz where id_klient = ?");
			usunSprzedaz = connection.prepareStatement("DELETE FROM Sprzedaz where id_klient = ? and id_bilet = ?");
			PobierzSprzedaz = connection.prepareStatement("SELECT id_klient, id_bilet FROM Sprzedaz");
			PobierzSprzedazPoKliencie = connection.prepareStatement("SELECT id_klient, id_bilet FROM Sprzedaz WHERE id_klient = ?");
			EdytujSprzedaz = connection.prepareStatement("UPDATE Sprzedaz SET id_bilet = ? WHERE id_klient = ?");
			
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return connection;
	}
	
	public static void wyczyscSprzedaz(){
		try{
			UsunSprzedaze.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static int dodajSprzedaz(Klient k, Bilet b){
		int licznik = 0;
		try {
			DodajSprzedaz.setInt(1, k.getId());
			DodajSprzedaz.setInt(2, b.getId());
			licznik = DodajSprzedaz.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public static int usunSprzedaz(Klient k, Bilet b){
		int licznik = 0;
		try{
			usunSprzedaz.setInt(1, k.getId());
			usunSprzedaz.setInt(2, b.getId());
			licznik = usunSprzedaz.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public static List<Sprzedaz> PobierzSprzedaz(){
		List<Sprzedaz> sprzedaz = new ArrayList<Sprzedaz>();
		
		try{
			ResultSet rs = PobierzSprzedaz.executeQuery();
			
			while(rs.next()){
				Sprzedaz s = new Sprzedaz();
				s.setId_klient(rs.getInt("id_klient"));
				s.setId_bilet(rs.getInt("id_bilet"));
				sprzedaz.add(s);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		return sprzedaz;
	}
	
	public int EdytujSprzedaz(Klient k, Bilet b){
		int licznik = 0;
		try{
			EdytujSprzedaz.setInt(1, b.getId());
			EdytujSprzedaz.setInt(2, k.getId());
			licznik = EdytujSprzedaz.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	//POBRANIE X NALEZACYCH DO Y - POBRANIE BILETOW PO KLIENCIE ZE SPRZEDAZY
	public List<Bilet> PobierzSprzedazPoKliencie(Sprzedaz s) {
		List<Bilet> bilety = new ArrayList<Bilet>();

		try {

			PobierzSprzedazPoKliencie.setInt(1, s.getId_klient());

			ResultSet rs = PobierzSprzedazPoKliencie.executeQuery();

			while (rs.next()) {
				Bilet b = new Bilet();
				b.setId(rs.getInt("id_bilet"));
				b.setRodzaj(rs.getString("rodzaj"));
				b.setCena(rs.getDouble("cena"));
				bilety.add(b);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bilety;
	}
}
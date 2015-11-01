package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SprzedazManager {
	private static Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	private String createTableSprzedaz = "CREATE TABLE Sprzedaz(klient_id_klient long, bilet_id_bilet long, datagodzina varchar(100), CONSTRAINT fk1 FOREIGN KEY(klient_id_klient) REFERENCES Klient(id_klient), CONSTRAINT fk2 FOREIGN KEY(bilet_id_bilet) REFERENCES Bilet(id_bilet))";

	private static PreparedStatement DodajSprzedaz;
	private static PreparedStatement UsunSprzedaz;
	private static PreparedStatement PobierzSprzedaz;
	
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
			
			DodajSprzedaz = connection.prepareStatement("INSERT INTO Sprzedaz(klient_id_klient, bilet_id_bilet, datagodzina) VALUES (?, ?, ?)");
			UsunSprzedaz = connection.prepareStatement("DELETE * FROM Sprzedaz");
			PobierzSprzedaz = connection.prepareStatement("SELECT * FROM Sprzedaz");
			
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return connection;
	}
	
	public static void wyczyscSprzedaz(){
		try{
			UsunSprzedaz.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static int dodajSprzedaz(Sprzedaz sprzedaz){
		int count = 0;
		try {
			DodajSprzedaz.setLong(1, sprzedaz.getKlient_id_klient());
			DodajSprzedaz.setLong(2, sprzedaz.getBilet_id_bilet());
			DodajSprzedaz.setString(3, sprzedaz.getDatagodzina());
			
			count = DodajSprzedaz.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}
	
	public static List<Sprzedaz> PobierzSprzedaz(){
		List<Sprzedaz> sprzedaz = new ArrayList<Sprzedaz>();
		
		try{
			ResultSet rs = PobierzSprzedaz.executeQuery();
			
			while(rs.next()){
				Sprzedaz s = new Sprzedaz();
				s.setKlient_id_klient(rs.getLong("klient_id_klient"));
				s.setBilet_id_bilet(rs.getLong("bilet_id_bilet"));
				s.setDatagodzina(rs.getString("datagodzina"));
				sprzedaz.add(s);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		return sprzedaz;
	}
}

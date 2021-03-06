package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.Klient;

public class KlientManager {
	private Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/";
	private String createTableKlient = "CREATE TABLE Klient(id_klient int GENERATED BY DEFAULT AS IDENTITY, imie varchar(50), nazwisko varchar(100), numertelefonu varchar(50))";
	
	private PreparedStatement DodajKlienta;
	private PreparedStatement UsunKlienta;
	private PreparedStatement UsunKlientow;
	private PreparedStatement EdytujKlienta;
	private PreparedStatement PobierzKlientow;
	private Statement statement;
	
	public KlientManager(){
		try{
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while(rs.next()){
				if("Klient".equalsIgnoreCase(rs.getString("TABLE_NAME"))){
						tableExists = true;
						break;
			}
		}
			
		if(!tableExists){
			statement.executeUpdate(createTableKlient);
		}
		DodajKlienta = connection.prepareStatement("INSERT INTO Klient(imie, nazwisko, numertelefonu) VALUES (?, ?, ?)"); 
		UsunKlienta = connection.prepareStatement("DELETE FROM Klient where id_klient = ?");
		UsunKlientow = connection.prepareStatement("DELETE FROM Klient");
		EdytujKlienta = connection.prepareStatement("UPDATE Klient set imie = ?, nazwisko = ?, numertelefonu = ? where id_klient = ?");
		PobierzKlientow = connection.prepareStatement("SELECT id_klient, imie, nazwisko, numertelefonu FROM Klient");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public void wyczyscKlienta(){
		try{
			UsunKlientow.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public int DodajKlienta(Klient klient){
		int licznik = 0;
		try{
			DodajKlienta.setString(1, klient.getImie());
			DodajKlienta.setString(2, klient.getNazwisko());
			DodajKlienta.setString(3, klient.getNumertelefonu());
			licznik = DodajKlienta.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public int UsunKlienta(Klient klient){
		int licznik = 0;
		try{
			UsunKlienta.setInt(1, klient.getId());
			licznik = UsunKlienta.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public int EdytujKlienta(Klient klient){
		int licznik = 0;
		try{
			EdytujKlienta.setString(1, klient.getImie());
			EdytujKlienta.setString(2, klient.getNazwisko());
			EdytujKlienta.setString(3, klient.getNumertelefonu());
			EdytujKlienta.setInt(4, klient.getId());
			licznik = EdytujKlienta.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return licznik;
	}
	
	public List<Klient> PobierzKlientow(){
		List<Klient> klienci = new ArrayList<Klient>();
		try{
			ResultSet rs = PobierzKlientow.executeQuery();
			while(rs.next()){
				Klient k = new Klient();
				k.setId(rs.getInt("id_klient"));
				k.setImie(rs.getString("imie"));
				k.setNazwisko(rs.getString("nazwisko"));
				k.setNumertelefonu(rs.getString("numertelefonu"));
				klienci.add(k);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return klienci;
	}
}
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class KlientManager {
	private Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	private String createTableKlient = "CREATE TABLE Klient(id_klient long GENERATED BY DEFAULT AS IDENTITY, imie varchar(50), nazwisko varchar(100), numertelefonu long)";
	
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
		UsunKlientow = connection.prepareStatement("DELETE * FROM Klient");
		EdytujKlienta = connection.prepareStatement("UPDATE Klient set imie = ?, nazwisko = ?, numertelefonu = ? where id_klient = ?");
		PobierzKlientow = connection.prepareStatement("SELECT * FROM Klient");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}

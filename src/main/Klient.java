package main;

public class Klient {
	private int id_klient;
	private String imie;
	private String nazwisko;
	private long numertelefonu;
	
	public Klient() {}
	
	public Klient(String imie, String nazwisko, long numertelefonu){
		super(); 
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.numertelefonu = numertelefonu;
	}
	
	public int getId(){
		return id_klient;
	}
	
	public void setId(int id_klient){
		this.id_klient = id_klient;
	}
	
	public String getImie(){
		return imie;
	}
	
	public void setImie(String imie){
		this.imie = imie;
	}
	
	public String getNazwisko(){
		return nazwisko;
	}
	
	public void setNazwisko(String nazwisko){
		this.nazwisko = nazwisko;
	}
	
	public long getNumertelefonu(){
		return numertelefonu;
	}
	
	public void setNumertelefonu(long numertelefonu){
		this.numertelefonu = numertelefonu;
	}
}

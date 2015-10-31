package main;

public class Bilet {
	static long id_bilet;
	private String rodzaj;
	private String cena;
	private String opis;
	
	public Bilet() {}
	
	public Bilet(String rodzaj, String cena, String opis){
		super();
		this.rodzaj = rodzaj;
		this.cena = cena;
		this.opis = opis;
	}
	
	public long getId(){
		return id_bilet;
	}
	
	public void setId(long id_bilet){
		this.id_bilet = id_bilet;
	}
	
	public String getRodzaj(){
		return rodzaj;
	}
	
	public void setRodzaj(String rodzaj){
		this.rodzaj = rodzaj;
	}
	
	public String getCena(){
		return cena;
	}
	
	public void setCena(String cena){
		this.cena = cena;
	}
	
	public String getOpis(){
		return opis;
	}
	
	public void setOpis(String opis){
		this.opis = opis;
	}
}

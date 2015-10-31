package main;

public class Sprzedaz {
	private long klient_id_klient;
	private long bilet_id_bilet;
	
	public Sprzedaz(){}
	
	public long getKlient_id_klient(){
		return klient_id_klient;
	}
	
	public void setKlient_id_klient(long klient_id_klient){
		this.klient_id_klient = klient_id_klient;
	}
	
	public long getBilet_id_bilet(){
		return bilet_id_bilet;
	}
	
	public void setBilet_id_bilet(long bilet_id_bilet){
		this.bilet_id_bilet = bilet_id_bilet;
	}
}

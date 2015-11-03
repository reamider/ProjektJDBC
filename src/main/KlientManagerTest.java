package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;

public class KlientManagerTest {
KlientManager klientManager = new KlientManager();
	
	private final static String imie = "Jan";
	private final static String nazwisko = "Kowalski";
	private final static String numertelefonu = "+48501601701";
	private final static String imie2 = "Adam";
	private final static String nazwisko2 = "Malinowski";
	private final static String numertelefonu2 = "+48600700800";
	
	
	@Test
	public void checkConnection(){
		assertNotNull(klientManager.getConnection());
	}
	
	@Test
	public void checkAdding(){
		Klient klient = new Klient(imie, nazwisko, numertelefonu);
		
		
		List<Klient> k = klientManager.PobierzKlientow();
		Klient aktualnyklient = k.get(0);
		
		assertEquals(imie, aktualnyklient.getImie());
		assertEquals(nazwisko, aktualnyklient.getNazwisko());
		assertEquals(numertelefonu, aktualnyklient.getNumertelefonu());
	}
	
	@Test
	public void checkUpdate(){
		Klient klient = new Klient(imie, nazwisko, numertelefonu);

		klientManager.wyczyscKlienta();
		assertEquals(1, klientManager.DodajKlienta(klient));
		
		List<Klient> k = klientManager.PobierzKlientow();
		Klient aktualnyklient = k.get(0);
		
		aktualnyklient.setImie("Adrian");
		aktualnyklient.setNazwisko("Malinowski");
		aktualnyklient.setNumertelefonu("+48600700800");
		
		assertEquals(1, klientManager.EdytujKlienta(aktualnyklient));
		
		List<Klient> k2 = klientManager.PobierzKlientow();
		Klient aktualnyklient2 = k2.get(0);
		
		assertEquals(imie2, aktualnyklient2.getImie());
		assertEquals(nazwisko2, aktualnyklient2.getNazwisko());
		assertEquals(numertelefonu2, aktualnyklient2.getNumertelefonu());
		assertEquals(aktualnyklient2.getId(), aktualnyklient2.getId());
	}
	
	@Test
	public void checkDelete() {
		Klient klient = new Klient(imie, nazwisko, numertelefonu);

		klientManager.wyczyscKlienta();
		assertEquals(1, klientManager.DodajKlienta(klient));
		
		List<Klient> k = klientManager.PobierzKlientow();
		Klient aktualnyklient = k.get(0);

		assertEquals(1, klientManager.UsunKlienta(aktualnyklient));

	}
}
package concessionaria;

import java.io.Serializable;

//definisce il tipo Auto (superclasse di AutoNuova e AutoUsata) e implementa l'interfaccia Comparable e Serializable 
public abstract class Auto implements Comparable<Auto>, Serializable{
	private String targa;	//targa dell'auto
	private String modello;	//modello dell'auto
	private int cilindrata;	//cilindrata dell'auto
	private String colore;	//colore dell'auto
	private float prezzo;	//prezzo di vendita dell'auto
	
	//costruttore
	public Auto(String targa, String modello, int cilindrata, String colore, float prezzo) {
		this.targa = targa;
		this.modello = modello;
		this.cilindrata = cilindrata;
		this.colore = colore;
		this.prezzo = prezzo;
	}
	
	//restituisce la targa
	public String getTarga() {
		return this.targa;
	}
	
	//restituisce le informazioni (variabili d'istanza) dell'oggetto auto 
	public String toString() {
		return "modello: "+ this.modello + ", targa: " + this.targa +", cilindrata: "+ this.cilindrata+ ", colore: "+ this.colore +", prezzo: "+ this.prezzo;
	}
	
	//metodo astratto che dovrebbe consentire di stampare le informazioni sulle auto
	public abstract void visualizzaAuto();
		
	//metodo compareTo implemetato da Auto: confronta due targhe.
	public int compareTo(Auto otherObject) {
	    return this.targa.compareTo(otherObject.getTarga());
	}
	
}

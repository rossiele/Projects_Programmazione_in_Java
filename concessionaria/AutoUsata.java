package concessionaria;

import java.io.Serializable;

//definisce il tipo Auto usata (sottoclasse di Auto) e implementa l'interfaccia Serializable
public class AutoUsata extends Auto implements Serializable{

	private int anno; //anno di prima immatricolazione
	private int chilometri; //chilometri percorsi dall'auto
	
	//costruttore
	public AutoUsata(String targa, String modello, int cilindrata, String colore, float prezzo, int anno, int chilometri) {
		super(targa, modello, cilindrata, colore, prezzo);
		this.anno = anno;
		this.chilometri = chilometri;
	}
	
	//restituisce l'anno di immatricolazione
	public int getAnno() {
		return this.anno;
	}
	
	//restituisce i chilometri percorsi
	public int getChilometri() {
		return this.chilometri;
	}
	
	//stampa le informazioni (variabili d'istanza) dell'oggetto auto usata
	public void visualizzaAuto() {
		System.out.println("- Auto usata, anno di prima immatricolazione "+ getAnno() + ", chilometri percorsi: " + getChilometri()+", "+ this.toString());																	
	}
}

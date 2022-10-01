package concessionaria;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//definisce il tipo Auto nuova (sottoclasse di Auto) e implementa l'interfaccia Serializable
public class AutoNuova extends Auto implements Serializable{
	private Date dataConsegna = new Date();	//data di prevista consegna al cliente	
	private String nome;	//nome del cliente
	
	//costruttore
	public AutoNuova(String targa, String modello, int cilindrata, String colore, float prezzo, Date dataConsegna, String nome) {
		super(targa, modello, cilindrata, colore, prezzo);
		this.dataConsegna = dataConsegna;
		this.nome = nome;
	}
	
	//restituisce la data di consegna
	public Date getConsegna() {
		return this.dataConsegna;
	}
	
	//modifica la data di consegna
	public void setConsegna(Date dataNuova) {
		this.dataConsegna = dataNuova;
	}
	
	//restituise il nome del cliente
	public String getNome() {
		return this.nome;
	}
	
	//stampa le informazioni (variabili d'istanza) dell'oggetto auto nuova
	public void visualizzaAuto() {
		SimpleDateFormat  formatter = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("- Auto nuova, nome cliente: "+ this.nome + ", data di prevista consegna: " + formatter.format(this.dataConsegna) +", "+ this.toString());													
	}
	
}

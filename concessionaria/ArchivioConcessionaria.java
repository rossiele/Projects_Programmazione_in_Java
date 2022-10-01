package concessionaria;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;


//contiene main, interfaccia testuale e metodi di tale interfaccia
public class ArchivioConcessionaria {
	public static ArrayList<Auto> automobili = new ArrayList<Auto>(); //lista delle auto in archivio
	
	public static void main(String[] args) {
			
			//creazione dell'oggetto Scanner per input dell'utente
			Scanner input = new Scanner(System.in);
			System.out.print("\n \n ** Benvenuto! **");
			while (true) {
				try {				
					//se sono presenti auto all'interno della lista viene stampato il menù completo
					if (!automobili.isEmpty()) {
						//interfacia testuale 
						System.out.println("\nSeleziona una delle operazioni possibili:");
						System.out.println("[1] Aggiungi auto");
						System.out.println("[2] Rimuovi auto");
						System.out.println("[3] Visualizza elenco auto");
						System.out.println("[4] Visualizza elenco auto nuove");
						System.out.println("[5] Visualizza elenco auto usate");
						System.out.println("[6] Modifica data di prevista consegna (solo per auto nuove)");
						System.out.println("[7] Ricerca auto nuove acquistate da un cliente");
						System.out.println("[8] Ricerca auto usate per anno di immatricolazione e chilometri percorsi");
						System.out.println("[9] Ricerca prossima auto nuova da consegnare");
						System.out.println("[X] Altre opzioni");
					} else { //se non sono presenti auto all'interno della lista viene stampato il menù ridotto
						//interfacia testuale 
						System.out.println("\n \nSeleziona una delle operazioni possibili: ");
						System.out.println("[1] Aggiungi auto");
						System.out.println("[X] Altre opzioni");
					}
					char scelta = input.nextLine().toUpperCase().charAt(0);	//memorizza l'input dell'utente
	
					switch (scelta) {
					case '1':
						aggiungiAuto();	//aggiunge una nuova auto all'archivio
						break;
					case '2':
						rimuoviAuto();	//rimuove un un'auto dall'archivio
						break;
					case '3':
						visualizzaAutoOrdinate();	//visualizza l'elendo di tutte le auto in ordine di targa
						break;
					case '4':
						visualizzaAutoNuove();	//visualizza elenco delle auto nuove
						break;
					case '5':
						visualizzaAutoUsate();	//visualizza elenco delle auto usate
						break;
					case '6':
						modificaDataConsegna();	//modifica la data di prevista consegna di un'auto nuova
						break;
					case '7':
						ricercaAutoNuove();	//ricerca le auto nuove acquistate da un certo cliente
						break;
					case '8':
						ricercaAutoUsate();	//ricerca auto usate per anno immatricolazione e chilometri
						break;
					case '9':
						ricercaConsegna();	//ricerca la prossima auto da consegnare
						break;
					case 'X':
						altreOpzioni();	//permette la scelta di altre opzioni (salva/carica/esci)
						break;
					default:
						//nel caso in cui non vengano soddisfatti le condizioni precedenti, genera un messaggio
						System.out.println("Scelta non consentita");
					}	
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Si è verificato un errore!");
				
			}
			catch (InputMismatchException e) {
				System.out.println("Input scorretto!");
				
			}
		}		
	}
	
	//aggiunge una nuova auto all'archio (lista automobili)
	private static void aggiungiAuto() {
		String targa = null;	//variabile per memorizzare la targa della nuova auto	
		Scanner input = new Scanner(System.in); 
		boolean flag = false;
		System.out.println("Inserisci la targa (deve essere lunga 7 caratteri):");

		//se la lista non è vuota si effettua un controllo
		if (!automobili.isEmpty()) {	
			//ciclo di controllo: verifica che la targa non sia già presente nella lista 
			do {
				targa = input.nextLine().toUpperCase();			
				//controlla se la targa è lunga 7 caratteri
				if (controlloTarga(targa)) {
					for (Auto auto : automobili) {
						//se ha scorso tutta la lista e l'ultimo elmento non ha targa uguale a quella inserita 
						//imposta il controllo a true ed esce dal for
						if (automobili.indexOf(auto) == automobili.size() - 1 && !targa.equals(auto.getTarga())) {
							flag = true;
							break;
						}
						//se l'elemento ha targa uguale a quella inserita esce dal for
						if (targa.equals(auto.getTarga())) {
							System.out.println("Numero targa già inserito. Inserirne un altro");
							break;
						}
					}
				}
			} while (!flag);
		} else {
			//ciclo di controllo: verifica che la targa sia lunga 7 caratteri
			do {
				targa = input.nextLine().toUpperCase();
			} while (!controlloTarga(targa));
		}
		//chiede all'utente tutte le altre informazioni sull'auto da aggiungere
		System.out.println("Inserisci il modello:");
		String modello = input.nextLine();
		System.out.println("Inserisci il colore:");
		String colore = input.nextLine();
		System.out.println("Inserisci il prezzo di vendita:");
		float prezzo = input.nextFloat();
		System.out.println("Inserisci la cilindrata:");
		int cilindrata = input.nextInt();
		
		creaAuto(targa, modello, colore, prezzo, cilindrata);	
	}
	
	//verifica che la targa sia lunga 7 caratteri
	private static boolean controlloTarga(String targa) {
		boolean flag = true;
		//se la targa non è lunga 7 caratteri, stampa un messaggio di errore
		if (targa.length() != 7) {
			flag = false;
			System.out.println("La targa deve essere lunga 7 caratteri! Inserirne un'altra");
		}
		return flag;
	}
	
	
	//definisce il tipo di auto da inserire e richiede informazioni aggiuntive
	private static void creaAuto(String targa, String modello, String colore, float prezzo, int cilindrata) {
		char scelta;	//memorizza l'input dell'utente
		int anno;	//memorizza l'anno di immatricolazione
		Scanner input = new Scanner(System.in);

		//ciclo per verificare che l'utente inserisca l'input opportuno (1 o 2)
		do {
			//interfaccia testuale
			System.out.println("Seleziona il tipo di auto:");
			System.out.println("[1] Nuova");
			System.out.println("[2] Usata");		
			 scelta = input.nextLine().charAt(0);
		} while (scelta != '1' && scelta != '2');
				
		switch (scelta) {
		//se sceglie il tipo auto nuova 
		case '1':					
			//richiede le informazioni necessarie
			System.out.println("Inserisci la data di prevista consegna al cliente [dd/MM/yyyy]");
			Date data = ottieniData();		
			System.out.println("Inserisci il nome del cliente:");
			String nome = input.nextLine();
			//crea l'oggetto di tipo AutoNuova
			AutoNuova a1 = new AutoNuova(targa, modello, cilindrata, colore, prezzo, data, nome);
			//aggiunge l'oggetto all'arraylist
			automobili.add(a1);
			break;
		//se sceglie il tipo auto usata
		case '2':
			//richiede le informazioni necessarie
			System.out.println("Inserisci l'anno di prima immatricolazione [yyyy]:");
			do {				
				anno = input.nextInt();
			}while(!controlloAnno(anno));			
			System.out.println("Inserisci i chilometri percorsi:");
			int chilometri = input.nextInt();
			//crea l'oggetto di tipo AutoUsata
			AutoUsata a2 = new AutoUsata(targa, modello, cilindrata, colore, prezzo, anno, chilometri);
			//aggiunge l'oggetto all'arraylist
			automobili.add(a2);
			break;
		}		
	}
	
	//verifica che sia inserito un numero composta da almeno 4 cifre
	private static boolean controlloAnno(int anno) {
		boolean flag = true;
		//se la targa non è lunga 7 caratteri, stampa un messaggio di errore
		if (anno < 1000 || anno > 9999) {
			flag = false;
			System.out.println("L'anno deve essere composto da 4 cifre. Inserirne un altro:");
		}
		return flag;
	}
	
	//riceve in input la data e la formatta nel formato dd/MM/yyyy
	private static Date ottieniData() {
		Scanner input = new Scanner(System.in);
		boolean flag = false;
		String data = ""; 	//serve per salvare la data inserita
		int dataInput;	//memorizza l'input dell'utente (data)
		String str[] = {"giorno", "mese", "anno" }; //serve per chiedere il giorno, mese e anno all'utente		 
		
		for(int i=0; i<3; i++) {
			flag = false;
			//chiede all'utente il giorno, il mese e l'anno
			System.out.println("Inserisci "+ str[i] + " in cifre: ");		
			switch (str[i]) {
			case "giorno":
				//se si sta chiedendo il giorno verifica che il numero inserito sia compreso tra 1 e 31
				do {					
					dataInput = input.nextInt();
					if (dataInput > 31 || dataInput <= 0) {
						System.out.println("Numero non valido, inserire un numero compreso tra 1 e 31:");
					} else {
						flag = true;
					}
				} while (!flag);
				//concatena il numero inserito con il resto della data
				data = data + dataInput + "/";
				break;
				
			case "mese":
				//se si sta chiedendo il mese verifica che il numero inserito sia compreso tra 1 e 12
				do {
					dataInput = input.nextInt();
					if (dataInput > 12 || dataInput <= 0) {
						System.out.println("Numero non valido, inserire un numero compreso tra 1 e 12:");
					} else {
						flag = true;
					}
				} while (!flag);
				//concatena il numero inserito con il resto della data
				data = data + dataInput + "/";
				break;
			case "anno":
				//se si sta chiedendo l'anno1 verifica che il numero sia composto da 4 cifre
				do {
					dataInput = input.nextInt();
				} while (!controlloAnno(dataInput));
				//concatena il numero inserito con il resto della data
				data = data + dataInput + "/";
				break;
			}			
		}
		//ottiene la sottostringa di data (non considera l'ultima /)
		data = data.substring(0, data.length()-1);
	    System.out.println("Data inserita: "+ data); 
	    //memorizza il formato della data
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    Date parsedDate = null;	//memorizza la data parsata
	    try {
	    	//parsa la data secondo il formato prescelto
	        parsedDate = dateFormat.parse(data);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return parsedDate;
	}
	
	//rimuove auto dall'archivio
	private static void rimuoviAuto() {
		Scanner input = new Scanner(System.in);
		boolean flag = false;
		//richiede la targa dell'auto da rimuovere
		System.out.println("Inserisci la targa dell'auto da rimuovere:");
		String targa = input.nextLine().toUpperCase();
		//ciclo che controlla se è presente un oggetto nella lista avente la stessa targa
		for (Auto auto : automobili) {
			//se la targa inserita è la stessa dell'oggetto viene rimosso l'oggetto auto 
			if (targa.equals(auto.getTarga())) {
				flag = true;
				automobili.remove(auto);
				System.out.println("Rimozione auto n° targa '" + targa + "' dall'archivio");
				break;
			}
		}
		if (!flag) {
			System.out.println("Auto non trovata!");
		}		
	}
	
	//stampa l'elenco delle auto (sia usate che nuove) in ordine di numero di targa
	private static void visualizzaAutoOrdinate() {
		System.out.println("Elenco delle auto:");
		//clono la lista automobili 
		ArrayList<Auto> autoOrdinate = (ArrayList<Auto>) automobili.clone();
		//ordina le auto in base alla targa (utilizzando il metodo compareTo presente nella classe Auto)
		Collections.sort(autoOrdinate);
		//ciclo per stampare le informazioni delle auto presenti nell'archivio
		for (Auto auto : autoOrdinate) {
			auto.visualizzaAuto();
		}
	}
	
	//stampa l'elenco delle auto usate 
	private static void visualizzaAutoUsate() {
		boolean flag = false;	//serve per verificare se sono presenti auto usate nella lista
		System.out.println("Elenco delle auto usate:");
		//ciclo per stampare le informazioni delle auto usate presenti nell'archivio
		for (Auto auto : automobili) {
			//stampa le info degli oggetti della classe AutoUsata
			if (auto instanceof AutoUsata) {
				flag = true;
				auto.visualizzaAuto();
			}
		}
		//se non ci sono auto usate stampa il messaggio
		if (!flag) {
			System.out.println("Non ci sono auto usate nell'archivio");
		}
	}
	
	//stampa l'elenco delle auto nuove 
	private static void visualizzaAutoNuove() {
		boolean flag = false;	//serve per verificare se sono presenti auto usate nella lista
		System.out.println("Elenco delle auto nuove:");
		//ciclo per stampare le informazioni delle auto nuove presenti nell'archivio
		for (Auto auto : automobili) {
			//stampa le info degli oggetti della classe AutoNuova
			if (auto instanceof AutoNuova) {
				flag = true;
				auto.visualizzaAuto();
			}
		}
		//se non ci sono auto nuove stampa il messaggio
		if (!flag) {
			System.out.println("Non ci sono auto nuove nell'archivio");
		}
	}

	//modifica la data di prevista consegna di un'auto nuova
	private static void modificaDataConsegna() {
		String targa = null;	//memorizza la targa immessa dall'utente
		Date data;	//memorizza la nuova data immessa dall'utente
		boolean flag = false;	
		Scanner input = new Scanner(System.in);
			
		System.out.println("Inserisci la targa dell'auto:");
		targa = input.nextLine().toUpperCase();
		for (Auto auto : automobili) {
			if (auto instanceof AutoNuova) {
				//se la targa corrisponde alla targa dell'oggetto viene modifica la data di consegna in quella inserita dall'utente
				if (targa.equals(auto.getTarga())) {
					flag = true;
					System.out.println("Inserisci nuova data di prevista consegna [dd-MM-yyyy]: ");
					data = ottieniData();
					((AutoNuova) auto).setConsegna(data);
					System.out.println("Data di consegna modificata");
					break;
				}
			}
		}
		//se non c'è corrispondenza stampa un messaggio
		if (!flag) {
			System.out.println("Auto non trovata!");
		}		
	}
	
	//ricerca le auto acquistate da un certo cliente 
	//sulla base del suo nome o porzione di esso
	private static void ricercaAutoNuove() {
		String nome = null;	//memorizza il nome inserito dall'utente
		Scanner input = new Scanner(System.in);
		boolean flag = false;	//serve per verificare se è presente il cliente 
		//richiede il nome del cliente da cercare 
		System.out.println("Inserisci il nome del cliente:");
		nome = input.nextLine().toLowerCase();
		System.out.println("Elenco auto del cliente:");
		//ciclo per stampare le informazioni delle auto del cliente inserito
		for (Auto auto : automobili) {
			if (auto instanceof AutoNuova) {
				
				//se il nome o parte di esso è come il nome dell'oggetto stampa le informazioni 
				if (((AutoNuova) auto).getNome().toLowerCase().contains(nome) || nome.contains(((AutoNuova) auto).getNome().toLowerCase())) {
					flag = true;
					auto.visualizzaAuto();
				}
			}
		}
		//se il cliente non è presente stampa il messaggio
		if (!flag) {
			System.out.println("Cliente non trovato!");
		}		
	}
	
	//ricerca le auto usate immatricolate dopo un certo periodo
	//e con al più un certo numero di chilometri
	private static void ricercaAutoUsate() {
		int anno;	//memorizza l'anno di immatricolazione immesso
		int chilometri;	//memorizza il numero di chilometri immesso
		boolean flag = false;
		Scanner input = new Scanner(System.in);
		//richiede le info necessarie (anno e chilometri)
		System.out.println("Ricerca di auto usate \n- immatricolate dopo un certo anno \n- con al più un certo numeri di chilometri\n");
		System.out.println("Inserisci l'anno di immatricolazione [yyyy]:");
		anno = input.nextInt();
		System.out.println("Inserisci i chilometri:");
		chilometri = input.nextInt();
		System.out.println("Elenco auto usate: ");
		//ciclo che stampa le auto che soddisfano i requisiti richiesti
		for (Auto auto : automobili) {
			if (auto instanceof AutoUsata) {
				//se l'oggetto ha anno di immatricolazione superiore 
				//e stessi o minor chilometri stampa le informazioni
				if (((AutoUsata) auto).getAnno() > anno && ((AutoUsata) auto).getChilometri() <= chilometri) {
					flag = true;
					auto.visualizzaAuto();
				}
			}
		}
		//se non sono presenti auto stampa il messaggio
		if (!flag) {
			System.out.println("Nessuna auto trovata!");
		}		
	}

	//ricerca la prossima auto nuova da consegnare data la data di oggi
	private static void ricercaConsegna() {
		Date dataOdierna;	//memorizza la data di oggi
		Date data = null;	//memorizza la data dell'auto presa in considerazione
		Date closest = null;	//memorizza la data dell'auto più vicina alla data odierna
		int i = 0; 	//memorizza l'indice della prossima auto da consegnare
		boolean flag = false;
		Scanner input = new Scanner(System.in);
		
		//chiede la data di oggi
		System.out.println("Inserisci la data di oggi [dd/MM/yyyy]");
		dataOdierna = ottieniData();	
		for (Auto auto : automobili) {
			if (auto instanceof AutoNuova) {			
				data = ((AutoNuova) auto).getConsegna();
				//se non ha ancora salvato la data più vicina alla data odierna... (lo fa una volta sola)
				if(closest == null) {
					//se la data dell'auto considerata è superiore rispetto a quella odierna oppure è uguale, 
					//memorizza tale data come la più vicina (e il relativo indice)
					if (data.after(dataOdierna) || data.equals(dataOdierna)) {
						flag = true;
						closest = data;
						i = automobili.indexOf(auto);					
					}
				//se ha già salvato una data più vicina...
				}else {
					//se la data dell'auto considerata è uguale a quella odierna oppure è superiore rispetto a quella odierna e minore rispetto a quella più finora più vicina, 
					//memorizza tale data come la più vicina (e il relativo indice)
					if (data.equals(dataOdierna) || (data.after(dataOdierna) && data.before(closest))) {
						closest = data;	
						i = automobili.indexOf(auto);
					}
				}			
			}
		}
		//se è stata trovata l'auto prossima alla consegna stampa le info di tale auto
		if(flag) {
			System.out.println("Prossima auto da consegnare: ");
			if (automobili.get(i) instanceof AutoNuova) {	
				automobili.get(i).visualizzaAuto();
			}
		}else {
			System.out.println("Non ci sono auto da consegnare!");
		}		
	}

	//presenta le altre opzioni disponibili (salva, carica, esci)
	private static void altreOpzioni() {
		Scanner input = new Scanner(System.in);
		//interfaccia testuale
		System.out.println("\n \nSeleziona una delle operazioni possibili:");
		System.out.println("[S]alva");
		System.out.println("[C]arica");
		System.out.println("[E]sci");
		char scelta = input.nextLine().toUpperCase().charAt(0); 
		
		switch (scelta) {
		case 'S':
			salvaElenco();	//salva la lista di auto
			break;
		case 'C':
			caricaElenco();	//carica la lista di auto
			break;
		case 'E':
			esci();	//termina l'esecuzione del programma
			break;
		default:
			//nel caso in cui non vengano soddisfatti le condizioni precedenti, genera un messaggio
			System.out.println("Scelta non consentita");
		}	
	}
	
	//salva l'elenco delle auto su file binario
	private static void salvaElenco() {
		try {
			//apre in scrittura il file e crea l'oggetto che lo rappresenta
			FileOutputStream out = new FileOutputStream("filelenco.ser");
			//scrive oggetto nel file
			ObjectOutputStream s = new ObjectOutputStream(out);
			s.writeObject(automobili);
			out.close();
			s.close();
			System.out.println("Salvataggio completato!");
		} catch (IOException e) {
			System.out.println("Salvataggio non riusito! " + e);
		}
	}
	
	//carica l'elenco delle auto da un file precedentemente salvato
	private static void caricaElenco() {
		try {
			//apre in lettura il file e crea l'oggetto che lo rappresenta
			FileInputStream in = new FileInputStream("filelenco.ser");
			//legge oggetto dal file
			ObjectInputStream s = new ObjectInputStream(in);
			automobili = (ArrayList<Auto>) s.readObject();	//salva la lista di auto

			System.out.println("Caricamento completato!");
			in.close();
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("File non trovato!" + e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e1) {
			System.out.println(e1);
		}
	}
	
	//termina l'esecuzione del programma
	private static void esci() {
		Scanner input = new Scanner(System.in);
		char scelta;	//memorizza la scelta dell'utente	
		//ciclo che controlla se l'utente vuole salvare prima di uscire
		do {
			System.out.println("\nVuoi salvare prima di uscire [S/N]?");
			scelta = input.nextLine().toUpperCase().charAt(0);
		} while (scelta != 'S' && scelta != 'N');
			
		if (scelta == 'N') {
			System.out.println("Uscita...");			
			System.exit(0); //termina il programma
		} else {
			salvaElenco(); //salva la lista di auto
			System.out.println("Uscita...");
			System.exit(0); //termina il programma
		}
	}
}

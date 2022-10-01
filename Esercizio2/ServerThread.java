package Esercizio2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {
	//inizializziamo socket, bufferedreader e printwriter
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	//Lista contenente i nomi utenti
	private static ArrayList<String> NomiUtenti = new ArrayList<>();
	
	public ServerThread(Socket s) throws IOException{
		socket = s;
		//creazione stream di input con clientSocket
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//creazione stream di output con clientSocket
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		start();
	}
	
	public void run(){
		try{
			//legge il nome Utente mandato dal client
			String str = in.readLine();
			System.out.println(" ");
			System.out.println("Ricevuto: " + str);
			
			String[] parts = str.split(" ");			
			String nomeUtente = parts[1]; //nome utente
			
			while(NomiUtenti.contains(nomeUtente)) {
				//se il nome utente è già presente nella lista, chiede un nuovo nome utente
				out.println("NOME UTENTE NON DISPONIBILE");
				nomeUtente = in.readLine();
			}
			
			//se il nome utente non è presente, lo aggiunge alla lista
			NomiUtenti.add(nomeUtente);
			
			//crea lista contenente i nomi degli utenti connessi
			String listaUtenti = " ";
			for (String nome: NomiUtenti){
				listaUtenti = listaUtenti + nome + " ";
			}		
			//invia la lista al client
			out.println("CIAO " + listaUtenti);
			
			
			//inizia la chat
			String str1;
			while(true){//!socket.isClosed()
				str1 = in.readLine();
				System.out.println("Ricevuto: " + str1);
				
				if(str1.contains("STOP")) {
					//se riceve STOP disconnette l'utente
					String[] parts1 = str1.split(" ");
					nomeUtente =  parts1[0]; //nome utente di chi si disconnette	
					//elimina il nome utente
					NomiUtenti.remove(nomeUtente);
					
					break;
				}
				if(str1!= null) {
					//se riceve un messaggio
					String[] parts1 = str1.split(" ");
					String messaggio = "";
					nomeUtente =  parts1[0]; //nome utente di chi invia il messaggio		
					//ottiene il messaggio
					for(int i = 1, n = parts1.length ; i < n ; i++) { 
					    messaggio = messaggio + parts1[i] + " ";
					}
					//invia il messaggi a tutti gli utenti connessi
					sendToAll(messaggio, nomeUtente);
				}
					
			}
				
		} catch(IOException e) {}
		try {
			socket.close();
		} catch (IOException e) {}
	}
	
	//metodo per l'invio dei messaggi a tutti i client
	private void sendToAll(String messaggio, String nomeUtente) {
		for (ServerThread canale: Server.canali) {
				canale.out.println("> "+ nomeUtente + ": " + messaggio);			
		}
	}
	
}

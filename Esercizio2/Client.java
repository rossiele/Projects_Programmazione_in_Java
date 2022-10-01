package Esercizio2;

import java.io.*;
import java.net.*;


public class Client {
	
	public static void main(String[] args) throws IOException{
		//inzializza socket, bufferedReader, inputUser e Pintwriter
		Socket socket = null;
		BufferedReader in = null, inputUser = null;
		PrintWriter out = null;
		
		//ottiene l'indirizzo IP locale (null)
		InetAddress addr = InetAddress.getByName(null);
		
		try {
			//invoca costruttore della socket specificando indirizzo IP del server e porta 
			socket = new Socket(addr, Server.PORT);
			System.out.println("Client: started");

			//crea uno stream di input da socket
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//crea uno stream di output da socket
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			
			//crea stream di input da tastiera
			inputUser = new BufferedReader(new InputStreamReader(System.in));
			
			//chiede il nome utente
			System.out.println("Scegli il nome utente");  
			String nomeUtente = inputUser.readLine();
			
			//invia al server il nome utente
			out.println("UTENTE " + nomeUtente);
			
			
			String serverOutput = in.readLine();
			
			//verifica se il nome utente è disponibile o no
			while(serverOutput.contains("NOME UTENTE NON DISPONIBILE"))
			{
				System.out.println("Il nome utente non è disponibile, scegline un altro");
				
				nomeUtente = inputUser.readLine();
				out.println(nomeUtente);			
				serverOutput = in.readLine();
				
			}
			//invio della lista			
			String[] parts = serverOutput.split(" ");
			String lista = "";
			//ottengo la lista
			for(int i = 1, n = parts.length ; i < n ; i++) { 
				lista = lista + parts[i] + " ";
			}
			
			System.out.println("Ecco la lista degli utenti online: " +  lista);
			System.out.println(" ");
			System.out.println("Ora puoi iniziare a chattare con gli altri utenti");
			System.out.println("-------------------------------------------------");
			System.out.println(" ");
			System.out.println("Scrivi il messaggio da inviare");
			System.out.println(" ");
			
			//ora che l'utente è registrato può iniziare a chattare
			String str = null;
			while(true)
			{
				if(in.ready()) {
					//se un altro client ha inviato un messaggio
					System.out.println(in.readLine());
				}
				if(inputUser.ready()) {
					//se l'utente ha inserito un messaggio
					str = inputUser.readLine();
					
					if(str.equals("STOP")){
						//il client vuole disconnettersi
						out.println(nomeUtente + " " + str);
						break;
					}
					if(!str.isBlank()) {
						//invia al server il messaggio
						out.println(nomeUtente + " " + str);
						//il server invia il messaggio a tutti gli utenti					
					}
				}
				
			}
			
		System.out.println("Client: closing...");
		} catch(UnknownHostException e) {
			System.out.println("Indirizzo non trovato");
			e.printStackTrace();
		} catch(IOException e) {
			System.out.println("Non riesco ad ottenere I/O per la connessione");
			e.printStackTrace();}
		
		out.close();
		in.close();
		inputUser.close();
		socket.close();
		
	}
}

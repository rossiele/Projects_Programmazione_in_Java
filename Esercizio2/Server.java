package Esercizio2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
	static final int PORT = 8080;
	
	//lista contenente i threads
	public static ArrayList<ServerThread> canali = new ArrayList<>();
	
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server: started");
		
		try {
			
			while(true){
				Socket clientSocket = serverSocket.accept();
				
				try{
					ServerThread canale = new ServerThread(clientSocket);
					canali.add(canale);	
				} catch(IOException e) {
					clientSocket.close();
				}
			}
		} catch(IOException e) {}
		
		System.out.println("Closing...");	
		serverSocket.close();
	}
}

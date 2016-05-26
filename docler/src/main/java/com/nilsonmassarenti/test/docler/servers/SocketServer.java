package com.nilsonmassarenti.test.docler.servers;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import com.nilsonmassarenti.test.docler.controller.ServerController;
import com.nilsonmassarenti.test.docler.utils.ManagerRequest;
import com.nilsonmassarenti.test.docler.utils.ReadProperties;

/**
 * This Class SocketServer is responsible to create and accept Socket connections 
 * @author Nilson Massarenti
 * @version 0.1
 */
public class SocketServer {
	private Properties prop = ReadProperties.getProperties();
	private ServerController serverController;
	
	/**
	 * @return the serverController
	 */
	public ServerController getServerController() {
		return serverController;
	}

	/**
	 * @param serverController the serverController to set
	 */
	public void setServerController(ServerController serverController) {
		this.serverController = serverController;
	}
	
	/**
	 * This method start a socket server connection and 
	 * manage the requests, creating to each request one thread  
	 */
	public void startServer(){
		ServerSocket server;
		try {
			server = new ServerSocket(Integer.parseInt(prop.getProperty("prop.server.socket.port")));
			System.out.println(prop.getProperty("prop.ping.server.socket.connect.success") + " " + prop.getProperty("prop.server.socket.port"));

			while (true) {
				// accept connection
				Socket client = server.accept();
				System.out.println(prop.getProperty("prop.ping.server.connected.success") + " " + client.getInetAddress().getHostAddress());

				// create a thread to each request
				ManagerRequest managerRequest = new ManagerRequest(client, this);
				new Thread(managerRequest).start();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param messageString String - Message to Socket Client
	 * @param client PrintStream  - Data of client
	 */
	public void sendMessageToClient(String message, PrintStream client) {
		client.println(message);
	}
}

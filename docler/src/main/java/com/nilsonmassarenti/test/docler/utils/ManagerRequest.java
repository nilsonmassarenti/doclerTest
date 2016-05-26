package com.nilsonmassarenti.test.docler.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import com.nilsonmassarenti.test.docler.controller.PingController;
import com.nilsonmassarenti.test.docler.model.PingUser;
import com.nilsonmassarenti.test.docler.servers.SocketServer;

/**
 * This Class ManagerRequest is responsible 
 * to manage the requests from sockets 
 * @author Nilson Massarenti
 * @version 0.1
 */
public class ManagerRequest implements Runnable {

	private SocketServer server;
	private Socket socketClient;
	
	private PingController pingController = new PingController();
	private Properties prop = ReadProperties.getProperties();
	
	/**
	 * Super Class to create the necessary data to class
	 * @param socketClient 
	 * @param server SocketServer - Information of server
	 * @param client PrintStream - Send the information to client
	 */
	public ManagerRequest(Socket socketClient, SocketServer server) {
		this.socketClient = socketClient;
		this.server = server;
	}
	
	/**
	 * this method is responsible to run a thread and 
	 * generate the commands to request the necessary informations 
	 */
	public void run() {
		pingController.setServerController(server.getServerController());
		Boolean executeCommand = false;
		String msgStart = "";
		String msgEnd = "";
		String msgHostProcessing = "";
		try {
			PrintStream client = new PrintStream(socketClient.getOutputStream());
			Scanner s = new Scanner(socketClient.getInputStream());
			String command[] = s.nextLine().split(" ");
			
			if (command.length == 2) {
				msgStart = prop.getProperty("prop.ping.server.startprocess").replace("&command", command[0]).replace("&host", command[1]);
				msgEnd = prop.getProperty("prop.ping.server.endprocess").replace("&command", command[0]).replace("&host", command[1]);
				msgHostProcessing = prop.getProperty("prop.ping.server.hostprocessing").replace("&command", command[0]).replace("&host", command[1]);
				pingController.setHost(command[1]);
				if (command[0].toUpperCase().equals("PING")) {
					pingController.setCommand(command[0].toUpperCase() + "_ICMP");
					executeCommand = true;
				} else if (command[0].toUpperCase().equals("TRACERT")) {
					pingController.setCommand(command[0].toUpperCase());
					executeCommand = true;
				} 
			} else {
				
			}
			
			if (executeCommand) {
				if (command.length == 2) {
					PingUser pingUser = new PingUser(command[1], command[0], socketClient.getInetAddress().getHostAddress(), new Date());
					if (!server.getServerController().searchConnection(pingUser)) {
						server.sendMessageToClient(msgStart, client);
						server.sendMessageToClient(pingController.processRequest(), client);	
					} else {
						server.sendMessageToClient(msgHostProcessing, client);
					}
				}
				
			}
			server.sendMessageToClient(msgEnd, client);
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(prop.getProperty("prop.ping.server.closeconnection") + " " + socketClient.getInetAddress().getHostAddress());
	}

}

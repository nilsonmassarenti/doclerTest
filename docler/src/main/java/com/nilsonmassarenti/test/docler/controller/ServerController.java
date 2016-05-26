/**
 * 
 */
package com.nilsonmassarenti.test.docler.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nilsonmassarenti.test.docler.model.PingError;
import com.nilsonmassarenti.test.docler.model.PingTCPResponse;
import com.nilsonmassarenti.test.docler.model.PingUser;
import com.nilsonmassarenti.test.docler.servers.HTTPServer;
import com.nilsonmassarenti.test.docler.servers.SocketServer;

/**
 * This Class ServerController is responsible to manager the servers
 * @author Nilson Massarenti
 * @version 0.1
 *
 */
public class ServerController {
	
	private List<PingUser> pingUsers = new ArrayList<PingUser>();
	private PingError pingError = new PingError();
	
	/**
	 * @return the pingError
	 */
	public PingError getPingError() {
		return pingError;
	}

	/**
	 * This method is responsible to manage the servers
	 */
	public void startConnections(){
		HTTPServer httpServer = new HTTPServer();
		httpServer.setServerController(this);
		httpServer.server();
		SocketServer socketServer = new SocketServer();
		socketServer.setServerController(this);
		socketServer.startServer();
	}
	
	/**
	 * This method is responsible to save the requests
	 * @param pingUser
	 */
	public void addUserConnection(PingUser pingUser){
		pingUsers.add(pingUser);
	}
	
	/**
	 * This method is responsible to erase the requests
	 * @param pingUser
	 */
	public void delUserConnection(PingUser pingUser){
		pingUsers.remove(pingUser);
	}
	
	/**
	 * This method is responsible to search open requests
	 * @param pingUserSearch
	 */
	public Boolean searchConnection(PingUser pingUserSearch){
		Boolean findhost = false;
		Iterator<PingUser> it = pingUsers.iterator();
		while (it.hasNext() && !findhost) {
			PingUser pingUser = (PingUser) it.next();
			if (pingUser.getHostRequest().equals(pingUserSearch.getHostRequest()) && pingUser.getCommand().equals(pingUserSearch.getCommand())) {
				findhost = true;
			} 
		}
		if (!findhost) {
			addUserConnection(pingUserSearch);
		}
		return findhost;
	}
	
	/**
	 * This method save error in PingError
	 * @param command
	 * @param result
	 * @param pingTCPResponse
	 */
	public void saveError(String command, String result, PingTCPResponse pingTCPResponse){
		if (command.equals("icmp")) {
			pingError.setIcmps(result);
		} else if (command.equals("tracert")) {
			pingError.setTracerts(result);
		} else if (command.equals("tcp")) {
			pingError.setPingTCPResponses(pingTCPResponse);
		}
	}

}

package com.nilsonmassarenti.test.docler.model;

import com.nilsonmassarenti.test.docler.controller.ServerController;

/**
 * This Abstract Class PingExecution is responsible to 
 * provide the factory implementation to differents operational systems.
 * 
 * @author Nilson Massarenti
 * @version 0.1
 */
public abstract class PingExecution {
	private String host;
	private String response;
	private ServerController serverController;
	
	
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	
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
	public abstract Boolean pingICMPExecution();
	public abstract Boolean pingTCPExecution(Integer delay);
	public abstract Boolean tracerouteExecution();
	public abstract Boolean execution(String command, Integer delay, Integer repeat);
	
}

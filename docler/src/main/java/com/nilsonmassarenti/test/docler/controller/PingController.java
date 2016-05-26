package com.nilsonmassarenti.test.docler.controller;

import java.util.Properties;

import com.nilsonmassarenti.test.docler.execution.PingLinuxExecution;
import com.nilsonmassarenti.test.docler.execution.PingOSExecution;
import com.nilsonmassarenti.test.docler.execution.PingWinExecution;
import com.nilsonmassarenti.test.docler.model.PingExecution;
import com.nilsonmassarenti.test.docler.utils.ReadProperties;

/**
 * This class Ping Controller is responsible to manager the requests and
 * generate the response to requester
 * 
 * @author Nilson Massarenti
 * @version 0.1
 *
 */
public class PingController {
	private String host;
	private String command;
	private Properties prop = ReadProperties.getProperties();
	private Integer delay;
	private ServerController serverController;

	/**
	 * 
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 
	 * @param command
	 *            the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @param delay
	 *            the delay to set
	 */
	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	/**
	 * @return the serverController
	 */
	public ServerController getServerController() {
		return serverController;
	}

	/**
	 * @param serverController
	 *            the serverController to set
	 */
	public void setServerController(ServerController serverController) {
		this.serverController = serverController;
	}

	/**
	 * This method is responsible to process all request of PING, PING_TCP and
	 * TRACERT
	 * 
	 * @return response String - Return to show an information to requester
	 */
	public String processRequest() {
		String response = "";
		PingExecution pingExecution = null;
		/*
		 * Checking the Operational System and configure the ping execution to
		 * selected system.
		 */
		if (System.getProperty("os.name").contains("Mac")) {
			pingExecution = new PingOSExecution();
		} else if (System.getProperty("os.name").contains("Linux")) {
			pingExecution = new PingLinuxExecution();
		} else if (System.getProperty("os.name").contains("Win")) {
			pingExecution = new PingWinExecution();
		}
		/*
		 * Checking if the ping execution is different of NULL because exist a
		 * possibility the operational system unsupported
		 */
		if (pingExecution != null) {
			pingExecution.setHost(this.host);
			/*
			 * Checking if the right command
			 */
			if (command.toUpperCase().equals("PING_ICMP")) {
				if (pingExecution.pingICMPExecution()) {
					response = pingExecution.getResponse();
				} else {
					response = prop.getProperty("prop.ping.general.error");
				}
			} else if (command.toUpperCase().equals("PING_TCP")) {
				if (this.delay == null) {
					setDelay(Integer.parseInt(prop.getProperty("prop.ping.tracert.delay")));
				}
				if (pingExecution.pingTCPExecution(this.delay)) {
					response = pingExecution.getResponse();
				} else {
					response = prop.getProperty("prop.ping.general.error");
				}
			} else if (command.toUpperCase().equals("TRACERT")) {
				if (pingExecution.tracerouteExecution()) {
					response = pingExecution.getResponse();
				} else {
					response = prop.getProperty("prop.ping.general.error");
				}
			} else {
				response = prop.getProperty("prop.ping.command.error");
			}
		} else {
			response = prop.getProperty("prop.ping.general.error");
		}
		return response;
	}

}

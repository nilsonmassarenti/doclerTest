package com.nilsonmassarenti.test.docler.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.nilsonmassarenti.test.docler.controller.PingController;
import com.nilsonmassarenti.test.docler.servers.HTTPServer;
import com.sun.net.httpserver.HttpExchange;

/**
 * This Class ManagerRequestHttp is responsible to manage the requests from http
 * 
 * @author Nilson Massarenti
 * @version 0.1
 */
public class ManagerRequestHttp implements Runnable {
	private HttpExchange httpExchange;
	private PingController pingController = new PingController();
	private HTTPServer httpServer;

	/**
	 * Super Class to create the necessary data to class
	 * 
	 * @param httpExchange
	 */
	public ManagerRequestHttp(HttpExchange httpExchange, HTTPServer httpServer) {
		this.httpExchange = httpExchange;
		this.httpServer = httpServer;
	}

	/**
	 * this method is responsible to run a thread and generate the commands to
	 * request the necessary informations
	 */
	public void run() {
		pingController.setServerController(httpServer.getServerController());
		Properties prop = ReadProperties.getProperties();
		StringBuilder response = new StringBuilder();
		System.out.println(httpExchange.getRequestURI().getQuery());
		if (httpExchange.getRequestURI().getQuery() != null) {
			Map<String, String> parms = httpServer.queryToMap(httpExchange.getRequestURI().getQuery());
			if (parms.get("host") != null && parms.get("command") != null) {
				pingController.setCommand("PING_TCP");
				pingController.setHost("host");
				if (parms.get("delay") != null) {
					pingController.setDelay(Integer.parseInt(parms.get("delay")));
				}

				response.append(pingController.processRequest());

			} else {
				response.append(prop.getProperty("prop.server.http.command.request.error"));
			}
		} else {
			response.append(prop.getProperty("prop.server.http.command.request.error"));
		}
		try {
			httpServer.httpResponse(httpExchange, response.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

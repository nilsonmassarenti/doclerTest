package com.nilsonmassarenti.test.docler.servers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.nilsonmassarenti.test.docler.controller.ServerController;
import com.nilsonmassarenti.test.docler.model.PingTCPResponse;
import com.nilsonmassarenti.test.docler.utils.ManagerRequestHttp;
import com.nilsonmassarenti.test.docler.utils.ReadProperties;
import com.nilsonmassarenti.test.docler.utils.Report;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * This Class HTTPServer is responsible to create and accept http connections 
 * @author Nilson Massarenti
 * @version 0.1
 */
public class HTTPServer {
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
	 * This method start a http server connection and 
	 * manage the requests, creating to each request one thread  
	 */
	public void server() {
		final HTTPServer httpServer = this;
		try {
			// creating a server with the port in ping.properties
			HttpServer server = HttpServer
					.create(new InetSocketAddress(Integer.parseInt(prop.getProperty("prop.server.http.port"))), 0);
			System.out.println(prop.getProperty("prop.ping.server.http.connect.success") + " " + prop.getProperty("prop.server.http.port"));
			// creating context of report
			server.createContext(prop.getProperty("prop.server.http.report"), new HttpHandler() {
				public void handle(HttpExchange httpExchange) throws IOException {
					String icmp = "";
					PingTCPResponse pingTCPResponse = null;
					String tracert = "";
					String response = "";
					if (serverController.getPingError().getIcmps().size() >0) {
						icmp = serverController.getPingError().getIcmps().get(serverController.getPingError().getIcmps().size()-1);
					}
					if (serverController.getPingError().getPingTCPResponses().size() >0) {
						pingTCPResponse = serverController.getPingError().getPingTCPResponses().get(serverController.getPingError().getPingTCPResponses().size()-1);
					}
					if (serverController.getPingError().getTracerts().size() >0) {
						tracert = serverController.getPingError().getTracerts().get(serverController.getPingError().getTracerts().size()-1);
					}
					if (!icmp.equals("") || pingTCPResponse != null || !tracert.equals("")) {
						Report report = new Report();
						report.setIcmpResult(icmp);
						report.setPingTCPResponse(pingTCPResponse);
						report.setTracertResult(tracert);
						response = report.generateReport();
					} else {
						response = "No errors with ping";
					}
					httpResponse(httpExchange, response.toString());
				}
			});
			// creating context of send commands via browser
			server.createContext(prop.getProperty("prop.server.http.command"), new HttpHandler() {
				
				// creating context of report
				public void handle(HttpExchange httpExchange) throws IOException {
					ManagerRequestHttp managerRequestHttp = new ManagerRequestHttp(httpExchange, httpServer);
					new Thread(managerRequestHttp).start();
				}
			});
			server.start();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * This method is responsible to return a message to requester
	 * @param httpExchange
	 * @param response String
	 * @throws IOException
	 */
	public void httpResponse(HttpExchange httpExchange, String response) throws IOException {
		httpExchange.sendResponseHeaders(200, response.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	/**
	 * This method is responsible to separate the parameters
	 * @param query String with all parameters
	 * @return result Map<String, String> with the parameters
	 */
	public Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<String, String>();
		for (String param : query.split("&")) {
			String pair[] = param.split("=");
			if (pair.length > 1) {
				result.put(pair[0], pair[1]);
			} else {
				result.put(pair[0], "");
			}
		}
		return result;
	}
}

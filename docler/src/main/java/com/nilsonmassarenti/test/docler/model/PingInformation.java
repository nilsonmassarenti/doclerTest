package com.nilsonmassarenti.test.docler.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This Class is a Model of Ping Information
 * @author nilsonmassarenti
 * @version 0.1
 */
public class PingInformation {
	private String hostname;
	private String hostIp;
	private Date startDate;
	private List<PingResponse> listPingResponses = new ArrayList<PingResponse>();
	private PingTCPResponse pingHttpResponse = new PingTCPResponse();
	
	/**
	 * This method save the ping informations
	 * @param hostname String
	 * @param hostIp String
	 * @param startDate Date
	 */
	public void savePingInformation(String hostname, String hostIp, Date startDate) {
		this.hostname = hostname;
		this.hostIp = hostIp;
		this.startDate = startDate;
	}
	
	/**
	 * This method return the response of pings
	 * @return listPingResponses List<PingResponse>
	 */
	public List<PingResponse> getListPingResponses() {
		return listPingResponses;
	}

	/**
	 * This method return the hostname of ping
	 * @return hostname String
	 */
	public String getHostname() {
		return hostname;
	}
	
	/**
	 * This method return the host IP of ping
	 * @return hostIp String
	 */
	public String getHostIp() {
		return hostIp;
	}

	/**
	 * This method return the start date of ping
	 * @return startDate Date
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * This method save the ping response
	 * @param pingResponse PingResponse
	 */
	public void savePingResponse(PingResponse pingResponse){
		this.listPingResponses.add(pingResponse);
	}

	/**
	 * this method return the data to http response.
	 * @return pingHttpResponse
	 */
	public PingTCPResponse getPingHttpResponse() {
		return pingHttpResponse;
	}

	/**
	 * This method save the pingHttpResponse
	 * @param pingHttpResponse
	 */
	public void setPingHttpResponse(PingTCPResponse pingHttpResponse) {
		this.pingHttpResponse = pingHttpResponse;
	}
}

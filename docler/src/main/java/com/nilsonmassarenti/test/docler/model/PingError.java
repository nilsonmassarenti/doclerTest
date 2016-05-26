package com.nilsonmassarenti.test.docler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class Ping Error is responsible to save all errors
 * @author Nilson Massarenti
 * @version 0.1
 */
public class PingError {
	private List<String> icmps = new ArrayList<String>();
	private List<PingTCPResponse> pingTCPResponses = new ArrayList<PingTCPResponse>();
	private List<String> tracerts = new ArrayList<String>();
	/**
	 * @return the icmps
	 */
	public List<String> getIcmps() {
		return icmps;
	}
	/**
	 * @param icmp the icmps to set
	 */
	public void setIcmps(String icmp) {
		this.icmps.add(icmp);
	}
	/**
	 * @return the pingTCPResponses
	 */
	public List<PingTCPResponse> getPingTCPResponses() {
		return pingTCPResponses;
	}
	/**
	 * @param pingTCPResponse the pingTCPResponses to set
	 */
	public void setPingTCPResponses(PingTCPResponse pingTCPResponse) {
		this.pingTCPResponses.add(pingTCPResponse);
	}
	/**
	 * @return the tracerts
	 */
	public List<String> getTracerts() {
		return tracerts;
	}
	/**
	 * @param tracerts the tracerts to set
	 */
	public void setTracerts(String tracert) {
		this.tracerts.add(tracert);
	}
	
	
}

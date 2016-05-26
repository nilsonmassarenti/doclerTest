/**
 * 
 */
package com.nilsonmassarenti.test.docler.utils;

import com.nilsonmassarenti.test.docler.model.PingTCPResponse;

/**
 * This Class Report is responsible to generate the report
 * @author nilsonmassarenti
 * @version 0.1
 */
public class Report {
	private String icmpResult;
	private PingTCPResponse pingTCPResponse;
	private String tracertResult;

	/**
	 * @param icmpResult the icmpResult to set
	 */
	public void setIcmpResult(String icmpResult) {
		this.icmpResult = icmpResult;
	}

	/**
	 * @param pingTCPResponse the pingTCPResponse to set
	 */
	public void setPingTCPResponse(PingTCPResponse pingTCPResponse) {
		this.pingTCPResponse = pingTCPResponse;
	}

	/**
	 * @param tracertResult the tracertResult to set
	 */
	public void setTracertResult(String tracertResult) {
		this.tracertResult = tracertResult;
	}

	/**
	 * This method is responsible to generate a String to report
	 * @return JSON
	 */
	public String generateReport(){
		String json = "{\n";
		if (!icmpResult.equals("")) {
			json = json + "\"ping_icmp_result\": "
					+ "\""+icmpResult+"\"";
		}
		if (pingTCPResponse != null) {
			if (json.length()>4) {
				json = json + ",\"" + "\"ping_tcp_result\": { "
						+ "\"url\":"
						+ "\""+pingTCPResponse.getUrl()+"\",\n"
					+ "\"response_time\":"
						+ "\""+pingTCPResponse.getAvgTimeResponse()+"\",\n"
					+ "\"host_status\":"
						+ "\""+pingTCPResponse.getHostStatus()+"\"}";
			} else {
				json = json + "\"ping_tcp_result\": { "
						+ "\"url\":"
						+ "\""+pingTCPResponse.getUrl()+"\",\n"
					+ "\"response_time\":"
						+ "\""+pingTCPResponse.getAvgTimeResponse()+"\",\n"
					+ "\"host_status\":"
						+ "\""+pingTCPResponse.getHostStatus()+"\"}";
			}
		}
		if (!tracertResult.equals("")) {
			if (json.length()>4) {
				json = json + ",\"" + "\"tracert_result\": "
						+ "\""+tracertResult+"\"\n";
			} else {
				json = json + "\"tracert_result\": "
								+ "\""+tracertResult+"\"\n";
			}
		}
		json = json + "}";
		return json;
	}
}

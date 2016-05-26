package com.nilsonmassarenti.test.docler.model;

/**
 * This Class is a Model of Ping HTTP Response
 * @author nilsonmassarenti
 * @version 0.1
 */
public class PingTCPResponse {
	private String url;
	private Double average;
	private String hostStatus;
	private Integer packetsTransmitted;
	private Integer packetsReceived;
	private Double percentagePacketLoss;
	private Double minTimeResponse;
	private Double avgTimeResponse;
	private Double maxTimeResponse;
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the average
	 */
	public Double getAverage() {
		return average;
	}
	/**
	 * @param average the average to set
	 */
	public void setAverage(Double average) {
		this.average = average;
	}
	/**
	 * @return the hostStatus
	 */
	public String getHostStatus() {
		return hostStatus;
	}
	/**
	 * @param hostStatus the hostStatus to set
	 */
	public void setHostStatus(String hostStatus) {
		this.hostStatus = hostStatus;
	}
	/**
	 * @return the packetsTransmitted
	 */
	public Integer getPacketsTransmitted() {
		return packetsTransmitted;
	}
	/**
	 * @param packetsTransmitted the packetsTransmitted to set
	 */
	public void setPacketsTransmitted(Integer packetsTransmitted) {
		this.packetsTransmitted = packetsTransmitted;
	}
	/**
	 * @return the packetsReceived
	 */
	public Integer getPacketsReceived() {
		return packetsReceived;
	}
	/**
	 * @param packetsReceived the packetsReceived to set
	 */
	public void setPacketsReceived(Integer packetsReceived) {
		this.packetsReceived = packetsReceived;
	}
	/**
	 * @return the percentagePacketLoss
	 */
	public Double getPercentagePacketLoss() {
		return percentagePacketLoss;
	}
	/**
	 * @param percentagePacketLoss the percentagePacketLoss to set
	 */
	public void setPercentagePacketLoss(Double percentagePacketLoss) {
		this.percentagePacketLoss = percentagePacketLoss;
	}
	/**
	 * @return the minTimeResponse
	 */
	public Double getMinTimeResponse() {
		return minTimeResponse;
	}
	/**
	 * @param minTimeResponse the minTimeResponse to set
	 */
	public void setMinTimeResponse(Double minTimeResponse) {
		this.minTimeResponse = minTimeResponse;
	}
	/**
	 * @return the avgTimeResponse
	 */
	public Double getAvgTimeResponse() {
		return avgTimeResponse;
	}
	/**
	 * @param avgTimeResponse the avgTimeResponse to set
	 */
	public void setAvgTimeResponse(Double avgTimeResponse) {
		this.avgTimeResponse = avgTimeResponse;
	}
	/**
	 * @return the maxTimeResponse
	 */
	public Double getMaxTimeResponse() {
		return maxTimeResponse;
	}
	/**
	 * @param maxTimeResponse the maxTimeResponse to set
	 */
	public void setMaxTimeResponse(Double maxTimeResponse) {
		this.maxTimeResponse = maxTimeResponse;
	}
	
}

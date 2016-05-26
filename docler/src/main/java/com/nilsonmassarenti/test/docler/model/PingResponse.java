package com.nilsonmassarenti.test.docler.model;

/**
 * This Class is a Model of Ping Response
 * @author nilsonmassarenti
 * @version 0.1
 */
public class PingResponse {
	
	private Integer icmpSeq;
	private Integer ttl;
	private Double responseTime;
	private Boolean timeout;
	
	/**
	 * This method save the response of ping
	 * @param icmpSeq Integer
	 * @param ttl Integer
	 * @param responseTime Double
	 * @param timeout  Boolean
	 */
	public PingResponse(Integer icmpSeq, Integer ttl, Double responseTime,
			Boolean timeout) {
		super();
		this.icmpSeq = icmpSeq;
		this.ttl = ttl;
		this.responseTime = responseTime;
		this.timeout = timeout;
	}
	
	/**
	 * This method return the sequence of ICMP
	 * @return icmpSeq Integer
	 */
	public Integer getIcmpSeq() {
		return icmpSeq;
	}
	
	/**
	 * This method return the TTL
	 * @return ttl Integer
	 */
	public Integer getTtl() {
		return ttl;
	}
	
	/**
	 * This method return the response time of ping
	 * @return responseTime Double
	 */
	public Double getResponseTime() {
		return responseTime;
	}
	
	/**
	 * This method return if ping had a timeout
	 * @return timeout Boolean
	 */
	public Boolean getTimeout() {
		return timeout;
	}	
}

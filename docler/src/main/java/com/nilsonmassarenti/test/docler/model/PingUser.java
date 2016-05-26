package com.nilsonmassarenti.test.docler.model;

import java.util.Date;

/**
 * 
 * @author nilsonmassarenti
 *
 */
public class PingUser {
	private String hostRequest;
	private String command;
	private String host;
	private Date date;

	/**
	 * @param hostRequest
	 * @param command
	 * @param host
	 * @param date
	 */
	public PingUser(String hostRequest, String command, String host, Date date) {
		super();
		this.hostRequest = hostRequest;
		this.command = command;
		this.host = host;
		this.date = date;
	}

	/**
	 * @return the hostRequest
	 */
	public String getHostRequest() {
		return hostRequest;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

}

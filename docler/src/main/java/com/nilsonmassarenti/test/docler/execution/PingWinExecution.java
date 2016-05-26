package com.nilsonmassarenti.test.docler.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import com.nilsonmassarenti.test.docler.model.PingExecution;
import com.nilsonmassarenti.test.docler.model.PingInformation;
import com.nilsonmassarenti.test.docler.model.PingResponse;
import com.nilsonmassarenti.test.docler.utils.ManagerFile;
import com.nilsonmassarenti.test.docler.utils.ReadProperties;

/**
 * This Class PingWinExecution is a implementation of Abstract Class
 * PingExecution and responsible to solution of Ping to Windows
 * 
 * @author Nilson Massarenti
 * @version 0.1
 */
public class PingWinExecution extends PingExecution {
	private Properties prop = ReadProperties.getProperties();
	private PingInformation pingInformation = new PingInformation();
	private ManagerFile managerFile = new ManagerFile();
	/**
	 * This method is responsible to configure the PING ICMP
	 * 
	 * @return the result of execution()
	 */
	@Override
	public Boolean pingICMPExecution() {
		String command = prop.getProperty("prop.ping.icmp.win") + " " + prop.getProperty("prop.ping.icmp.amount") + " "
				+ getHost();
		return execution(command, Integer.parseInt(prop.getProperty("prop.ping.icmp.delay")),
				Integer.parseInt(prop.getProperty("prop.ping.icmp.repeat")));
	}

	/**
	 * This method is responsible to configure the PING TCP
	 * 
	 * @param delay
	 *            - Delay to execute the ping
	 * @return execution() Boolean - The result of execution()
	 */
	@Override
	public Boolean pingTCPExecution(Integer delay) {
		String command = prop.getProperty("prop.ping.icmp.linux") + " " + prop.getProperty("prop.ping.icmp.amount")
				+ " " + getHost();
		return execution(command, delay, Integer.parseInt(prop.getProperty("prop.ping.icmp.repeat")));
	}

	/**
	 * This method is responsible to execute the TRACERT
	 * 
	 * @param delay
	 *            - Delay to execute the ping
	 * @return checkReturn Boolean - The result of TRACERT
	 */
	@Override
	public Boolean tracerouteExecution() {
		/*
		 * Variable declarations
		 */
		String command = prop.getProperty("prop.ping.tracert.win") + " " + getHost();
		Integer repeat = Integer.parseInt(prop.getProperty("prop.ping.tracert.repeat"));
		Integer delay = Integer.parseInt(prop.getProperty("prop.ping.tracert.delay"));
		Integer timeout;
		Boolean checkReturn = true;
		Boolean someError = false;
		try {
			String resultReturn = "";
			Boolean checkRepeat = true;
			managerFile.setHostName(getHost());
			/*
			 * Checking if the file was created
			 */
			if (managerFile.createFile(prop.getProperty("prop.ping.tracert.alias"))) {
				/*
				 * This part of code is responsible to execute the command and
				 * check possible errors
				 */
				while (checkRepeat) {
					someError = false;
					timeout = 3;
					resultReturn = resultReturn + "\n\n";
					Process p = Runtime.getRuntime().exec(command);
					BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

					String result = "";
					while (((result = inputStream.readLine()) != null) && (timeout > 0)) {
						resultReturn = resultReturn + result + "\n";
						managerFile.saveInformationFile(prop.getProperty("prop.ping.tracert.alias"), result);
						if (result.contains("Request timed out.")) {
							--timeout;
							someError = true;
						} else {
							timeout = 3;
						}

					}
					if (--repeat == 0) {
						setResponse(resultReturn);
						checkRepeat = false;
					}
					if (someError) {
						getServerController().saveError("TRACERT", getResponse(), null);
					}
					if (timeout == 0) {
						setResponse(resultReturn);
					}
					try {
						Thread.sleep(delay * 1000);
					} catch (InterruptedException e) {
						checkReturn = false;
						e.printStackTrace();
					}
				}
			} else {
				// generate log of system
				checkReturn = false;
			}
		} catch (IOException e) {
			checkReturn = false;
			e.printStackTrace();
		}
		return checkReturn;
	}

	/**
	 * This method is responsible to execute the ping
	 * 
	 * @param command
	 *            String - the command to be execute
	 * @param delay
	 *            Integer - Delay to execute the ping
	 * @param repeat
	 *            Integer Quantity of times to repeat the command
	 * @return checkReturn Boolean - The result of PING
	 */
	@Override
	public Boolean execution(String command, Integer delay, Integer repeat) {
		Boolean checkReturn = true;
		try {
			/*
			 * Variable declarations
			 */
			String resultReturn = "";
			Integer startPosition, endPosition, icmpSeq, ttl;
			Double responseTime;
			Boolean timeout;
			Boolean checkRepeat = true;
			String hostIp = "";
			managerFile.setHostName(getHost());
			/*
			 * Checking if the file was created
			 */
			if (managerFile.createFile(prop.getProperty("prop.ping.icmp.alias"))) {
				/*
				 * This part of code is responsible to execute the command and
				 * check possible errors
				 */
				while (checkRepeat) {
					resultReturn = resultReturn + "\n\n";
					Process p = Runtime.getRuntime().exec(command);
					BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

					String result = "";
					while ((result = inputStream.readLine()) != null) {
						resultReturn = resultReturn + result + "\n";
						managerFile.saveInformationFile(prop.getProperty("prop.ping.icmp.alias"), result);
						if (this.pingInformation.getHostIp() == null) {
							if (result.substring(0, 8).toUpperCase().equals("PINGING ")) {
								startPosition = result.indexOf("[");
								endPosition = result.indexOf("]");
								if (startPosition != -1) {
									hostIp = result.substring(startPosition + 1, endPosition);
								} else {
									hostIp = this.getHost();
								}
								pingInformation.savePingInformation(this.getHost(), hostIp, new Date());
							} else {
								checkRepeat = false;
								checkReturn = false;
							}
						} else {
							if (result.contains("from " + hostIp)) {
								icmpSeq = 0;
								startPosition = result.indexOf("TTL") + 4;
								endPosition = result.length();
								ttl = Integer.parseInt(result.substring(startPosition, endPosition));
								startPosition = result.indexOf(" time") + 6;
								endPosition = result.indexOf(" TTL") - 2;
								responseTime = Double.parseDouble(result.substring(startPosition, endPosition));
								timeout = false;
								PingResponse pingResponse = new PingResponse(icmpSeq, ttl, responseTime, timeout);
								pingInformation.savePingResponse(pingResponse);
							} else if (result.contains("Request timed out")) {
								// call report
							}
						}
					}
					if (--repeat == 0) {
						setResponse(resultReturn);
						checkRepeat = false;
					}
					try {
						Thread.sleep(delay * 1000);
					} catch (InterruptedException e) {
						checkReturn = false;
						e.printStackTrace();
					}
				}
			} else {
				checkReturn = false;
			}
		} catch (IOException e) {
			checkReturn = false;
			e.printStackTrace();
		}
		return checkReturn;
	}

}

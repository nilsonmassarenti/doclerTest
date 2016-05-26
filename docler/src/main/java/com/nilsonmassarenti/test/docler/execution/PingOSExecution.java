package com.nilsonmassarenti.test.docler.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import com.nilsonmassarenti.test.docler.model.PingExecution;
import com.nilsonmassarenti.test.docler.model.PingTCPResponse;
import com.nilsonmassarenti.test.docler.model.PingInformation;
import com.nilsonmassarenti.test.docler.model.PingResponse;
import com.nilsonmassarenti.test.docler.utils.ManagerFile;
import com.nilsonmassarenti.test.docler.utils.ReadProperties;

/**
 * This Class PingOSExecution is a implementation of Abstract Class
 * PingExecution and responsible to solution of Ping to MAC OS
 * 
 * @author Nilson Massarenti
 * @version 0.1
 */
public class PingOSExecution extends PingExecution {

	private Properties prop = ReadProperties.getProperties();
	private PingInformation pingInformation = new PingInformation();
	private ManagerFile managerFile = new ManagerFile();
	private String pingType;

	/**
	 * This method is responsible to configure the PING ICMP
	 * 
	 * @return the result of execution()
	 */
	@Override
	public Boolean pingICMPExecution() {
		pingType = "ICMP";
		String command = prop.getProperty("prop.ping.icmp.os") + " " + prop.getProperty("prop.ping.icmp.amount") + " "
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
		pingType = "TCP";
		String command = prop.getProperty("prop.ping.icmp.os") + " " + prop.getProperty("prop.ping.icmp.amount") + " "
				+ getHost();
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
		 * variable declarations
		 */
		String command = prop.getProperty("prop.ping.tracert.os") + " " + getHost();
		Integer repeat = Integer.parseInt(prop.getProperty("prop.ping.tracert.repeat"));
		Integer delay = Integer.parseInt(prop.getProperty("prop.ping.tracert.delay"));
		Integer timeout;
		Boolean checkReturn = true;
		Boolean someError = false;
		try {
			String resultReturn = "";
			Boolean checkRepeat = true;
			managerFile.setHostName(getHost());

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
						if (result.contains("* * *")) {
							--timeout;
							someError = true;
						} else if (result.contains("ret=-1")) {
							timeout = 0;
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

	@Override
	public Boolean execution(String command, Integer delay, Integer repeat) {
		Boolean checkReturn = true;
		try {
			/*
			 * variable declarations
			 */
			String resultReturn = "";
			String resultReturnTCP = "";
			Integer startPosition, endPosition, icmpSeq, ttl;
			Double responseTime;
			Boolean timeout;
			Boolean checkRepeat = true;
			String hostIp = "";
			managerFile.setHostName(getHost());
			PingTCPResponse pingHttpResponse = new PingTCPResponse();
			/*
			 * Checking if the file was create
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
							if (result.substring(0, 5).equals("PING ")) {
								startPosition = result.indexOf("(");
								endPosition = result.indexOf(")");
								hostIp = result.substring(startPosition + 1, endPosition);
								pingInformation.savePingInformation(this.getHost(), hostIp, new Date());
							} else {
								checkRepeat = false;
								checkReturn = false;
							}
						} else {
							if (result.contains("from " + hostIp)) {
								startPosition = result.indexOf("icmp_seq") + 9;
								endPosition = result.indexOf("ttl") - 1;
								icmpSeq = Integer.parseInt(result.substring(startPosition, endPosition));
								startPosition = result.indexOf("ttl") + 4;
								endPosition = result.indexOf("time") - 1;
								ttl = Integer.parseInt(result.substring(startPosition, endPosition));
								startPosition = result.indexOf("time") + 5;
								endPosition = result.length() - 2;
								responseTime = Double.parseDouble(result.substring(startPosition, endPosition));
								timeout = false;
								PingResponse pingResponse = new PingResponse(icmpSeq, ttl, responseTime, timeout);
								pingInformation.savePingResponse(pingResponse);
							} else {
								if (result.contains("packets transmitted")) {
									pingHttpResponse.setUrl(getHost());
									startPosition = 0;
									endPosition = result.indexOf("packets transmitted") - 1;
									pingHttpResponse.setPacketsTransmitted(
											Integer.parseInt(result.substring(startPosition, endPosition)));
									startPosition = result.indexOf("packets transmitted") + 21;
									endPosition = result.indexOf("packets received") - 1;
									pingHttpResponse.setPacketsReceived(
											Integer.parseInt(result.substring(startPosition, endPosition)));
									startPosition = result.indexOf("packets received") + 18;
									endPosition = result.indexOf("packet loss") - 2;
									pingHttpResponse.setPercentagePacketLoss(
											Double.parseDouble(result.substring(startPosition, endPosition)));
								} else if (result.contains("min/avg/max")) {
									startPosition = result.indexOf("=") + 2;
									endPosition = result.length() - 3;
									String numberResults[] = result.substring(startPosition, endPosition).split("/");
									pingHttpResponse.setMinTimeResponse(Double.parseDouble(numberResults[0]));
									pingHttpResponse.setAvgTimeResponse(Double.parseDouble(numberResults[1]));
									pingHttpResponse.setMaxTimeResponse(Double.parseDouble(numberResults[2]));
								} else if (result.equals("ping: sendto: No route to host")) {
									getServerController().saveError("ICMP", getResponse(), null);

								}
							}
						}
					}
					if (--repeat == 0) {
						setResponse(resultReturn);
						checkRepeat = false;
					}
					if (this.pingInformation.getPingHttpResponse().getPacketsReceived() != null) {
						if (pingType.equals("TCP")) {
							String hostStatus = "ONLINE";
							if (this.pingInformation.getPingHttpResponse().getPacketsReceived() == 0) {
								hostStatus = "OFFLINE";
							}
							resultReturnTCP = resultReturnTCP + "HOST: " + this.getHost() + "\n" + "Response: "
									+ this.pingInformation.getPingHttpResponse().getAvgTimeResponse() + "\n"
									+ "Status: " + hostStatus + "\n";
							setResponse(resultReturnTCP);
						}
						if (this.pingInformation.getPingHttpResponse().getPacketsReceived()
								- this.pingInformation.getPingHttpResponse().getPacketsTransmitted() != 0) {
							if (this.pingType.equals("ICMP")) {
								getServerController().saveError("ICMP", getResponse(), null);
							} else {
								getServerController().saveError("TCP", null,
										this.pingInformation.getPingHttpResponse());
							}
						}
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

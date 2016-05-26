package com.nilsonmassarenti.test.view;

import java.util.Properties;

import com.nilsonmassarenti.test.docler.controller.SocketClient;
import com.nilsonmassarenti.test.docler.utils.ReadProperties;

/**
 * Hello world!
 *
 */
public class App 
{	
    public static void main( String[] args )
    {
    	Properties prop = ReadProperties.getProperties();
    	if (args.length == 2) {
    		if ((args[0].toUpperCase().equals("PING")|| args[0].toUpperCase().equals("TRACERT"))) {
    			SocketClient socketClient = new SocketClient();
    			String command = args[0] + " " + args[1];
    			socketClient.setCommand(command);
                socketClient.serverConnection();
			} else {
				System.out.println(prop.getProperty("prop.ping.server.args.unsuccess") + " - " + prop.getProperty("prop.ping.server.args.help"));
			}
		} else {
			System.out.println(prop.getProperty("prop.ping.server.args.unsuccess") + " - " + prop.getProperty("prop.ping.server.args.help"));
		}
        
    }
}

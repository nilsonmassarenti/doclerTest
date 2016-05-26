package com.nilsonmassarenti.test.docler.view;

import com.nilsonmassarenti.test.docler.controller.ServerController;

public class App {

	public static void main(String[] args){
		ServerController serverController = new ServerController();
		serverController.startConnections();
		
	}

}

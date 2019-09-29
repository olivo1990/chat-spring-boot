package com.bolsadeideas.springboot.backend.chat.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.bolsadeideas.springboot.backend.chat.models.documents.Mensaje;
import com.bolsadeideas.springboot.backend.chat.models.services.IChatService;

@Controller
public class ChatController {
	
	@Autowired
	private IChatService chatService;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	private String[] colores = {"red", "green", "blue", "black", "magenta", "purlple", "orange"};
	
	@MessageMapping("/mensaje")
	@SendTo("/chat/mensaje")
	public Mensaje recibeMensaje(Mensaje mensaje) {
		
		mensaje.setFecha(new Date().getTime());
		
		if(mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setColor(this.colores[new Random().nextInt(colores.length)]);
			mensaje.setTexto("se ha conectado");
		}else {
			chatService.guardar(mensaje);
		}
		
		return mensaje;
	}
	
	@MessageMapping("/escribiendo")
	@SendTo("/chat/escribiendo")
	public Map<String, Object> estaEscribiendo(String username) {
		
		Map<String, Object> response = new HashMap<>();
		response.put("escribiendo", username.concat(" está escribiendo..."));
		response.put("username", username);
		
		return response;
		//return username.concat(" está escribiendo...");
	}
	
	@MessageMapping("/historial")
	public void historialMensajes(String clienteId) {
		webSocket.convertAndSend("/chat/historial/"+ clienteId, chatService.obtenerUltimos10Mesajes());
	}
	
	
}

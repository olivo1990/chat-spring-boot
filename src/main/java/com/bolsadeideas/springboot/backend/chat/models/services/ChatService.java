package com.bolsadeideas.springboot.backend.chat.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.backend.chat.models.dao.ChatRepository;
import com.bolsadeideas.springboot.backend.chat.models.documents.Mensaje;

@Service
public class ChatService implements IChatService {
	
	@Autowired
	private ChatRepository chatRepositoryDao;

	@Override
	public List<Mensaje> obtenerUltimos10Mesajes() {
		return chatRepositoryDao.findFirst10ByOrderByFechaDesc();
	}

	@Override
	public Mensaje guardar(Mensaje mensaje) {
		return chatRepositoryDao.save(mensaje);
	}

}

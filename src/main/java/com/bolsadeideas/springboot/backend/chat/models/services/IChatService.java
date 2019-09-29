package com.bolsadeideas.springboot.backend.chat.models.services;

import java.util.List;

import com.bolsadeideas.springboot.backend.chat.models.documents.Mensaje;

public interface IChatService {
	public List<Mensaje> obtenerUltimos10Mesajes();
	public Mensaje guardar(Mensaje mensaje);
}

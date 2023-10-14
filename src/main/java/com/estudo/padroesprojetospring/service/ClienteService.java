package com.estudo.padroesprojetospring.service;

import com.estudo.padroesprojetospring.model.Cliente;

public interface ClienteService {
	
	Iterable<Cliente> buscarTodos();
	
	Cliente buscarPorId(Long Id);
	
	void inserir(Cliente cliente);
	
	void atualizar(Long Id, Cliente cliente);
	
	void deletar(Long Id);

}

package com.estudo.padroesprojetospring.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudo.padroesprojetospring.model.Cliente;
import com.estudo.padroesprojetospring.model.ClienteRepository;
import com.estudo.padroesprojetospring.model.Endereco;
import com.estudo.padroesprojetospring.model.EnderecoRepository;
import com.estudo.padroesprojetospring.service.ClienteService;
import com.estudo.padroesprojetospring.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired 
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long Id) {
		Optional<Cliente> cliente = clienteRepository.findById(Id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCep(cliente);
	}

	@Override
	public void atualizar(Long Id, Cliente cliente) {
		Optional<Cliente> clienteBd = clienteRepository.findById(Id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(cliente);
		}
	}

	@Override
	public void deletar(Long Id) {
		clienteRepository.deleteById(Id);
	}
	
	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}

}

package domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.entity.Cliente;
import domain.repository.IClienteRepository;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private IClienteRepository clienteRepository;
    

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}

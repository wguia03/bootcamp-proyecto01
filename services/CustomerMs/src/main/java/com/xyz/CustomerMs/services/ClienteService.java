package com.xyz.CustomerMs.services;

import com.xyz.CustomerMs.models.Cliente;
import com.xyz.CustomerMs.models.ClienteRequest;
import com.xyz.CustomerMs.models.ClienteResponse;
import com.xyz.CustomerMs.models.ClienteUpdate;
import com.xyz.CustomerMs.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;

    public ClienteResponse getClienteById(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        return convertToClienteResponse(cliente);
    }

    public ClienteResponse createCliente(ClienteRequest clienteRequest) {
        Cliente cliente = convertToEntity(clienteRequest);
        return convertToClienteResponse(clienteRepository.save(cliente));
    }

    public List<ClienteResponse> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(this::convertToClienteResponse).toList();
    }

    public void deleteCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    public void updateCliente(Integer id, ClienteUpdate clienteUpdate) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if(cliente == null) {
            return;
        }
        if(clienteUpdate.getNombre() != null && !clienteUpdate.getNombre().isEmpty()) {
            cliente.setNombre(clienteUpdate.getNombre());
        }
        if(clienteUpdate.getApellido() != null && !clienteUpdate.getApellido().isEmpty()) {
            cliente.setApellido(clienteUpdate.getApellido());
        }
        if(clienteUpdate.getDni() != null && !clienteUpdate.getDni().isEmpty()) {
            cliente.setDni(clienteUpdate.getDni());
        }
        if(clienteUpdate.getEmail() != null && !clienteUpdate.getEmail().isEmpty()) {
            cliente.setEmail(clienteUpdate.getEmail());
        }
        System.out.println(cliente);
        clienteRepository.save(cliente);
    }

    private Cliente convertToEntity(ClienteRequest clienteRequest) {
        if(clienteRequest == null) {
            return null;
        }
        return modelMapper.map(clienteRequest, Cliente.class);
    }

    private ClienteResponse convertToClienteResponse(Cliente cliente) {
        if(cliente == null) {
            return null;
        }
        return modelMapper.map(cliente, ClienteResponse.class);
    }
}

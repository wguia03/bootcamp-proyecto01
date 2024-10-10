package com.xyz.CustomerMs.utils;

import com.xyz.CustomerMs.models.Cliente;
import com.xyz.CustomerMs.models.ClienteRequest;
import com.xyz.CustomerMs.models.ClienteResponse;
import com.xyz.CustomerMs.models.ClienteUpdate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteMapper {

    private final ModelMapper modelMapper;

    public Cliente convertToEntity(ClienteRequest clienteRequest) {
        return modelMapper.map(clienteRequest, Cliente.class);
    }

    public ClienteResponse convertToClienteResponse(Cliente cliente) {
        return modelMapper.map(cliente, ClienteResponse.class);
    }

    public List<ClienteResponse> convertToClienteResponse(List<Cliente> clientes) {
        return clientes.stream().map(this::convertToClienteResponse).toList();
    }

    public Cliente updateClienteFromRequest(Cliente cliente, ClienteUpdate clienteUpdate) {
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
        return cliente;
    }
}

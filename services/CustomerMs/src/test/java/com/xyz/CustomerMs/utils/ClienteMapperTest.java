package com.xyz.CustomerMs.utils;

import com.xyz.CustomerMs.models.Cliente;
import com.xyz.CustomerMs.models.ClienteRequest;
import com.xyz.CustomerMs.models.ClienteResponse;
import com.xyz.CustomerMs.models.ClienteUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClienteMapperTest {

    private ClienteMapper clienteMapper;

    @BeforeEach
    void setUp() {
        clienteMapper = new ClienteMapper(new ModelMapper());
    }

    @Test
    void convertToEntity() {
        ClienteRequest clienteRequest = new ClienteRequest(
                "John",
                "Doe",
                "12345678",
                "john.doe@example.com"
        );

        Cliente cliente = clienteMapper.convertToEntity(clienteRequest);
        assertEquals(clienteRequest.getNombre(), cliente.getNombre());
        assertEquals(clienteRequest.getApellido(), cliente.getApellido());
        assertEquals(clienteRequest.getDni(), cliente.getDni());
        assertEquals(clienteRequest.getEmail(), cliente.getEmail());
    }

    @Test
    void convertToClienteResponse() {
        Cliente cliente = new Cliente(
                1,
                "John",
                "Doe",
                "12345678",
                "john.doe@example.com"
        );

        ClienteResponse clienteResponse = clienteMapper.convertToClienteResponse(cliente);
        assertNotNull(clienteResponse.getId());
        assertEquals(cliente.getNombre(), clienteResponse.getNombre());
        assertEquals(cliente.getApellido(), clienteResponse.getApellido());
        assertEquals(cliente.getDni(), clienteResponse.getDni());
        assertEquals(cliente.getEmail(), clienteResponse.getEmail());
    }

    @Test
    void convertToClienteResponseList() {
        List<Cliente> clienteList = new ArrayList<>();
        Cliente cliente = new Cliente(
                1,
                "John",
                "Doe",
                "12345678",
                "john.doe@example.com"
        );
        clienteList.add(cliente);

        List<ClienteResponse> clienteResponseList = clienteMapper.convertToClienteResponse(clienteList);
        assertNotNull(clienteResponseList);
        assertEquals(1, clienteResponseList.size());
        assertEquals(cliente.getNombre(), clienteResponseList.get(0).getNombre());
        assertEquals(cliente.getApellido(), clienteResponseList.get(0).getApellido());
        assertEquals(cliente.getDni(), clienteResponseList.get(0).getDni());
        assertEquals(cliente.getEmail(), clienteResponseList.get(0).getEmail());
    }

    @Test
    void updateClienteFromRequest() {
        Cliente cliente = new Cliente(
                1,
                "John",
                "Doe",
                "12345678",
                "john.doe@example.com"
        );

        ClienteUpdate clienteUpdate = new ClienteUpdate(
                "Jane",
                "Smith",
                "87654321",
                "jane.smith@example.com"
        );

        Cliente updatedCliente = clienteMapper.updateClienteFromRequest(cliente, clienteUpdate);
        assertEquals(clienteUpdate.getNombre(), updatedCliente.getNombre());
        assertEquals(clienteUpdate.getApellido(), updatedCliente.getApellido());
        assertEquals(clienteUpdate.getDni(), updatedCliente.getDni());
        assertEquals(clienteUpdate.getEmail(), updatedCliente.getEmail());
    }
}
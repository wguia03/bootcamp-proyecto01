package com.xyz.CustomerMs.services;

import com.xyz.CustomerMs.exceptions.AssociatedRecordException;
import com.xyz.CustomerMs.exceptions.DuplicateFieldException;
import com.xyz.CustomerMs.exceptions.ResourceNotFoundException;
import com.xyz.CustomerMs.models.Cliente;
import com.xyz.CustomerMs.models.ClienteRequest;
import com.xyz.CustomerMs.models.ClienteResponse;
import com.xyz.CustomerMs.models.ClienteUpdate;
import com.xyz.CustomerMs.repositories.ClienteRepository;
import com.xyz.CustomerMs.repositories.CuentaRepository;
import com.xyz.CustomerMs.utils.ClienteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getClienteByIdWithExistingId() throws ResourceNotFoundException {
        Integer clienteId = 1;
        Cliente cliente = new Cliente(
                clienteId,
                "John",
                "Doe",
                "12345678",
                "john.doe@example.com"
        );
        ClienteResponse clienteResponse = new ClienteResponse(
                clienteId,
                "John",
                "Doe",
                "12345678",
                "john.doe@example.com\""
        );
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteMapper.convertToClienteResponse(cliente)).thenReturn(clienteResponse);

        ClienteResponse result = clienteService.getClienteById(clienteId);

        assertNotNull(result);
        assertEquals(clienteResponse, result);
    }

    @Test
    void getClienteByIdWithNonExistingId() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.getClienteById(1));
    }

    @Test
    void createClienteWithDuplicateDni() {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setDni("12345678");
        when(clienteRepository.findByDni("12345678")).thenReturn(Optional.of(new Cliente()));

        assertThrows(DuplicateFieldException.class, () -> clienteService.createCliente(clienteRequest));
    }

    @Test
    void createClienteWithValidRequest() throws DuplicateFieldException {
        ClienteRequest clienteRequest = new ClienteRequest(
                "John",
                "Doe",
                "12345678",
                "john.doe@example.com"
        );

        Cliente cliente = new Cliente();
        cliente.setNombre("John");
        cliente.setApellido("Doe");
        cliente.setDni("12345678");
        cliente.setEmail("john.doe@example.com");

        Cliente createdCliente = new Cliente(
                1,
                "John",
                "Doe",
                "12345678",
                "john.doe@example.com"
        );

        ClienteResponse clienteResponse = new ClienteResponse(
                1,
                "John",
                "Doe",
                "12345678",
                "john.doe@example.com"
        );

        when(clienteRepository.findByDni(clienteRequest.getDni())).thenReturn(Optional.empty());
        when(clienteMapper.convertToEntity(clienteRequest)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(createdCliente);
        when(clienteMapper.convertToClienteResponse(createdCliente)).thenReturn(clienteResponse);

        ClienteResponse result = clienteService.createCliente(clienteRequest);

        assertNotNull(result);
        assertEquals(clienteResponse, result);
    }

    @Test
    void deleteClienteWithNonExistingId() {
        when(clienteRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clienteService.deleteCliente(1));
    }

    @Test
    void deleteClienteWithAssociatedRecords() {
        when(clienteRepository.existsById(1)).thenReturn(true);
        when(cuentaRepository.existsByClienteId(1)).thenReturn(true);

        assertThrows(AssociatedRecordException.class, () -> clienteService.deleteCliente(1));
    }

    @Test
    void deleteClienteWithValidId() throws ResourceNotFoundException, AssociatedRecordException {
        when(clienteRepository.existsById(1)).thenReturn(true);
        when(cuentaRepository.existsByClienteId(1)).thenReturn(false);

        clienteService.deleteCliente(1);

        verify(clienteRepository, times(1)).deleteById(1);
    }

    @Test
    void updateClienteWithNonExistingId() {
        ClienteUpdate clienteUpdate = new ClienteUpdate();
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.updateCliente(1, clienteUpdate));
    }

    @Test
    void updateClienteWithValidId() throws ResourceNotFoundException {
        Cliente cliente = new Cliente();
        ClienteUpdate clienteUpdate = new ClienteUpdate();
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteMapper.updateClienteFromRequest(cliente, clienteUpdate)).thenReturn(cliente);

        clienteService.updateCliente(1, clienteUpdate);

        verify(clienteRepository, times(1)).save(cliente);
    }
}
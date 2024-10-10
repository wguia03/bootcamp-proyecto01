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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteMapper clienteMapper;
    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;

    public ClienteResponse getClienteById(Integer id) throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        return clienteMapper.convertToClienteResponse(cliente);
    }

    public ClienteResponse createCliente(ClienteRequest clienteRequest) throws DuplicateFieldException {
        Optional<Cliente> clienteOptional = clienteRepository.findByDni(clienteRequest.getDni());
        if(clienteOptional.isPresent()) {
            throw new DuplicateFieldException("Ya existe un cliente con el DNI: " + clienteRequest.getDni());
        }
        Cliente cliente = clienteMapper.convertToEntity(clienteRequest);
        return clienteMapper.convertToClienteResponse(clienteRepository.save(cliente));
    }

    public List<ClienteResponse> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clienteMapper.convertToClienteResponse(clientes);
    }

    public void deleteCliente(Integer id) throws ResourceNotFoundException, AssociatedRecordException {
        if(!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        if(cuentaRepository.existsByClienteId(id)) {
            throw new AssociatedRecordException("No se puede eliminar el registro porque el cliente tiene cuentas activas");
        }
        clienteRepository.deleteById(id);
    }

    public void updateCliente(Integer id, ClienteUpdate clienteUpdate) throws ResourceNotFoundException{
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        cliente = clienteMapper.updateClienteFromRequest(cliente, clienteUpdate);
        clienteRepository.save(cliente);
    }
}
package com.xyz.CustomerMs.controllers;

import com.xyz.CustomerMs.exceptions.AssociatedRecordException;
import com.xyz.CustomerMs.exceptions.DuplicateFieldException;
import com.xyz.CustomerMs.exceptions.ResourceNotFoundException;
import com.xyz.CustomerMs.models.ClienteRequest;
import com.xyz.CustomerMs.models.ClienteResponse;
import com.xyz.CustomerMs.models.ClienteUpdate;
import com.xyz.CustomerMs.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCliente(@RequestBody @Valid ClienteRequest clienteRequest) throws DuplicateFieldException {
        ClienteResponse createdCliente = clienteService.createCliente(clienteRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCliente.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCliente(@PathVariable Integer id, @RequestBody @Valid ClienteUpdate clienteUpdate) throws ResourceNotFoundException{
        clienteService.updateCliente(id, clienteUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) throws ResourceNotFoundException, AssociatedRecordException {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok().build();
    }
}

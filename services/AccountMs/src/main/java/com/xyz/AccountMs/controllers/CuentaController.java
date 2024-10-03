package com.xyz.AccountMs.controllers;

import com.xyz.AccountMs.models.CuentaRequest;
import com.xyz.AccountMs.models.CuentaResponse;
import com.xyz.AccountMs.models.TransaccionRequest;
import com.xyz.AccountMs.models.TransaccionResponse;
import com.xyz.AccountMs.services.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> getCuentas() {
        return ResponseEntity.ok(cuentaService.getCuentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> getCuenta(@PathVariable Integer id) {
        return ResponseEntity.ok(cuentaService.getCuenta(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCuenta(@RequestBody @Valid CuentaRequest cuentaRequest) {
        CuentaResponse createdCuenta = cuentaService.createCuenta(cuentaRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCuenta.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}/depositar")
    public ResponseEntity<TransaccionResponse> realizarDeposito(@PathVariable Integer id, @RequestBody @Valid TransaccionRequest transaccionRequest) {
        return ResponseEntity.ok(cuentaService.realizarDeposito(transaccionRequest, id));
    }

    @PutMapping("/{id}/retirar")
    public ResponseEntity<TransaccionResponse> realizarRetiro(@PathVariable Integer id, @RequestBody @Valid TransaccionRequest transaccionRequest) {
        return ResponseEntity.ok(cuentaService.realizarRetiro(transaccionRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Integer id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }
}

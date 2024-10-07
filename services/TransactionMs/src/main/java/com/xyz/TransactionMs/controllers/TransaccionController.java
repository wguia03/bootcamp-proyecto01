package com.xyz.TransactionMs.controllers;

import com.xyz.TransactionMs.exceptions.DuplicateAccountNumber;
import com.xyz.TransactionMs.models.TransaccionRequest;
import com.xyz.TransactionMs.models.TransaccionResponse;
import com.xyz.TransactionMs.models.TransferenciaRequest;
import com.xyz.TransactionMs.services.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transacciones")
public class TransaccionController {
    private final TransaccionService transaccionService;

    @GetMapping("/historial")
    public ResponseEntity<List<TransaccionResponse>> obtenerTransacciones(){
        return ResponseEntity.ok(transaccionService.obtenerTransacciones());
    }

    @PostMapping("/deposito")
    public ResponseEntity<String> realizarDeposito(@RequestBody @Valid TransaccionRequest transaccionRequest){
        transaccionService.registrarDeposito(transaccionRequest);
        return ResponseEntity.ok("Deposito realizado");
    }

    @PostMapping("/retiro")
    public ResponseEntity<String> realizarRetiro(@RequestBody @Valid TransaccionRequest transaccionRequest){
        transaccionService.registrarRetiro(transaccionRequest);
        return ResponseEntity.ok("Retiro realizado");
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> realizarTransferencia(@RequestBody @Valid TransferenciaRequest transferenciaRequest) throws DuplicateAccountNumber {
        transaccionService.registrarTransferencia(transferenciaRequest);
        return ResponseEntity.ok("Transferencia realizada");
    }
}
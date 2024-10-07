package com.xyz.TransactionMs.services;

import com.xyz.TransactionMs.exceptions.DuplicateAccountNumber;
import com.xyz.TransactionMs.models.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.xyz.TransactionMs.repositories.TransaccionRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final ModelMapper modelMapper;

    public void registrarDeposito(TransaccionRequest transaccionRequest) {
        Transaccion transaccion = modelMapper.map(transaccionRequest, Transaccion.class);
        transaccion.setTipo(TipoTransaccionEnum.DEPOSITO);
        transaccion.setFecha(new Date());
        transaccionRepository.save(transaccion);
    }

    public void registrarRetiro(TransaccionRequest transaccionRequest) {
        Transaccion transaccion = modelMapper.map(transaccionRequest, Transaccion.class);
        transaccion.setTipo(TipoTransaccionEnum.RETIRO);
        transaccion.setFecha(new Date());
        transaccionRepository.save(transaccion);
    }

    public void registrarTransferencia(TransferenciaRequest transferenciaRequest) throws DuplicateAccountNumber {
        if(transferenciaRequest.getCuenta_origen().equals(transferenciaRequest.getCuenta_destino())) {
            throw new DuplicateAccountNumber("La cuenta origen y la cuenta destino no pueden ser iguales");
        }
        Transaccion transaccion = modelMapper.map(transferenciaRequest, Transaccion.class);
        transaccion.setFecha(new Date());
        transaccion.setTipo(TipoTransaccionEnum.TRANSFERENCIA);
        transaccionRepository.save(transaccion);
    }

    public List<TransaccionResponse> obtenerTransacciones() {
        List<Transaccion> transacciones = transaccionRepository.findAll();
        return transacciones.stream()
                .map(transaccion -> modelMapper.map(transaccion, TransaccionResponse.class))
                .toList();
    }
}

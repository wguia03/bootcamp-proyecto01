package com.xyz.TransactionMs.utils;

import com.xyz.TransactionMs.models.Transaccion;
import com.xyz.TransactionMs.models.TransaccionRequest;
import com.xyz.TransactionMs.models.TransaccionResponse;
import com.xyz.TransactionMs.models.TransferenciaRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransaccionMapper {

    private final ModelMapper modelMapper;

    public Transaccion convertToEntity(TransaccionRequest transaccionRequest){
        return modelMapper.map(transaccionRequest, Transaccion.class);
    }

    public Transaccion convertToEntity(TransferenciaRequest transferenciaRequest){
        return modelMapper.map(transferenciaRequest, Transaccion.class);
    }

    public List<TransaccionResponse> convertToTransaccionResponseList(List<Transaccion> transacciones){
        return transacciones.stream()
                .map(transaccion -> modelMapper.map(transaccion, TransaccionResponse.class))
                .toList();
    }
}

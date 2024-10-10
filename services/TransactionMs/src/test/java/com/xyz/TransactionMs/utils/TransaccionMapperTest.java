package com.xyz.TransactionMs.utils;

import com.xyz.TransactionMs.models.*;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransaccionMapperTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private final TransaccionMapper transaccionMapper = new TransaccionMapper(modelMapper);

    @Test
    void convertToEntity_convertsTransaccionRequestToTransaccion() {
        TransaccionRequest request = new TransaccionRequest(
                100.0,
                "1234567890123456"
        );
        Transaccion result = transaccionMapper.convertToEntity(request);

        assertEquals(request.getMonto(), result.getMonto());
        assertEquals(request.getCuenta_origen(), result.getCuenta_origen());
    }

    @Test
    void convertToEntity_convertsTransferenciaRequestToTransaccion() {
        TransferenciaRequest request = new TransferenciaRequest(
                100.0,
                "1234567890123456",
                "1234567890123457"
        );
        Transaccion result = transaccionMapper.convertToEntity(request);

        assertNotNull(result);
        assertEquals(request.getMonto(), result.getMonto());
        assertEquals(request.getCuenta_origen(), result.getCuenta_origen());
        assertEquals(request.getCuenta_destino(), result.getCuenta_destino());
    }

    @Test
    void convertToTransaccionResponseList_convertsListOfTransaccionesToListOfTransaccionResponses() {
        Transaccion transaccion = new Transaccion();
        transaccion.setId("1");
        transaccion.setTipo(TipoTransaccionEnum.DEPOSITO);
        transaccion.setMonto(100.0);
        transaccion.setFecha(new Date());
        transaccion.setCuenta_origen("1234567890123456");

        List<Transaccion> transacciones = List.of(transaccion);

        List<TransaccionResponse> result = transaccionMapper.convertToTransaccionResponseList(transacciones);

        assertNotNull(result);
        assertEquals(transacciones.size(), result.size());
        assertEquals(transaccion.getTipo(), result.get(0).getTipo());
        assertEquals(transaccion.getMonto(), result.get(0).getMonto());
        assertEquals(transaccion.getFecha(), result.get(0).getFecha());
        assertEquals(transaccion.getCuenta_origen(), result.get(0).getCuenta_origen());
        assertEquals(transaccion.getCuenta_destino(), result.get(0).getCuenta_destino());
    }
}
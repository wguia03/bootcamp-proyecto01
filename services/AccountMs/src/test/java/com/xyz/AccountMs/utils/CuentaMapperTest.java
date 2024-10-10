package com.xyz.AccountMs.utils;

import com.xyz.AccountMs.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuentaMapperTest {

    private CuentaMapper cuentaMapper;

    @BeforeEach
    void setUp() {
        cuentaMapper = new CuentaMapper(new ModelMapper());
    }

    @Test
    void convertToEntityWithValidRequest() {
        CuentaRequest cuentaRequest = new CuentaRequest(
                100.0,
                TipoCuentaEnum.AHORROS,
                1
        );

        Cuenta result = cuentaMapper.convertToEntity(cuentaRequest);

        assertEquals(cuentaRequest.getSaldo(), result.getSaldo());
        assertEquals(cuentaRequest.getTipoCuenta(), result.getTipoCuenta());
    }

    @Test
    void convertToCuentaResponseWithValidCuenta() {
        Cuenta cuenta = new Cuenta(
                1,
                "123456",
                100.0,
                TipoCuentaEnum.AHORROS,
                new Cliente(
                        1,
                        "John",
                        "Doe",
                        "12345678",
                        "john.doe@example.com"
                )
        );

        CuentaResponse result = cuentaMapper.convertToCuentaResponse(cuenta);

        assertNotNull(result);
        assertEquals(cuenta.getId(), result.getId());
        assertEquals(cuenta.getNumeroCuenta(), result.getNumeroCuenta());
        assertEquals(cuenta.getSaldo(), result.getSaldo());
        assertEquals(cuenta.getTipoCuenta(), result.getTipoCuenta());
        assertEquals(cuenta.getCliente().getId(), result.getClienteId());
    }

    @Test
    void covertToCuentaResponseListWithValidList() {
        List<Cuenta> cuentas = List.of(new Cuenta(
                1,
                "123456",
                100.0,
                TipoCuentaEnum.AHORROS,
                new Cliente(
                        1,
                        "John",
                        "Doe",
                        "12345678",
                        "john.doe@example.com"
                )
        ));

        List<CuentaResponse> result = cuentaMapper.covertToCuentaResponseList(cuentas);

        assertNotNull(result);
        assertEquals(cuentas.size(), result.size());
        assertEquals(cuentas.get(0).getId(), result.get(0).getId());
        assertEquals(cuentas.get(0).getNumeroCuenta(), result.get(0).getNumeroCuenta());
        assertEquals(cuentas.get(0).getSaldo(), result.get(0).getSaldo());
        assertEquals(cuentas.get(0).getTipoCuenta(), result.get(0).getTipoCuenta());
        assertEquals(cuentas.get(0).getCliente().getId(), result.get(0).getClienteId());
    }
}
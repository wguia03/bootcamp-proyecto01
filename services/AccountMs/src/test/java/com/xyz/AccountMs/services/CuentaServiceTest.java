package com.xyz.AccountMs.services;

import com.xyz.AccountMs.exceptions.ResourceNotFoundException;
import com.xyz.AccountMs.exceptions.TransactionException;
import com.xyz.AccountMs.models.*;
import com.xyz.AccountMs.repositories.ClienteRepository;
import com.xyz.AccountMs.repositories.CuentaRepository;
import com.xyz.AccountMs.utils.CuentaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CuentaMapper cuentaMapper;

    @InjectMocks
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCuenta_createsAndReturnsCuenta() {
        CuentaRequest request = new CuentaRequest();
        request.setClienteId(1);
        Cliente cliente = new Cliente();
        Cuenta cuenta = new Cuenta();
        CuentaResponse response = new CuentaResponse();

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(cuentaMapper.convertToEntity(request)).thenReturn(cuenta);
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);
        when(cuentaMapper.convertToCuentaResponse(cuenta)).thenReturn(response);

        CuentaResponse result = cuentaService.createCuenta(request);

        assertNotNull(result);
        verify(cuentaRepository).save(cuenta);
    }

    @Test
    void getCuenta_returnsCuentaResponse() throws ResourceNotFoundException {
        Cuenta cuenta = new Cuenta();
        CuentaResponse response = new CuentaResponse();

        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        when(cuentaMapper.convertToCuentaResponse(cuenta)).thenReturn(response);

        CuentaResponse result = cuentaService.getCuenta(1);

        assertNotNull(result);
        verify(cuentaRepository).findById(1);
    }

    @Test
    void getCuenta_throwsResourceNotFoundException() {
        when(cuentaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cuentaService.getCuenta(1));
    }

    @Test
    void realizarDeposito_depositsAmount() throws ResourceNotFoundException {
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(100.0);
        TransaccionRequest transaccion = new TransaccionRequest(50.0);
        TransaccionResponse response = new TransaccionResponse("Depósito realizado con éxito", 150.0);

        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        TransaccionResponse result = cuentaService.realizarDeposito(transaccion, 1);

        assertEquals(response.message(), result.message());
        assertEquals(response.saldo(), result.saldo());
        verify(cuentaRepository).save(cuenta);
    }

    @Test
    void realizarRetiro_retiresAmountFromAhorros() throws ResourceNotFoundException, TransactionException {
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(100.0);
        cuenta.setTipoCuenta(TipoCuentaEnum.AHORROS);
        TransaccionRequest transaccion = new TransaccionRequest(50.0);
        TransaccionResponse response = new TransaccionResponse("Retiro realizado con éxito", 50.0);

        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        TransaccionResponse result = cuentaService.realizarRetiro(transaccion, 1);

        assertEquals(response.message(), result.message());
        assertEquals(response.saldo(), result.saldo());
        verify(cuentaRepository).save(cuenta);
    }

    @Test
    void realizarRetiro_throwsTransactionExceptionForAhorros() {
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(100.0);
        cuenta.setTipoCuenta(TipoCuentaEnum.AHORROS);
        TransaccionRequest transaccion = new TransaccionRequest(150.0);

        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));

        assertThrows(TransactionException.class, () -> cuentaService.realizarRetiro(transaccion, 1));
    }

    @Test
    void deleteCuenta_deletesCuenta() throws ResourceNotFoundException {
        when(cuentaRepository.existsById(1)).thenReturn(true);

        cuentaService.deleteCuenta(1);

        verify(cuentaRepository).deleteById(1);
    }

    @Test
    void deleteCuenta_throwsResourceNotFoundException() {
        when(cuentaRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> cuentaService.deleteCuenta(1));
    }
}
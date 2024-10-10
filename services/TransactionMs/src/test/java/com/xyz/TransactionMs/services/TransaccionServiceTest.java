package com.xyz.TransactionMs.services;

import com.xyz.TransactionMs.exceptions.DuplicateAccountNumber;
import com.xyz.TransactionMs.models.*;
import com.xyz.TransactionMs.repositories.TransaccionRepository;
import com.xyz.TransactionMs.utils.TransaccionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class TransaccionServiceTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private TransaccionMapper transaccionMapper;

    @InjectMocks
    private TransaccionService transaccionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarDeposito_savesDepositoTransaction() {
        TransaccionRequest request = new TransaccionRequest();
        Transaccion transaccion = new Transaccion();

        when(transaccionMapper.convertToEntity(request)).thenReturn(transaccion);

        transaccionService.registrarDeposito(request);

        assertEquals(TipoTransaccionEnum.DEPOSITO, transaccion.getTipo());
        assertNotNull(transaccion.getFecha());
        verify(transaccionRepository).save(transaccion);
    }

    @Test
    void registrarRetiro_savesRetiroTransaction() {
        TransaccionRequest request = new TransaccionRequest();
        Transaccion transaccion = new Transaccion();

        when(transaccionMapper.convertToEntity(request)).thenReturn(transaccion);

        transaccionService.registrarRetiro(request);

        assertEquals(TipoTransaccionEnum.RETIRO, transaccion.getTipo());
        assertNotNull(transaccion.getFecha());
        verify(transaccionRepository).save(transaccion);
    }

    @Test
    void registrarTransferencia_savesTransferenciaTransaction() throws DuplicateAccountNumber {
        TransferenciaRequest request = new TransferenciaRequest();
        request.setCuenta_origen("123");
        request.setCuenta_destino("456");
        Transaccion transaccion = new Transaccion();

        when(transaccionMapper.convertToEntity(request)).thenReturn(transaccion);

        transaccionService.registrarTransferencia(request);

        assertEquals(TipoTransaccionEnum.TRANSFERENCIA, transaccion.getTipo());
        assertNotNull(transaccion.getFecha());
        verify(transaccionRepository).save(transaccion);
    }

    @Test
    void registrarTransferencia_throwsExceptionForDuplicateAccountNumber() {
        TransferenciaRequest request = new TransferenciaRequest();
        request.setCuenta_origen("123");
        request.setCuenta_destino("123");

        DuplicateAccountNumber exception = assertThrows(DuplicateAccountNumber.class, () -> {
            transaccionService.registrarTransferencia(request);
        });

        assertEquals("La cuenta origen y la cuenta destino no pueden ser iguales", exception.getMessage());
        verify(transaccionRepository, never()).save(any(Transaccion.class));
    }

    @Test
    void obtenerTransacciones_returnsListOfTransaccionResponses() {
        Transaccion transaccion = new Transaccion();
        TransaccionResponse response = new TransaccionResponse();
        List<Transaccion> transacciones = List.of(transaccion);
        List<TransaccionResponse> expectedResponses = List.of(response);

        when(transaccionRepository.findAll()).thenReturn(transacciones);
        when(transaccionMapper.convertToTransaccionResponseList(transacciones)).thenReturn(expectedResponses);

        List<TransaccionResponse> result = transaccionService.obtenerTransacciones();

        assertNotNull(result);
        assertEquals(expectedResponses.size(), result.size());
        assertEquals(expectedResponses, result);
    }
}
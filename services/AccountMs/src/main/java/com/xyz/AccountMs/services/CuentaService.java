package com.xyz.AccountMs.services;

import com.xyz.AccountMs.exceptions.ResourceNotFoundException;
import com.xyz.AccountMs.exceptions.TransactionException;
import com.xyz.AccountMs.models.*;
import com.xyz.AccountMs.repositories.ClienteRepository;
import com.xyz.AccountMs.repositories.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    public CuentaResponse createCuenta(CuentaRequest cuentaRequest) {
        Cliente cliente = clienteRepository.findById(cuentaRequest.getClienteId()).orElseThrow();
        Cuenta cuenta = modelMapper.map(cuentaRequest, Cuenta.class);
        cuenta.setCliente(cliente);
        cuenta.setNumeroCuenta(generateAccountNumber());
        System.out.println(cuenta);
        cuenta = cuentaRepository.save(cuenta);
        return modelMapper.map(cuenta, CuentaResponse.class);
    }

    public CuentaResponse getCuenta(Integer id) throws ResourceNotFoundException {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));
        return modelMapper.map(cuenta, CuentaResponse.class);
    }

    public List<CuentaResponse> getCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        return cuentas.stream()
                .map(cuenta -> modelMapper.map(cuenta, CuentaResponse.class))
                .toList();
    }

    public TransaccionResponse realizarDeposito(TransaccionRequest transaccion, Integer id) throws ResourceNotFoundException {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));
        cuenta.setSaldo(cuenta.getSaldo() + transaccion.monto());
        cuenta = cuentaRepository.save(cuenta);
        return new TransaccionResponse("Depósito realizado con éxito", cuenta.getSaldo());
    }

    public TransaccionResponse realizarRetiro(TransaccionRequest transaccion, Integer id) throws ResourceNotFoundException, TransactionException{
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));
        var saldoActual = cuenta.getSaldo();
        cuenta.setSaldo(cuenta.getSaldo() - transaccion.monto());
        if(cuenta.getTipoCuenta().toString().equals("AHORROS")){
            if(cuenta.getSaldo() < 0){
                throw new TransactionException("No se puede realizar el retiro. Saldo insuficiente");
            } else {
                cuenta = cuentaRepository.save(cuenta);
                return new TransaccionResponse("Retiro realizado con éxito", cuenta.getSaldo());
            }
        }
        if(cuenta.getTipoCuenta().toString().equals("CORRIENTE")){
            if(cuenta.getSaldo() < -500){
                throw new TransactionException("No se puede realizar el retiro. Saldo insuficiente");
            } else {
                cuenta = cuentaRepository.save(cuenta);
                return new TransaccionResponse("Retiro realizado con éxito", cuenta.getSaldo());
            }
        }
        else {
            return new TransaccionResponse("No se puede realizar el retiro. Tipo de cuenta no válido", cuenta.getSaldo());
        }
    }

    private String generateAccountNumber() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String numericString = uuid.replaceAll("[a-f]", "");
        if (numericString.length() < 16) {
            numericString = String.format("%-16s", numericString).replace(' ', '0'); // Rellenar con ceros
        }
        return numericString.substring(0, 16); // Obtener una cuenta de 16 dígitos numéricos
    }

    public void deleteCuenta(Integer id) throws ResourceNotFoundException {
        if (!cuentaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cuenta no encontrada");
        }
        cuentaRepository.deleteById(id);
    }
}

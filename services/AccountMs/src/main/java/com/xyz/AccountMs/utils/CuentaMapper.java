package com.xyz.AccountMs.utils;

import com.xyz.AccountMs.models.Cuenta;
import com.xyz.AccountMs.models.CuentaRequest;
import com.xyz.AccountMs.models.CuentaResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CuentaMapper {
    private final ModelMapper modelMapper;

    public Cuenta convertToEntity(CuentaRequest cuentaRequest) {
        return modelMapper.map(cuentaRequest, Cuenta.class);
    }

    public CuentaResponse convertToCuentaResponse(Cuenta cuenta) {
        return modelMapper.map(cuenta, CuentaResponse.class);
    }

    public List<CuentaResponse> covertToCuentaResponseList(List<Cuenta> cuentas) {
        return cuentas.stream()
                .map(cuenta -> modelMapper.map(cuenta, CuentaResponse.class))
                .toList();
    }
}

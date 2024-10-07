package com.xyz.TransactionMs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransaccionResponse {
    TipoTransaccionEnum tipo;
    Double monto;
    Date fecha;
    String cuenta_origen;
    String cuenta_destino;
}

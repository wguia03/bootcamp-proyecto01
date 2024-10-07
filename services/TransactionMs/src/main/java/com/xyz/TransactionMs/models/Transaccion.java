package com.xyz.TransactionMs.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "transacciones")
public class Transaccion {

    @Id
    private String id;

    private TipoTransaccionEnum tipo;
    private Double monto;
    private Date fecha;
    private String cuenta_origen;
    private String cuenta_destino;
}

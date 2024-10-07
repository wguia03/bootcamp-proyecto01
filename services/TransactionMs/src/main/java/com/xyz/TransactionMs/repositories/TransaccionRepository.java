package com.xyz.TransactionMs.repositories;

import com.xyz.TransactionMs.models.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransaccionRepository extends MongoRepository<Transaccion, String> {
}

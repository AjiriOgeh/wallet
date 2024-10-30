package com.wallet.application.port.output;

import com.wallet.domain.model.Transaction;

public interface TransactionOutputPort {
    Transaction save(Transaction transaction);
}

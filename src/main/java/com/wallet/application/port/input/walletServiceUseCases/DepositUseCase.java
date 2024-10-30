package com.wallet.application.port.input.walletServiceUseCases;

import com.wallet.domain.model.Deposit;
import com.wallet.domain.model.Transaction;
import com.wallet.infrastructure.adapters.input.rest.dto.request.InitialisePaymentRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;

public interface DepositUseCase {
    InitialisePaymentResponse deposit(InitialisePaymentRequest initialisePaymentRequest);
}

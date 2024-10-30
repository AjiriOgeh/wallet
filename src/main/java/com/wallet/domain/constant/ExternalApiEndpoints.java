package com.wallet.domain.constant;

public class ExternalApiEndpoints {
    public static final String PAYSTACK_INITIALIZE_PAY_URL = "https://api.paystack.co/transaction/initialize";
    public static final String PAYSTACK_VERIFY_URL = "https://api.paystack.co/transaction/verify/";
    public static final String AUTH_TOKEN_URL = "http://localhost:8085/realms/wallet/protocol/openid-connect/token";
    public static final String PREMBLY_PHONE_NUMBER_VERIFICATION_URL = "https://api.prembly.com/identitypass/verification/phone_number";
    public static final String PREMBLY_BANK_VERIFICATION_NUMBER_VERIFICATION_URL = "https://api.prembly.com/identitypass/verification/bvn";
}

package com.wallet.application.port.output;

import com.wallet.domain.model.AuthToken;
import com.wallet.domain.model.AuthUser;
import jakarta.ws.rs.core.Response;

public interface AuthOutputPort {

    Response createAuthUser(AuthUser authUser);

    void sendVerificationEmail(String keycloakId);

    AuthToken login(String email, String password);

    void editAuthUser(String keycloakId, AuthUser authUser);

    void deleteAuthUser(String email);


}

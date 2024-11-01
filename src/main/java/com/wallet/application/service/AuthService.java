package com.wallet.application.service;

import com.wallet.application.port.input.authServiceUseCases.CreateUserRepresentationUseCase;
import com.wallet.application.port.input.authServiceUseCases.DeleteUserRepresentationUseCase;
import com.wallet.application.port.input.authServiceUseCases.EditUserRepresentationUseCase;
import com.wallet.application.port.input.authServiceUseCases.LoginUserRepresentationUseCase;
import com.wallet.domain.exception.AuthUserCreationException;
import com.wallet.domain.exception.ExternalApiException;
import com.wallet.domain.exception.InvalidUserCredentialsException;
import com.wallet.domain.model.AuthToken;
import com.wallet.domain.model.AuthUser;
import com.wallet.domain.model.User;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static com.wallet.domain.constant.ExternalApiEndpoints.AUTH_TOKEN_URL;
import static org.keycloak.representations.idm.CredentialRepresentation.PASSWORD;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Slf4j
@RequiredArgsConstructor
public class AuthService implements CreateUserRepresentationUseCase, EditUserRepresentationUseCase,
        DeleteUserRepresentationUseCase, LoginUserRepresentationUseCase {

    private final Keycloak keycloak;
    private final WebClient webClient;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Value("${app.keycloak.admin.clientId}")
    private String clientId;

    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;


    @Override
    public Response createUserRepresentation(AuthUser authUser) {
        UserRepresentation userRepresentation = createAuthUser(authUser);
        setAuthUserCredentials(authUser, userRepresentation);
        Response response = getUsersResource().create(userRepresentation);
        if (response.getStatus() != 201) {
            throw new AuthUserCreationException("User cannot be created");
        }
        setAuthUserRole(authUser, response);
        return response;
    }

    @NotNull
    private static UserRepresentation createAuthUser(AuthUser authUser) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(authUser.getFirstname());
        userRepresentation.setLastName(authUser.getLastname());
        userRepresentation.setUsername(authUser.getEmail());
        userRepresentation.setEmail(authUser.getEmail());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    private void setAuthUserRole(AuthUser authUser, Response response) {
        String role = authUser.getRole() != null ? String.valueOf(authUser.getRole()) : "USER";
        RoleRepresentation roleRepresentation = assignRoleToUserRepresentation(role.toUpperCase());
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        UserResource userResource = getUsersResource().get(userId);
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    private RoleRepresentation assignRoleToUserRepresentation(String role) {
        RolesResource roles = keycloak.realm(realm).roles();
        return roles.get(role).toRepresentation();
    }

    private static void setAuthUserCredentials(AuthUser authUser, UserRepresentation userRepresentation) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(authUser.getPassword());
        credentialRepresentation.setType(PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
    }

    private void sendVerificationEmail(String keycloakId) {
        getUsersResource().get(keycloakId).sendVerifyEmail();
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    @Override
    public AuthToken login(String email, String password) {
        return webClient.post()
                .uri(AUTH_TOKEN_URL)
                .contentType(APPLICATION_FORM_URLENCODED)
                .bodyValue("client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&grant_type=password" +
                        "&username=" + email +
                        "&password=" + password)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InvalidUserCredentialsException("Invalid user credentials"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalApiException("Keycloak is unable to authenticate user due to server error"))
                )
                .bodyToMono(AuthToken.class).block();
    }

    @Override
    public void editUserRepresentation(String email, User user) {
        UserRepresentation userRepresentation = getUsersResource()
                .search(email, true).getFirst();
        if (user.getFirstname() != null) userRepresentation.setFirstName(user.getFirstname());
        if (user.getLastname() != null) userRepresentation.setLastName(user.getLastname());
        UserResource userResource = getUsersResource().get(userRepresentation.getId());
        userResource.update(userRepresentation);
    }

    @Override
    public void deleteUserRepresentation(String email) {
        UserRepresentation userRepresentation = getUsersResource()
                .search(email, true).getFirst();
        getUsersResource().delete(userRepresentation.getId());
    }

}


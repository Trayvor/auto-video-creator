package org.creator.autovideocreator.configuration;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.creator.autovideocreator.exception.YoutubeAuthorizationException;
import org.creator.autovideocreator.exception.TokenRefreshingException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

public class YoutubeAuth {
    public static final String UPLOAD_VIDEO_SCOPE =
            "https://www.googleapis.com/auth/youtube.upload";
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new GsonFactory();

    private static final String REDIRECT_URI = "http://localhost:8080/api/project/oauth2callback";

    public static String getAuthorizationUrl(List<String> scopes, Long projectId) {
        GoogleClientSecrets clientSecrets = getClientSecrets();

        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            throw new YoutubeAuthorizationException(
                    "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential "
                            + "into src/main/resources/client_secrets.json");
        }

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).build();

        GoogleAuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
        authorizationUrl.setRedirectUri(REDIRECT_URI);
        authorizationUrl.setAccessType("offline");
        authorizationUrl.set("prompt", "consent");
        authorizationUrl.setState(projectId.toString());
        return authorizationUrl.build();
    }

    public static Credential exchangeCodeForTokens(String code) throws IOException {
        GoogleClientSecrets clientSecrets = getClientSecrets();
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                HTTP_TRANSPORT, JSON_FACTORY, "https://oauth2.googleapis.com/token",
                clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret(),
                code, REDIRECT_URI)
                .execute();

        // Створюємо Credential без збереження токенів
        return new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret())
                .build()
                .setAccessToken(tokenResponse.getAccessToken())
                .setRefreshToken(tokenResponse.getRefreshToken());
    }

    private static GoogleClientSecrets getClientSecrets() {
        Reader clientSecretReader = new InputStreamReader(Objects.requireNonNull(YoutubeAuth.class.getResourceAsStream("/client_secrets.json")));
        try {
            return GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);
        } catch (IOException e) {
            throw new YoutubeAuthorizationException("Can`t read client secrets from resources folder " + "/client_secrets.json", e);
        }
    }

    public static Credential getCredentialByRefreshToken(String refreshToken) {
        Credential credential = new GoogleCredential.Builder()
                .setClientSecrets(getClientSecrets())
                .setJsonFactory(JSON_FACTORY)
                .setTransport(HTTP_TRANSPORT)
                .build()
                .setRefreshToken(refreshToken);
        try {
            credential.refreshToken();
        } catch (IOException e) {
            throw new TokenRefreshingException("Can`t refresh token in credentials " + credential
                    , e);
        }
        return credential;
    }
}
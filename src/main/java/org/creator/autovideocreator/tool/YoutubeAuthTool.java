package org.creator.autovideocreator.tool;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import org.creator.autovideocreator.exception.TokenRefreshingException;
import org.creator.autovideocreator.exception.YoutubeAuthorizationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class YoutubeAuthTool {
    public static final String UPLOAD_VIDEO_SCOPE =
            "https://www.googleapis.com/auth/youtube.upload";
    public static final String READONLY_VIDEO_SCOPE =
            "https://www.googleapis.com/auth/youtube.readonly";
    public static final String FORCE_SSL_SCOPE =
            "https://www.googleapis.com/auth/youtube.force-ssl";
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new GsonFactory();
    public static final String APP_NAME = "auto-video-creator";

    private static final String REDIRECT_URI = "http://localhost:8080/api/callback/oauth2callback";

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
        Reader clientSecretReader =
                new InputStreamReader(Objects.requireNonNull(YoutubeAuthTool.class.getResourceAsStream("/client_secrets.json")));
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

    public static YouTube createYouTubeInstance(String refreshToken) {
        return new YouTube.Builder(YoutubeAuthTool.HTTP_TRANSPORT, YoutubeAuthTool.JSON_FACTORY,
                getCredentialByRefreshToken(refreshToken)).setApplicationName(APP_NAME).build();
    }
}

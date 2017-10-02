package org.github.snambi.bbs.microsoft;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.web.util.UriComponentsBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AuthHelper {

    private static Logger logger = LoggerFactory.getLogger(AuthHelper.class);

    private static final String authority = "https://login.microsoftonline.com";
    private static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";

    private static String[] scopes = {
            "openid",
            "offline_access",
            "profile",
            "User.Read",
            //"User.Read.All",
            "User.ReadBasic.All",
            "Mail.Read",
            "Calendars.Read",
            "Contacts.Read"
    };

    private static String appId = null;
    private static String appPassword = null;
    private static String redirectUrl = null;

    private static String getAppId() throws IOException {
        if (appId == null) {
            loadConfig();
        }

        return appId;
    }
    private static String getAppPassword() throws IOException {
        if (appPassword == null) {
            loadConfig();
        }

        return appPassword;
    }

    private static String getRedirectUrl() throws IOException {
        if (redirectUrl == null) {
            loadConfig();
        }

        return redirectUrl;
    }

    private static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope: scopes) {
            sb.append(scope + " ");
        }
        return sb.toString().trim();
    }

    private static void loadConfig() throws IOException {
        String authConfigFile = "auth.properties";

        Map<String,String> envs = System.getenv();

        String appIdEnv = envs.get("MS.APPID");
        if( appIdEnv != null && !appIdEnv.trim().isEmpty()){
            appId = appIdEnv;
        }

        String appPasswordEnv = envs.get("MS.APP_PASSWORD");
        if( appPasswordEnv != null && !appPasswordEnv.trim().isEmpty()){
            appPassword = appPasswordEnv;
        }

        String redirectUrlEnv = envs.get("MS.REDIRECT_URL");
        if(redirectUrlEnv != null && !redirectUrlEnv.trim().isEmpty()){
            redirectUrl = redirectUrlEnv;
        }

        InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);

        if (authConfigStream != null) {
            Properties authProps = new Properties();

            try {

                authProps.load(authConfigStream);

                if( appId == null || appId.trim().isEmpty()) {
                    appId = authProps.getProperty("MS.APPID");
                }


                if( appPassword == null || appPassword.trim().isEmpty()) {
                    appPassword = authProps.getProperty("MS.APP_PASSWORD");
                }

                if( redirectUrl == null || redirectUrl.trim().isEmpty()){
                    redirectUrl = authProps.getProperty("MS.REDIRECT_URL");
                }

            } finally {
                authConfigStream.close();
            }
        }

        MissingRequiredPropertiesException exception = new MissingRequiredPropertiesException();
        if( appId == null || appId.trim().equals("") ){
            exception.getMissingRequiredProperties().add("appId for microsoft office 365 is missing.");
        }
        if( appPassword == null || appPassword.trim().equals("")){
            exception.getMissingRequiredProperties().add("appPassword for microsoft office 365 is missing.");
        }
        if( redirectUrl == null || redirectUrl.trim().equals("")){
            exception.getMissingRequiredProperties().add("RedirectURL for microsoft office 365 is missing.");
        }

        if( exception.getMissingRequiredProperties().size() > 0 ){
            throw exception;
        }
    }

    public static String getLoginUrl(UUID state, UUID nonce) throws IOException {

        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(authorizeUrl);

        urlBuilder.queryParam("client_id", getAppId());
        urlBuilder.queryParam("redirect_uri", getRedirectUrl());
        urlBuilder.queryParam("response_type", "code id_token");
        urlBuilder.queryParam("scope", getScopes());
        urlBuilder.queryParam("state", state);
        urlBuilder.queryParam("nonce", nonce);
        urlBuilder.queryParam("response_mode", "form_post");

        return urlBuilder.toUriString();
    }

    public static TokenResponse getTokenFromAuthCode(String authCode, String tenantId) {

        // Create a logging interceptor to log request and responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();


        // Create and configure the Retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(authority)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        // Generate the token service
        ITokenService tokenService = retrofit.create(ITokenService.class);

        try {
            return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(),
                    "authorization_code", authCode, getRedirectUrl()).execute().body();
        } catch (IOException e) {
            TokenResponse error = new TokenResponse();
            error.setError("IOException");
            error.setErrorDescription(e.getMessage());
            return error;
        }
    }


    public static TokenResponse ensureTokens(TokenResponse tokens, String tenantId) {

        // Are tokens still valid?
        Calendar now = Calendar.getInstance();
        if (now.getTime().before(tokens.getExpirationTime())) {
            // Still valid, return them as-is
            return tokens;
        }
        else {
            // Expired, refresh the tokens
            // Create a logging interceptor to log request and responses
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor).build();

            // Create and configure the Retrofit object
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(authority)
                    .client(client)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            // Generate the token service
            ITokenService tokenService = retrofit.create(ITokenService.class);

            try {
                return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(),
                        "refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
            } catch (IOException e) {
                TokenResponse error = new TokenResponse();
                error.setError("IOException");
                error.setErrorDescription(e.getMessage());
                return error;
            }
        }
    }
}
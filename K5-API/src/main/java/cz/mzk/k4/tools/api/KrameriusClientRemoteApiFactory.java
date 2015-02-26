package cz.mzk.k4.tools.api;

import retrofit.*;

/**
 * Created by holmanj on 8.2.15.
 */
public class KrameriusClientRemoteApiFactory {

    private static final String PROTOCOL = "http://";
    private static final String KRAMERIUS_CLIENT_API = "/search/api/v5.0";

    // TODO: asi lepší mít 1 interface a custom adapter (converter?), který parsuje json, string i xml podle typu dat v response

//    public static ClientRemoteApiJSON getJsonService(String krameriusHostUrl, String login, String password) {
//
//        AuthorizationInterceptor authInterceptor = new AuthorizationInterceptor(login, password);
//
//        RestAdapter.Builder builder = new RestAdapter.Builder()
//                .setRequestInterceptor(authInterceptor)
//                .setEndpoint("http://" + krameriusHostUrl + "/search/api/v5.0");
//        ClientRemoteApiJSON clientApi = builder.build().create(ClientRemoteApiJSON.class);
//
//        return clientApi;
//
//    }

    public static ClientRemoteApi getClientRemoteApi(String krameriusHostUrl, String login, String password) {

        // hlavičky Authorization a User-Agent (pro identifikaci v logu)
        final AuthenticationInterceptor authInterceptor = new AuthenticationInterceptor(login, password);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                // přidání hlaviček
                .setRequestInterceptor(authInterceptor)
                // základ URL
                .setEndpoint(PROTOCOL + krameriusHostUrl + KRAMERIUS_CLIENT_API);
                // deserializace defaultně jako JSON
        ClientRemoteApiJSON apiJSON = builder.build().create(ClientRemoteApiJSON.class);

        builder = new RestAdapter.Builder()
                .setRequestInterceptor(authInterceptor)
                // deserializace jako String
                .setConverter(new StringConverter())
                .setEndpoint(PROTOCOL + krameriusHostUrl + KRAMERIUS_CLIENT_API);
        ClientRemoteApiString apiString = builder.build().create(ClientRemoteApiString.class);

        // spojení do 1 objektu
        ClientRemoteApi api = new ClientRemoteApi(apiJSON, apiString);
        return api;
    }
}
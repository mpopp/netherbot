package chatbot.repositories.impl;

import chatbot.repositories.api.TwitchRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author Matthias Popp
 *         Service for wrapping plain twitch API calls and processing them.
 */
@Component
public class TwitchAPIRepository implements TwitchRepository {
    public static final String BASE_PATH = "https://api.twitch.tv/kraken";
    private static final String CHANNEL = "{channel}";
    private final String getFollowersForChannel = BASE_PATH + "/channels/" + CHANNEL + "/follows";
    private final String getStream = BASE_PATH + "/streams?channel=" + CHANNEL;

    HttpClient client;

    public TwitchAPIRepository() {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        client = HttpClientBuilder.create().setSSLContext(ctx).build();
    }

    @Override
    public String findFollowersForChannel(String channelName) {
        String queryString = StringUtils.replace(getFollowersForChannel, CHANNEL, channelName);
        return sendGetRequest(queryString);
    }

    @Override
    public String findStream(String channelName) {
        return sendGetRequest(StringUtils.replace(getStream, CHANNEL, channelName));
    }


    private String sendGetRequest(String queryString) {
        HttpGet getRequest = new HttpGet(queryString);
        getRequest.addHeader("accept", "application/json");
        String responseString = null;
        try {
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                System.err.println("The following call was not successful!");
                System.err.println(queryString);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder builder = new StringBuilder();
            String aux = "";

            while ((aux = br.readLine()) != null) {
                builder.append(aux);
            }
            responseString = builder.toString();
        } catch (IOException e) {
            System.err.println("The following call was not successful!");
            System.err.println(queryString);
        }
        return responseString;
    }

    /**
     * Accepts all certificates, even self signed ones.
     */
    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

}

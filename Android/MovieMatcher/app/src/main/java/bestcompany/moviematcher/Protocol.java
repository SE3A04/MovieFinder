package bestcompany.moviematcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class Protocol {
    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
    private static final String CERTIFICATE_TYPE = "X.509";
    private static final String CERTIFICATE_ALIAS = "MMCert";
    private static final String SECURE_PROTOCOL = "TLS";

    private String hostName;
    private SSLContext ssl;

    public Protocol(InputStream caCert, String hostName) throws GeneralSecurityException, IOException {
        CertificateFactory certFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE);
        Certificate cert = certFactory.generateCertificate(caCert);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null);
        keystore.setCertificateEntry(CERTIFICATE_ALIAS, cert);

        tmf.init(keystore);

        ssl = SSLContext.getInstance(SECURE_PROTOCOL);
        ssl.init(null, tmf.getTrustManagers(), null);

        this.hostName = hostName;
    }

    private class HNV implements HostnameVerifier {
        private String allowedHost;

        public HNV(String allowedHost) {
            this.allowedHost = allowedHost;
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return hostname.equals(allowedHost);
        }
    }

    private String mapToEncoded(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();

        for (String key : map.keySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key);
            sb.append("=");
            sb.append(map.get(key));
        }

        return sb.toString();
    }

    public String securePostRequest(String url, boolean getResponse) throws IOException {
        return securePostRequest(url, getResponse, null);
    }

    public String securePostRequest(String url, boolean getResponse, Map<String, String> params) throws IOException {
        String response = "";
        byte[] postContent = null;
        boolean sendPost = false;
        if (params != null) {
            postContent = mapToEncoded(params).getBytes(CHARSET_UTF8);
            sendPost = true;
        }

        HttpsURLConnection con = (HttpsURLConnection)new URL("https://" + hostName + "/" + url).openConnection();
        con.setHostnameVerifier(new HNV(hostName));
        con.setSSLSocketFactory(ssl.getSocketFactory());
        con.setRequestMethod("POST");
        con.setDoOutput(sendPost);
        con.setDoInput(getResponse);

        if (sendPost) {
            con.setFixedLengthStreamingMode(postContent.length);
            OutputStream os = con.getOutputStream();
            os.write(postContent);
            os.close();
        }

        if (getResponse) {
            String currentLine;
            StringBuilder sb = new StringBuilder();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET_UTF8));

            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
                sb.append('\n');
            }

            br.close();

            response = sb.toString();
        }

        con.disconnect();

        return response;
    }

}
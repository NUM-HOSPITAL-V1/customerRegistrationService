package mn.edu.userapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@Component
public class HttpClientUtil {
    private final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public String httpRequest(String url, String body, String method, Map<String, String> headers) throws Exception {
        HttpURLConnection conn = null;
        try {
            logger.debug("HTTP request started [url={}, method={}, body={}]", url, method, body);
            URLConnection urlCon = new URL(url).openConnection();

            if(urlCon instanceof HttpsURLConnection) {
                conn = (HttpsURLConnection) urlCon;
            } else {
                conn = (HttpURLConnection) urlCon;
            }

            conn.setDoInput(true);
            conn.setReadTimeout(90000);
            conn.setConnectTimeout(60000);

            conn.setRequestMethod(method);

            if(headers != null && !headers.isEmpty()) {
                for(Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                    logger.debug("Header - {}: {}", entry.getKey(), entry.getValue());
                }
            }

            if(body != null) {
                conn.setDoOutput(true);
                try(OutputStream out = conn.getOutputStream();
                    OutputStreamWriter wout = new OutputStreamWriter(out)) {
                    wout.write(body);
                    wout.flush();
                }
            } else {
                conn.setDoOutput(false);
            }

            conn.connect();

            int responseCode = conn.getResponseCode();
            logger.debug("HTTP request completed [respCode={}, respMsg={}]", responseCode, conn.getResponseMessage());

            InputStream inputStream = null;
            if(responseCode == 200 || responseCode == 201) {
                inputStream = conn.getInputStream();
            } else if(responseCode == 204) {
                return "";
            } else {
                inputStream = conn.getErrorStream();
            }

            try (InputStreamReader isr = new InputStreamReader(inputStream);
                 BufferedReader in = new BufferedReader(isr)) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\r\n");
                }

                logger.debug("[responseString={}]", response.toString());
                return response.toString();
            }
        } catch (Exception e) {
            logger.error("Error in HTTP request: {}", e.getMessage());
            throw e;
        }
    }
}

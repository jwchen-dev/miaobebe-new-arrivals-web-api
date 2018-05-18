package com.miaobebe;

import com.uitox.browser.UitoxBrowser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by tech0251 on 2018/5/17.
 */
public class Runner {

    public static void main(String[] args) {

        Runner runner = new Runner();
        runner.run();
    }


    public void run() {

        try {
            UitoxBrowser browser = new UitoxBrowser.UitoxBrowserBuilder().build();
            browser.setVisible(true);
            browser.loadURL("https://www.carters.com/carters-toddler-girl-new-arrivals?navID=left&sz=all");
            browser.scrollToBottom();
            browser.shutdown();

            Document doc = Jsoup.parse(browser.html());

            Elements divs = doc.body().select("div.grid-tile3");
            System.out.println("[]" + divs.size());
            for (int i = 0, size = divs.size(); i < size; i++) {
                Element div = divs.get(i);

                //
                String title = div.select("a.name-link").attr("title");
                System.out.println(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private SSLSocketFactory disableSSLCertificateChecking() {

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {

                return null;
            }


            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }


            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            return sc.getSocketFactory();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}

package de.orat.netbeans.translator.impl.google;

import de.orat.netbeans.translator.spi.iTranslatorImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;
import java.util.ResourceBundle;
import org.openide.util.NbBundle;

/**
 * Translator based on the google translation service.
 * 
 * available languages:<br>
 * https://cloud.google.com/translate/docs/languages<p>
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
@ServiceProvider(service=de.orat.netbeans.translator.spi.iTranslatorImpl.class)
public class GoogleTranslator implements iTranslatorImpl {
     
    /**
     * Translate the given String into the given language.
     * 
     * @param sourceLang ""==auto
     * @param targetLang en, de
     * @param sourceText source text
     * @return translated text
     * @throws IOException if no internet connection available, or the thr remote service invocation is failed.
     */
    @Override
    public String translate(String sourceLang, String targetLang, 
                            String sourceText) throws IOException {
        String urlString = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + 
                sourceLang + "&tl=" + targetLang + "&dt=t&ie=UTF-8&oe=UTF-8&q=" + 
                URLEncoder.encode(sourceText, "UTF-8");
        URL url = new URL(urlString);
        
        // HttpURLConnection does not implement AutoClosable interface, so try-catch-with-resource
        // can not be used. Better use apache.httpURLConnection instead
        //TODO
        HttpURLConnection con = null;
        String errorMessage = "";
        try {
            con = (HttpURLConnection) url.openConnection();
            try {
                final int responseCode = con.getResponseCode();
                // nach dem zweiten Aufruf des service:
                // Response code = 429 : Server returned HTTP response code: 429 for 
                // URL: https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl=hi&dt=t&ie=UTF-8&oe=UTF-8&q=Dies+ist+ein+Test.
                System.out.println("Response code = "+String.valueOf(responseCode)+"!");
                switch (responseCode){
                    // request timeout
                    case 408:
                        errorMessage = "(408) Request timeout!";
                        break;

                    // payload too large
                    case 413:
                        errorMessage ="(413) Payload too large!";
                        break;

                    // service unavailable
                    case 503:
                        errorMessage = "(503) Service unavailable!";
                        break;

                    // Too many requests
                    case 429:
                        errorMessage = "(429) Too many requests in a given amount of time (\"rate limiting\")!";
                        break;
                    case 200:
                    default:
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));){
                            StringBuilder response = new StringBuilder();
                            String inputLine;
                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            String result = response.toString();
                            
                            // https://stackoverflow.com/questions/52038464/decode-google-translate-api-response-in-java
                            int start = result.indexOf("\"")+1;
                            return result.substring(start, result.indexOf("\"",start));
                        }
                }
            } catch (UnknownHostException h){
                errorMessage = "Internet connection is not available!";
            }
        } catch (IOException e){
            errorMessage = e.getLocalizedMessage();
        } finally {
            if (con != null){
                con.disconnect();
            }
        }
        throw new IOException(errorMessage);
    }

    @Override
    public String getName() {
        return "google translator";
    }

    //private Translator.Callback callback;
    
    /*@Override
    public void init(Translator.Callback callback) {
        this.callback = callback;
    }*/

    //TODO
    // String localized = NbBundle.getMessage(ThisClass.class, "LBL_some_text");
    
    @Override
    public Map<String, String> readAvailableTargetLanguages() {
        //ResourceBundle bundle =  ResourceBundle.getBundle("de.orat.translator.impl.google.targetlanguages");
        // test ob der folgende code auch funktioniert und damit automatisch lokalisiert
        ResourceBundle bundle = NbBundle.getBundle("de.orat.translator.impl.google.targetlanguages");
        Map<String, String> result = new HashMap<>();
        Enumeration<String> keyEnum = bundle.getKeys();
        while (keyEnum.hasMoreElements()){
            String key = keyEnum.nextElement();
            result.put(key, bundle.getString(key));
        }
        return result;
    }
}
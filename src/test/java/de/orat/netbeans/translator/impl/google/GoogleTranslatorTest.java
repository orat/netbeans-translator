package de.orat.netbeans.translator.impl.google;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.junit.NbTestCase;

/**
 * @author Oliver Rettig
 */
public class GoogleTranslatorTest extends NbTestCase {

    private static final String DEFAULT_TEST_SOURCE_TEXT = "Dies ist ein Test.";
    private static final String DEFAULT_TEST_SOURCE_LANGUAGE_CODE = "de";
    
    public GoogleTranslatorTest(String name) {
        super(name);
    }
    
    public void testTranslate(){
        GoogleTranslator googleTranslator = new GoogleTranslator();
        Map<String, String> languages = googleTranslator.readAvailableTargetLanguages();
        
        // key == language code, value == translated DEFAULT_TEST_SOURCE_TEXT
        Map<String, String> translations = new HashMap<>();
        
       /* languages.keySet().forEach((targetLanguageCode) -> {
            try {
                // I've just tried this method again myself and was blocked after 
                // ~100 sequential requests in under a minute. During that time a 
                // HTML page with a ReCaptcha was being returned instead of the 
                // expected response. The requests began succeeding again after 
                // several minutes of no requests and without submitting the ReCaptcha.
                
                String translatedText = googleTranslator.translate("auto", targetLanguageCode, DEFAULT_TEST_SOURCE_TEXT);
                translations.put(targetLanguageCode, translatedText);
                System.out.println(languages.get(targetLanguageCode)+": "+translatedText);
            } catch (IOException e){
                assertTrue("failure - IO exception for translating into the language \""+targetLanguageCode+"\" : "+e.getMessage(), false);
            }
        });*/
        
        //TODO
        // save translations into properties file
        
        //TODO
        // translate all translations back into the source language, compare with the original
        
        //TODO
        // test mit fehlende Internetverbindung
    }
}

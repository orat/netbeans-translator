package de.orat.netbeans.translator.api;

import de.orat.netbeans.translator.spi.iTranslatorImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Translator API class.
 * 
 * @author Oliver Rettig
 */
public class Translator {
    
    private final iTranslatorImpl impl;
    
    static final Map<iTranslatorImpl, Translator>
             impls = new HashMap<iTranslatorImpl, Translator>();

    public String getName(){
        return impl.getName();
    }
    
    private Translator(iTranslatorImpl impl){
        this.impl = impl;
    }
    static Translator create(iTranslatorImpl impl){
        Translator result = impls.get(impl);
        if (result == null){
            result = new Translator(impl);
            //Callback callback = new Callback(result);
            //impl.init(callback);
            impls.put(impl, result);
        }
        return result;
    }
    public void delete(){
        impls.remove(impl);
    }
    
    /*public static final class Callback {

        private final Translator api;

        Callback(Translator api){
            this.api = api;
        }
    }*/  
    
    /**
     * Translate the given String into the given language.
     * 
     * @param sourceLang source language codes, or auto for autodetect the source language
     * @param targetLang target langueage codes, e.g. en, de
     * @param sourceText source text
     * @return translated source
     * @throws IOException if remote service fails
     */
    public String translate(String sourceLang, String targetLang, String sourceText) throws IOException {
        return impl.translate(sourceLang, targetLang, sourceText);
    }
     
    /**
     * Read a list of available target languages.
     * 
     * @return key=code, value=display name
     */
    public Map<String, String> readAvailableTargetLanguages(){
        return impl.readAvailableTargetLanguages();
    }
}
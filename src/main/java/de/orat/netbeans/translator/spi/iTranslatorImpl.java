package de.orat.netbeans.translator.spi;

import java.io.IOException;
import java.util.Map;

/**
 * Service provider interface to register translator implementations.
 * 
 * @author Oliver Rettig
 */
public interface iTranslatorImpl {
    
   /**
    * Translate the given String into the given language.
    * 
    * @param sourceLang source language codes, or auto for autodetect the source language
    * @param targetLang target langueage codes, e.g. en, de
    * @param sourceText source text
    * @return translated source
    * @throws IOException if invocation of remote translation service fails.
    */
   public String translate(String sourceLang, String targetLang, String sourceText) throws IOException;
   
   /**
    * Get a name for the translator implementation.
    * 
    * @return implementation name
    */
   public String getName();
   
   //public void init(Translator.Callback callback);
   
   /**
    * Read a list of available target languages.
    * 
    * @return key=code, value=display name
    */
   public Map<String, String> readAvailableTargetLanguages();
}

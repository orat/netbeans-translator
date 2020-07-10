package de.orat.netbeans.translator.api;

import de.orat.netbeans.translator.spi.iTranslatorImpl;
import java.util.Collection;
import java.util.Iterator;
import org.openide.util.Lookup;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class TranslatorManager {
    
    private static TranslatorManager instance;
    
    public static TranslatorManager getInstance(){
        if (instance == null){
            instance = new TranslatorManager();
        }
        return instance;
    }
    private TranslatorManager(){
        Collection<? extends iTranslatorImpl> c =
                Lookup.getDefault().lookupAll(iTranslatorImpl.class);
        for (Iterator i = c.iterator(); i.hasNext();) {
            Translator.create((iTranslatorImpl) i.next());
        }
    }
    
    public Translator getTranslator(){
        if (!Translator.impls.isEmpty()){
            return Translator.impls.values().iterator().next();
        }
        return null;
    }
}
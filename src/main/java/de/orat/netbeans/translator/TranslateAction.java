package de.orat.netbeans.translator;

import de.orat.netbeans.translator.api.Translator;
import de.orat.netbeans.translator.api.TranslatorManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.openide.text.NbDocument;
import org.netbeans.api.editor.EditorRegistry;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;

/**
 * Translation service action.
 * 
 * Open any file, right-click on a selection of text and choose Translate. 
 * 
 * @author Oliver Rettig
 */
@ActionRegistration(
        displayName = "#CTL_TranslateAction"
)
@NbBundle.Messages("CTL_TranslateAction=Translate")
@ActionID(category = "Translate", id = "de.orat.googletranslate.TranslateAction")
// https://netbeans.apache.org/wiki/DevFaqActionAddToContextMenuOfAllEditors.asciidoc
@ActionReference(path="Editors/Popup") // for an action with EditorCookie as context object
public final class TranslateAction implements ActionListener{
    
    // https://netbeans.apache.org/wiki/DevFaqMultipleProgrammaticEdits.asciidoc
    private class RunnableAction implements Runnable {
        
        private final JTextComponent ed;
        private final Document doc;
        
        private RunnableAction(JTextComponent ed, Document doc){
            this.ed = ed;
            this.doc = doc;
        }

        @Override
        public void run(){
            try {
                String sourceText = ed.getSelectedText();
                
                String targetLang = NbPreferences.forModule(
                    TranslateAction.class).get("selectedTargetLanguageCode", "de");
                
                Translator translator = TranslatorManager.getInstance().getTranslator();
                try {
                    if (translator != null){
                        String targetText = translator.translate("auto", targetLang, sourceText);
                        
                        int start = ed.getSelectionStart();
                        doc.remove(start, ed.getSelectionEnd()-start);
                        doc.insertString(start, targetText, null);
                    } else {
                        StatusDisplayer.getDefault().setStatusText("No translator available!");
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                    String translatorName = "UNKNOWN";
                    if (translator != null){
                        translatorName = translator.getName();
                    }
                    StatusDisplayer.getDefault().setStatusText("Invocation of \""+translatorName+"\" service failed!");
                }
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
        // Truncate excessively long strings. Limit for Google Translate is 5000 characters?
        /*if (sourceText.length() > 4500) {
            sourceText = sourceText.substring(0, 4500);
        }*/
    }

    @Override
    public void actionPerformed(ActionEvent e){
        JTextComponent ed = EditorRegistry.lastFocusedComponent();
        StyledDocument doc = (StyledDocument) ed.getDocument();

        // Perform all of the changes atomically so that they can be undone with one undo.
        NbDocument.runAtomic(doc, new RunnableAction(ed, doc));
    }
}
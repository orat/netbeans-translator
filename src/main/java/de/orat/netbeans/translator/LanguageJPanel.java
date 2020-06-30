package de.orat.netbeans.translator;

import de.orat.netbeans.translator.api.TranslatorManager;
import java.awt.event.ItemEvent;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultComboBoxModel;
import org.openide.util.NbPreferences;

/**
 * @author Oliver Rettig
 */
public class LanguageJPanel extends javax.swing.JPanel {

    private static final String DEFAULT_SELECTION_LANGUAGE_CODE = "de";
    
    private class Language {
        private final String code;
        private final String displayName;
        public Language(String code, String displayName){
            this.code = code;
            this.displayName = displayName;
        }
        @Override
        public String toString(){
            return displayName;
        }
        public String getCode(){
            return code;
        }
    }
    
    /**
     * Create a Combobox Model as a list of available target languages sorted by the language names.
     * 
     * @return available target languages model to be shown in the UI.
     */
    private DefaultComboBoxModel<Language> getAvailableTargetLanguages(){
        Map<String, String> languages = TranslatorManager.getInstance().getTranslator().readAvailableTargetLanguages();
        
        // sort by display name
        Map<String, Language> sortedLanguages = new TreeMap<>();
        languages.forEach((code,displayName)->sortedLanguages.put(displayName, new Language(code,displayName)));
        
        return new javax.swing.DefaultComboBoxModel<>(sortedLanguages.values().
                toArray(new Language[languages.size()]));
    }
    
    /**
     * Creates new form FrameJPanel
     */
    public LanguageJPanel() {
        initComponents();
        
        String code = NbPreferences.forModule(
                TranslateAction.class).get("selectedTargetLanguageCode", DEFAULT_SELECTION_LANGUAGE_CODE);
        for (int i=0;i<languageComboBox.getItemCount();i++){
            Language language = languageComboBox.getItemAt(i);
            if (language.getCode().equals(code)){
                languageComboBox.setSelectedItem(language);
                break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        languageComboBox = new javax.swing.JComboBox<>();

        languageComboBox.setModel(getAvailableTargetLanguages());
        languageComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(LanguageJPanel.class, "LanguageJPanel.languageComboBox.toolTipText")); // NOI18N
        languageComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                languageComboBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void languageComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_languageComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            NbPreferences.forModule(
                TranslateAction.class).put("selectedTargetLanguageCode", ((Language) evt.getItem()).getCode());
        }
    }//GEN-LAST:event_languageComboBoxItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Language> languageComboBox;
    // End of variables declaration//GEN-END:variables
}

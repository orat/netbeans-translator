package de.orat.netbeans.translator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

/**
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
@ActionID(
        category = "Edit",
        id = "de.orat.googletranslate.TargetLanguageAction"
)
@ActionRegistration(
        displayName = "#CTL_TargetLanguageAction",
        lazy=false
)
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = 0),
    @ActionReference(path = "Toolbars/Translator", position = 0)
})
@Messages("CTL_TargetLanguageAction=Target language")
public final class TargetLanguageAction extends AbstractAction implements Presenter.Toolbar {

    private final LanguageJPanel panel = new LanguageJPanel();
    
    @Override
    public void actionPerformed(ActionEvent ev) {}
    
    @Override
    public Component getToolbarPresenter() {
        return panel;
    }
}
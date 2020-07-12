# netbeans-translator
Translation of selected text in the Netbeans editor.

1. Build the Netbeans module with maven.
2. Install the module in your Netbeans IDE.
3. Look in the Translator toolbar (View > Toolbars > Translate, if it is 
   closed) where you should see a list of target languages.
4. Open any kind of file.
5. Select a peace of text.
6. Right-click and choose Translate.
7. Text changes to the target-language.
8. If a problem occurs, a message is printed in the status bar. Look into the notification-window for details. For example, 
   Google translation service doesn't support all from/to combinations and
   its usage is restricted to a fix number of invocations in a time interval. If the selected text is not translated this is the most probable reason.

package net.c0f3.jjsc.core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: KPC
 * Date: 07.11.12 - 18:42
 */
public class ScriptLoader {
    private Logger log = Logger.getLogger(ScriptContainer.MODULE_NAME);

    public ScriptHandler loadScript(String scriptName, String scriptCode) {
        ScriptHandler script =  new ScriptHandler(scriptName, scriptCode);
        try {
            script.init();
        } catch (ScriptingException se) {
            log.log(Level.WARNING,"script \""+scriptName+"\" failed to load: "+se.getMessage());
            return null;
        }
        return script;
    }

}

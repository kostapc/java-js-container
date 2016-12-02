package net.c0f3.jjsc.core;


import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: KPC
 * Date: 07.11.12 - 18:42
 */

public class ScriptHandler { // single object for each script file

    private Logger log = Logger.getLogger(ScriptContainer.MODULE_NAME);

    private static final int DEBUG_LINES = 5;

    private static ScriptEngineManager mngr = new ScriptEngineManager();
    private static final String SCRIPT_LANG = "JavaScript";
    private static final String INSTALL = "install";

    private String scriptCode;
    private ScriptEngine script;

    public ScriptHandler(String scriptName, String inScriptCode) {
        scriptCode = scriptName + "\n\n\n" + inScriptCode;
    }

    public void init() throws ScriptingException {
        try {
            script = mngr.getEngineByName(SCRIPT_LANG);
            script.eval(scriptCode);
            ((Invocable)script).invokeFunction(INSTALL);
        } catch (NumberFormatException | NoSuchMethodException e) {
            throw new ScriptingException(e);
        } catch (ScriptException e) {
            processScriptException("init", e);
            throw new ScriptingException(e);
        }
    }

    public boolean onEvent(String eventName, ScriptDataContext source) {
        try {
            ((Invocable)script).invokeFunction(eventName, source);
        } catch (ScriptException e) {
            processScriptException(eventName, e);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    private void processScriptException(String eventName, ScriptException e) {
        String[] lines = scriptCode.split("\n");
        String nearLines = "";
        int lineNumber = e.getLineNumber();
        for(int i=lineNumber-DEBUG_LINES/2-DEBUG_LINES%2;i<=lineNumber+DEBUG_LINES/2;i++) {
            if(i<0 || i>=lines.length) {
                continue;
            }
            if(i+1==lineNumber) {
                nearLines += "\t"+(i+1)+">\t"+lines[i]+"\n";
            } else {
                nearLines += "\t"+(i+1)+")\t"+lines[i]+"\n";
            }
        }
        log.log(Level.FINE,"("+eventName+") Script exception <" + e.getMessage() + "> at \n" + nearLines);
    }
}

package org.github.snambi.bbs.util;

import javax.script.*;

/**
 */
public class JavascriptParser {

    ScriptEngineManager scriptEngineManager=null;
    ScriptEngine jsEngine =null;

    public JavascriptParser() throws ScriptException {
        scriptEngineManager = new ScriptEngineManager();
        jsEngine = scriptEngineManager.getEngineByName("JavaScript");

        // Add the JSON function
        String parseFn = "function parseJSON( json ){ var obj = JSON.parse(json); return obj; }";

        jsEngine.eval(parseFn);
    }

    public void addFunction(String jsFunction) throws ScriptException {
        jsEngine.eval(jsFunction);
    }


    public Object parse( String json ) throws ScriptException, NoSuchMethodException {

        Invocable inv = (Invocable) jsEngine;
        Object result = ((Invocable) jsEngine).invokeFunction("parseJSON", json);

        System.out.println("JSON "+ result);

        return result;
    }

    // invoked 'getHeader' function
    public Object getHeader( String json ) throws ScriptException, NoSuchMethodException {

        Invocable inv = (Invocable) jsEngine;
        Object result = ((Invocable) jsEngine).invokeFunction("getHeader", json);

        System.out.println("JSON "+ result);

        return result;
    }

}

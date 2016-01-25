package com.jass.core.elements;

import static com.jass.core.handlers.JSHandler.executeJavaScript;

public class JSElement {

    private String path;

    public JSElement(String path) {
        this.path = path;
    }

    public boolean isChecked() {
        return executeJavaScript(String.format("return %s.checked", path));
    }

    public void click() {
        executeJavaScript(String.format("%s.click()",path));
    }

    public void select() {
        executeJavaScript(String.format("%s.select()", path));
    }
}
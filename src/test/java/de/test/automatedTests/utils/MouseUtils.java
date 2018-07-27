package de.test.automatedTests.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class MouseUtils {

    public static void dragAndDropIssues(String source, String target, int indexSource, int numberIssues, WebDriver driver) {
        for (int i = 0; i < numberIssues; i++) {
            ((JavascriptExecutor) driver).executeScript("var event = new Event('dragstart');event.dataTransfer = {setData: function(text, value){ (window._transfer||(window._transfer={}))[text] = value;}};\n" +
                    "document.querySelectorAll('" + source + "')[" + String.valueOf(indexSource) + "].dispatchEvent(event);\n" +
                    "var evente = new Event('drop'); evente.dataTransfer = {getData: function(text, value){ return (window._transfer||(window._transfer={}))[text];}};\n" +
                    "document.querySelector('" + target + "').dispatchEvent(evente);");
        }
    }
}

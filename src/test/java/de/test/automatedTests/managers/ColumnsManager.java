package de.test.automatedTests.managers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ColumnsManager {


    WebDriver driver;
    public static String ADDD_COLUMN_BUTTON_ID = "#addColBtn";
    public static String MAX_NUMBER_OF_TICKETS_ID = "#addColInput";
    public static String BLOCK_AT_MAX_CHECK = "#myCheck";
    public static String SAVE_BUTTON_SELECTOR = "#addCol .button";
    public static String SECOND_COLUMN="#mainList  li:nth-child(2)";
    public static String SECOND_COLUMN_CLOSE_BUTTON="#mainList  li:nth-child(2) div.close";
    public static String MAIN_COLUMN_SELECTOR="#mainList .newDiv";

    public ColumnsManager(WebDriver driver) {
        this.driver = driver;
    }

    public void addColumnActions(int maxNumber, Boolean flag) {
        //enter on add column menu
        WebElement addColumnButton = driver.findElement(By.cssSelector(ADDD_COLUMN_BUTTON_ID));
        addColumnButton.click();
        new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.elementToBeClickable(By.cssSelector(SAVE_BUTTON_SELECTOR)));
        //set max number of elements
        WebElement numberOfTickets = driver.findElement(By.cssSelector(MAX_NUMBER_OF_TICKETS_ID));
        numberOfTickets.sendKeys(String.valueOf(maxNumber));

        //checkbox for block column as maxNumber
        WebElement maxNumberBlock = driver.findElement(By.cssSelector(BLOCK_AT_MAX_CHECK));
        if (flag)
            maxNumberBlock.click();
        //press save button
        WebElement saveButton = driver.findElement(By.cssSelector(SAVE_BUTTON_SELECTOR));
        saveButton.click();
        new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(SAVE_BUTTON_SELECTOR)));


    }
}

package de.test.automatedTests.managers;

import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class IssueManager {

    WebDriver driver;

    public IssueManager(WebDriver driver) {
        this.driver = driver;
    }


    public static String CREATE_BUTTON_SELECTOR = "#myBtn";
    public static String TITLE_INPUT_SELECTOR = "#titleInput";
    public static String CONTENT_INPUT_SELECTOR = "#contentInput";
    public static String LEFT_COLUMN_SELECTOR = ".left.dropzone .newDiv";
    public static String EDIT_ISSUE_TITLE = "#editTitle";
    public static String EDIT_CONTENT = "#editContent";
    public static String ISSUE_HEADER_SELECTOR=".newDivHeader";
    public static String ISSUE_CONTENT_SELECTOR=".newDivContent";


    public void createIssue(String title, String content) {
        //enter on create issue menu
        WebElement createButton = driver.findElement(By.cssSelector(CREATE_BUTTON_SELECTOR));
        createButton.click();
        new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(TITLE_INPUT_SELECTOR)));
        //set the title of the issue
        WebElement titleInput = driver.findElement(By.cssSelector(TITLE_INPUT_SELECTOR));
        titleInput.sendKeys(title);

        WebElement contentInput = driver.findElement(By.cssSelector(CONTENT_INPUT_SELECTOR));
        contentInput.sendKeys(content);

        WebLocator myModal = new WebLocator().setId("myModal");
        WebLocator saveButton = new WebLocator(myModal).setClasses("button").setText("Save");
        saveButton.click();
        new WebDriverWait(driver, ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(TITLE_INPUT_SELECTOR)));
    }

    public void editIssue(int index, String title, String content) {
        List<WebElement> firstColumnElements = driver.findElements(By.cssSelector(LEFT_COLUMN_SELECTOR));
        //select the element from the list
        firstColumnElements.get(index).click();
        WebElement editTitleElement = driver.findElement(By.cssSelector(EDIT_ISSUE_TITLE));
        //change the title
        editTitleElement.clear();
        editTitleElement.sendKeys(title);
        WebElement editContentElement = driver.findElement(By.cssSelector(EDIT_CONTENT));
        //change the content
        editContentElement.clear();
        editContentElement.sendKeys(content);
        //click on edit button
        WebLocator modal = new WebLocator().setClasses("modal-content");
        WebLocator editButton = modal.setClasses("button").setText("Edit");
        editButton.click();

    }
}

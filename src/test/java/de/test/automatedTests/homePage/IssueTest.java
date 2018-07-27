package de.test.automatedTests.homePage;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import de.test.automatedTests.config.AbstractAcceptanceTest;
import de.test.automatedTests.managers.ApplicationManager;
import de.test.automatedTests.utils.MouseUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.List;

import static de.test.automatedTests.managers.ColumnsManager.MAIN_COLUMN_SELECTOR;
import static de.test.automatedTests.managers.ColumnsManager.SECOND_COLUMN;
import static de.test.automatedTests.managers.IssueManager.*;

public class IssueTest extends AbstractAcceptanceTest {


    class Ticket {
        int id;
        String title;
        String content;


        Ticket(int i, String titlu, String continut) {
            id = i;
            title = titlu;
            content = continut;
        }
    }


    @Test
    public void createNewIssue() {

        //the data for new issue
        String newIssueTitle = "test";
        String newIssueContent = "test";
        //create the new issue
        IssuesMananger.createIssue(newIssueTitle, newIssueContent);
        //verify if the title and content for the new issue match with data sent by us
        WebElement newIssueElement = getWebDriver().findElement(By.cssSelector(LEFT_COLUMN_SELECTOR));
        String issueTitle = newIssueElement.findElement(By.cssSelector(ISSUE_HEADER_SELECTOR)).getText();
        String issueContent = newIssueElement.findElement(By.cssSelector(ISSUE_CONTENT_SELECTOR)).getText();
        Assert.assertEquals(newIssueTitle, issueTitle, "The title of the new issue not match expect" + newIssueTitle + "but is " + issueTitle);
        Assert.assertEquals(issueContent, newIssueContent, "the content of the new issue not match, expect" + newIssueContent + "but is" + issueContent);
    }

    @Test
    public void cancelCreateNewIssue() {
        String newIssueTitle = "test";
        String newIssueContent = "test";
        List<WebElement> IssueElements = getWebDriver().findElements(By.cssSelector(LEFT_COLUMN_SELECTOR));
        //save the initial number of issues
        int length = IssueElements.size();
        //enter on create issue menu
        WebElement createButton = getWebDriver().findElement(By.cssSelector(CREATE_BUTTON_SELECTOR));
        createButton.click();
        new WebDriverWait(getWebDriver(), ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(TITLE_INPUT_SELECTOR)));

        //set the title of the issue
        WebElement titleInput = getWebDriver().findElement(By.cssSelector(TITLE_INPUT_SELECTOR));
        titleInput.sendKeys(newIssueTitle);
        //write a content in the issue
        WebElement contentInput = getWebDriver().findElement(By.cssSelector(CONTENT_INPUT_SELECTOR));
        contentInput.sendKeys(newIssueContent);
        //cancel the tab
        WebLocator myModal = new WebLocator().setId("myModal");
        WebLocator cancelButton = new WebLocator(myModal).setClasses("button").setText("Cancel");
        cancelButton.click();
        new WebDriverWait(getWebDriver(), ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(TITLE_INPUT_SELECTOR)));
        //verify if the number of issues is the same
        IssueElements = getWebDriver().findElements(By.cssSelector(LEFT_COLUMN_SELECTOR));
        Assert.assertEquals(IssueElements.size(), length, "The number of the news issues need to be zero");
    }

    @Test
    public void verifyCreateIssue() {
        //enter on create issue menu
        WebElement createButton = getWebDriver().findElement(By.cssSelector(CREATE_BUTTON_SELECTOR));
        createButton.click();
        WebLocator myModal = new WebLocator().setId("myModal");
        WebLocator saveButton = new WebLocator(myModal).setClasses("button").setText("Save");
        saveButton.click();

        //verify if title and contain have boarder red
        WebElement titleInput = getWebDriver().findElement(By.cssSelector(TITLE_INPUT_SELECTOR));
        Assert.assertEquals(titleInput.getCssValue("border"), "2px solid rgb(255, 0, 0)", "The board for title input need to be 2px solid rgb(255, 0, 0)");
        WebElement contentInput = getWebDriver().findElement(By.cssSelector(CONTENT_INPUT_SELECTOR));
        Assert.assertEquals(contentInput.getCssValue("border"), "2px solid rgb(255, 0, 0)", "The board for content input need to be 2px solid rgb(255, 0, 0)");

        //verify good title but empty contain
        titleInput.sendKeys("test");
        //need to pres click else the board did'n make blue
        titleInput.click();
        Assert.assertEquals(titleInput.getCssValue("border"), "2px inset rgb(0, 0, 0)", "The board for title input isn good");
        Assert.assertEquals(contentInput.getCssValue("border"), "2px solid rgb(255, 0, 0)", "The board for content input need to be 2px solid rgb(255, 0, 0)");

        //make inputs red
        titleInput.clear();
        saveButton.click();
        //verify  good contain, empty title
        contentInput.sendKeys("test");
        contentInput.click();
        Assert.assertEquals(titleInput.getCssValue("border"), "2px solid rgb(255, 0, 0)", "The board for title input isn good");
        Assert.assertEquals(contentInput.getCssValue("border"), "1px solid rgb(169, 169, 169)", "The board for content input need to be 1px solid rgb(169, 169, 169)");
    }

    @Test
    public void editIssueTest() {
        //add 5 issues in the first colunm
        for (int i = 0; i < 5; i++) {
            IssuesMananger.createIssue("test", "test");
        }
        //change the second element
        IssuesMananger.editIssue(1, "second", "second");
        //verify the changes
        List<WebElement> firstColumnIssues = getWebDriver().findElements(By.cssSelector(LEFT_COLUMN_SELECTOR));
        String issueTitle = firstColumnIssues.get(1).findElement(By.cssSelector(ISSUE_HEADER_SELECTOR)).getText();
        String issueContent = firstColumnIssues.get(1).findElement(By.cssSelector(ISSUE_CONTENT_SELECTOR)).getText();
        Assert.assertEquals("second", issueTitle, "The title of the new issue not match expect second but is " + issueTitle);
        Assert.assertEquals(issueContent, "second", "the content of the new issue not match, expect second but is" + issueContent);
    }

    @Test
    public void deleteIssueTest() {
        /**
         * this test will delete an element from the first column
         */
        int index = 3;
        //add 5 issues in the first column
        for (int i = 0; i < 5; i++) {
            IssuesMananger.createIssue(String.valueOf(i), "test");
        }
        List<WebElement> firstColumnIssues = getWebDriver().findElements(By.cssSelector(LEFT_COLUMN_SELECTOR));
        firstColumnIssues.get(index).click();
        WebLocator modal = new WebLocator().setClasses("modal-content");
        //delete the element from index"index=3"
        WebLocator deleteButton = modal.setClasses("button").setText("Delete");
        deleteButton.click();
        //verify if the element with index=3 change
        firstColumnIssues = getWebDriver().findElements(By.cssSelector(LEFT_COLUMN_SELECTOR));
        String issueTitle = firstColumnIssues.get(index).findElement(By.cssSelector(ISSUE_HEADER_SELECTOR)).getText();
        Assert.assertNotEquals(issueTitle, "3");
    }

    @Test
    public void verifyBackgroundAlert() throws AWTException {
        for (int i = 0; i < 10; i++) {
            IssuesMananger.createIssue("test" + i, "test");
        }
        ColumnManager.addColumnActions(5,true);
        WebElement secondColumn;
        MouseUtils.dragAndDropIssues(MAIN_COLUMN_SELECTOR, SECOND_COLUMN, 0,5, getWebDriver());
        secondColumn=getWebDriver().findElement(By.cssSelector(SECOND_COLUMN));
        Assert.assertEquals(secondColumn.getCssValue("background-color"),"rgba(255, 96, 96, 1)");

        List<WebElement> secondColumnIssues=secondColumn.findElements(By.cssSelector(".newDiv"));
        int length=secondColumnIssues.size();
        MouseUtils.dragAndDropIssues(MAIN_COLUMN_SELECTOR, SECOND_COLUMN, 0,2, getWebDriver());
        secondColumnIssues=secondColumn.findElements(By.cssSelector(".newDiv"));
        Assert.assertEquals(length,secondColumnIssues.size());

    }

    @Test
    public void verifyMaxLimitOfIssues(){
        for (int i = 0; i < 10; i++) {
            IssuesMananger.createIssue("test" + i, "test");
        }
        ColumnManager.addColumnActions(2,true);
        WebElement secondColumn=getWebDriver().findElement(By.cssSelector(SECOND_COLUMN));
        List<WebElement> secondColumnIssues=secondColumn.findElements(By.cssSelector(".newDiv"));
        MouseUtils.dragAndDropIssues(MAIN_COLUMN_SELECTOR, SECOND_COLUMN, 0,2, getWebDriver());
        int length=secondColumnIssues.size();
        MouseUtils.dragAndDropIssues(MAIN_COLUMN_SELECTOR, SECOND_COLUMN, 0,2, getWebDriver());
        secondColumnIssues=secondColumn.findElements(By.cssSelector(".newDiv"));
        Assert.assertEquals(length,secondColumnIssues.size());
    }

}

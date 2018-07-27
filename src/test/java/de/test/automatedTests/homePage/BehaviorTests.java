package de.test.automatedTests.homePage;

import de.test.automatedTests.config.AbstractAcceptanceTest;
import de.test.automatedTests.managers.ApplicationManager;
import de.test.automatedTests.utils.MouseUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static de.test.automatedTests.managers.ColumnsManager.*;

public class BehaviorTests extends AbstractAcceptanceTest {
    @Test
    public void verifyBackgroundAlert() {
        //adding 10 issues in the main column
        for (int i = 0; i < 10; i++) {
            IssuesMananger.createIssue("test" + i, "test");
        }
        //make a new column with max limit at 5 issues
        ColumnManager.addColumnActions(5, true);
        WebElement secondColumn;
        //move 5 issue from main column in the second column
        MouseUtils.dragAndDropIssues(MAIN_COLUMN_SELECTOR, SECOND_COLUMN, 0, 5, getWebDriver());
        //verify if the background color of the second column is red
        secondColumn = getWebDriver().findElement(By.cssSelector(SECOND_COLUMN));
        Assert.assertEquals(secondColumn.getCssValue("background-color"), "rgba(255, 96, 96, 1)", "The background of the second column is not rgba(255, 96, 96, 1)");
    }

    @Test
    public void verifyMaxLimitOfIssues() {
        //adding 10 issues in the second column
        for (int i = 0; i < 10; i++) {
            IssuesMananger.createIssue("test" + i, "test");
        }
        //make a new column with max limit at 2 issues
        ColumnManager.addColumnActions(2, true);

        //move 2 issues to the second column
        WebElement secondColumn = getWebDriver().findElement(By.cssSelector(SECOND_COLUMN));
        MouseUtils.dragAndDropIssues(MAIN_COLUMN_SELECTOR, SECOND_COLUMN, 0, 2, getWebDriver());

        //verify and save the number of issues from second column
        List<WebElement> secondColumnIssues = secondColumn.findElements(By.cssSelector(".newDiv"));
        int length = secondColumnIssues.size();

        //try to move another 2 issues on the second column
        MouseUtils.dragAndDropIssues(MAIN_COLUMN_SELECTOR, SECOND_COLUMN, 0, 2, getWebDriver());
        secondColumnIssues = secondColumn.findElements(By.cssSelector(".newDiv"));

        //verify if the number of issues from second column change
        Assert.assertEquals(length, secondColumnIssues.size());
    }

    @Test
    public void verifyDeleteColumnWithIssues() {
        //adding 10 issues in the second column
        for (int i = 0; i < 10; i++) {
            IssuesMananger.createIssue("test" + i, "test");
        }

        //make a new column with max limit at 2 issues
        ColumnManager.addColumnActions(0, true);

        MouseUtils.dragAndDropIssues(MAIN_COLUMN_SELECTOR, SECOND_COLUMN, 0, 4, getWebDriver());

        //try to delete
        WebElement closeButton = getWebDriver().findElement(By.cssSelector(SECOND_COLUMN_CLOSE_BUTTON));
        closeButton.click();

        //exit from pop up and verify if change something in column 2
        WebElement closePopUp = getWebDriver().findElement(By.cssSelector("#closeDel"));
        closePopUp.click();

        WebElement secondColumn = getWebDriver().findElement(By.cssSelector(SECOND_COLUMN));
        Assert.assertTrue(secondColumn.isDisplayed());
        closeButton.click();

        //press the cancel button
        List<WebElement> buttonsPopUp = getWebDriver().findElements(By.cssSelector("#confirmation .button"));
        buttonsPopUp.get(1).click();
        Assert.assertTrue(secondColumn.isDisplayed());

        //press the delete button and the column is deleted
        closeButton.click();
        buttonsPopUp.get(0).click();
        new WebDriverWait(getWebDriver(), ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.invisibilityOf(secondColumn));

    }

    @Test
    public void verifyInvisibilityOfCloseIcon() {
        //identify the close button for first and last column
        List<WebElement> closeButtonsList = getWebDriver().findElements(By.cssSelector("#mainList .close"));
        for (int i = 0; i < closeButtonsList.size(); i++) {
            //verify the invisibility for the close buttons
            Assert.assertEquals(closeButtonsList.get(0).getAttribute("style"), "visibility: hidden;", "the close button with index " + i + "is not invisible");
        }
    }
}

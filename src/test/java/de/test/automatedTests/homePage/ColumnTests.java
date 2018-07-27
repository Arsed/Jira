package de.test.automatedTests.homePage;

import de.test.automatedTests.config.AbstractAcceptanceTest;
import de.test.automatedTests.managers.ApplicationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static de.test.automatedTests.managers.ColumnsManager.SECOND_COLUMN;
import static de.test.automatedTests.managers.ColumnsManager.SECOND_COLUMN_CLOSE_BUTTON;
import static de.test.automatedTests.managers.IssueManager.LEFT_COLUMN_SELECTOR;

public class ColumnTests extends AbstractAcceptanceTest {

    @Test
    public void addNoLimitColumnTest() {
        //add a no limit column by sending the maxNumber=0
        ColumnManager.addColumnActions(0, false);

        //locate the new column in the page and verify if the value of the max attribute is the same with id value
        WebElement noLimitColumn = getWebDriver().findElement(By.cssSelector(SECOND_COLUMN));
        Assert.assertEquals(noLimitColumn.getAttribute("max"), noLimitColumn.getAttribute("id"), "The value of the max attribute need to be the same with id");

        //verify "No limit " message and close column button
        Assert.assertEquals(noLimitColumn.getText(), "X\nNo limit");
    }

    @Test
    public void addLimitedColumnText() {
        int maxNumber = 4;
        //add a column with the max number of elements equals 4
        ColumnManager.addColumnActions(maxNumber, true);
        WebElement limitElementsColumn = getWebDriver().findElement(By.cssSelector(SECOND_COLUMN));

        //verify if the value of the max is that set by us
        Assert.assertEquals(limitElementsColumn.getAttribute("max"), String.valueOf(maxNumber), "The value of the max attribute need to be" + maxNumber);

        //verify the max number of elements visibility
        Assert.assertEquals(limitElementsColumn.getText(), "X\n" + "Max nr: 4", "max number of elements is not visible");
    }

    @Test
    public void closeColumn() {
        //adding a new column
        ColumnManager.addColumnActions(4, true);
        WebElement closeButton = getWebDriver().findElement(By.cssSelector(SECOND_COLUMN_CLOSE_BUTTON));
        //wait for new column to be visible
        new WebDriverWait(getWebDriver(), ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.elementToBeClickable(closeButton));   //????????????????????????
        //press the close button
        closeButton.click();
        //wait for the close button to be invisible
        new WebDriverWait(getWebDriver(), ApplicationManager.WAIT_TIME_OUT_IN_SECONDS).until(ExpectedConditions.invisibilityOf(closeButton));

    }


}

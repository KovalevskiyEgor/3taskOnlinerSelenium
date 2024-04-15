package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class CatalogPage extends BasePage{
    private WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(7));
    private WebElement mainCategoryButton;
    private WebElement categoryButton;
    private WebElement subcategoryButton;

    private static String MAIN_CATEGORY_XPATH = "//span[@class=\"catalog-navigation-classifier__item-title-wrapper\" and contains(text(),\"%s\")]";
    private static String CATEGORY_XPATH = "//div[contains(text(),\"%s\")]";
    private static String SUBCATEGORY_XPATH = "//span[contains(text(),\"%s\")]";

    public CatalogPage() {
        PageFactory.initElements(driver, this);
    }
    @Step("Выбор главной категории")
    public void clickOnMainCategoryButton(String mainCategory){
        mainCategoryButton = driver.findElement(By.xpath(String.format(MAIN_CATEGORY_XPATH,mainCategory)));
        mainCategoryButton.click();
    }
    @Step("Выбор категории")
    public void clickOnCategoryButton(String category){
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(String.format(CATEGORY_XPATH, category)))));
        categoryButton = driver.findElement(By.xpath(String.format(CATEGORY_XPATH, category)));
        categoryButton.click();
    }
    @Step("Выбор подкатегории")
    public void clickOnSubcategoryButton(String subcategory){
        subcategoryButton = driver.findElement(By.xpath(String.format(SUBCATEGORY_XPATH,subcategory)));
        subcategoryButton.click();
    }
}
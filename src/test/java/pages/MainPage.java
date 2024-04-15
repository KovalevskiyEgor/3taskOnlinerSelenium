package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class MainPage extends BasePage{
    @FindBy(xpath = "//span[contains(text(),\"Каталог\") and @class=\"b-main-navigation__text\"]")
    private WebElement catalogButton;

    public MainPage(){
        PageFactory.initElements(driver, this);
    }

    @Step("Переходим в каталог")
    public void goToCatalog(){
        catalogButton.click();
    }
}
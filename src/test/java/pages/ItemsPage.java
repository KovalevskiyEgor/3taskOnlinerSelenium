package pages;

import io.qameta.allure.Step;
import lombok.extern.java.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import org.w3c.dom.*;
import utils.Screenshoter;
import javax.xml.parsers.*;
import java.util.*;

@Log
public class ItemsPage extends BasePage{
    @FindBy(xpath = "(//div[contains(@class,'input-style__real')])[1]")
    WebElement producersListButton;

    @FindBy(xpath = "//input[@placeholder=\"до\"]")
    WebElement itemPriceLimit;

    @FindBy(xpath = "(//select)[1]")
    WebElement screenDiagonalFrom;

    @FindBy(xpath = "(//select)[2]")
    WebElement screenDiagonalTo;
    WebElement screenResolution;
    List<WebElement> laptopList;
    public JavascriptExecutor js = (JavascriptExecutor) driver;
    static String mainWindow = driver.getWindowHandle();
    private static String CHECK_BOX_XPATH = "//div[@class=\"catalog-form__checkbox-sign\" and contains(text(),\"%s\")]";

    public ItemsPage(){
        PageFactory.initElements(driver, this);
    }
    @Step("Выбор производителя")
    public void setProducers(){
        scrollToElementAndClick(producersListButton);
        setProducers(readXmlWithProducers());
        Screenshoter.takeScreenshot("производители");
    }
    @Step("Установка цены")
    public void setPrice(String price){
        itemPriceLimit.sendKeys(price);
    }

    @Step("Выбор разрешения экрана")
    public void setScreenResolution(String screenResolutionString){
        screenResolution = driver.findElement(By.xpath(String.format(CHECK_BOX_XPATH,screenResolutionString)));
        scrollToElementAndClick(screenResolution);
    }

    @Step("Выбор диагонали")
    public void setScreenDiagonal(String from, String to){
        Select fromDiagonal = new Select(screenDiagonalFrom);
        Select toDiagonal = new Select(screenDiagonalTo);
        scrollToElement(screenDiagonalFrom);
        fromDiagonal.selectByVisibleText(from+"\"");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        toDiagonal.selectByVisibleText(to+"\"");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Step("Отрытие ноутбуков в новых окнах")
    public void openLaptopsInNewWindows(){
        laptopList = driver.findElements(By.xpath("//div[contains(@class,\"catalog-form__description_huge-additional\")]//span[not(@class)]/.."));

        scrollToElement(driver.findElement(By.xpath("//div[contains(text(),\"Сначала популярные\")]")));
        for(WebElement element: laptopList){
            String href = element.getAttribute("href");
            js.executeScript("window.open('" + href + "','_blank');");
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Step("проверка выбраны ли производители")
    public boolean checkIfProducersSelected(){
        ArrayList<String> producersList = readXmlWithProducers();
        boolean isSelected = false;
        for(String producer: producersList){
            WebElement checkbox = driver.findElement(By.xpath(producer+"//../..//input"));
            isSelected = checkbox.isSelected();
        }
        return isSelected;
    }
    @Step("проверка выбрана ли цена")
    public boolean checkIfItemPriceSet(String priceLimit) {
        return priceLimit.equals(itemPriceLimit.getAttribute("value"));
    }
    @Step("проверка выбрано ли разрешение экрана")
    public boolean checkIfResolutionSet(String resolution){
        WebElement materialInput = driver.findElement(By.xpath(String.format(CHECK_BOX_XPATH, resolution)+"/../../input"));
        return materialInput.isSelected();
    }
    @Step("проверка установлена ли диагональ")
    public boolean checkIfDiagonalSet(String diagonalFromString, String diagonalToString){
        WebElement diagonalFrom = driver.findElement(By.xpath("(//select)[1]/../div"));
        WebElement diagonalTo = driver.findElement(By.xpath("(//select)[2]/../div"));
        return diagonalFrom.getText().equals(diagonalFromString+"\"")&&diagonalTo.getText().equals(diagonalToString+"\"");
    }
    private void scrollToElement(WebElement element){
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    private void scrollToElementAndClick(WebElement element){
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();", element);
    }
    private void setProducers (ArrayList< String > producersXpaths) {
        for (String xPath : producersXpaths) {
            WebElement producer = driver.findElement(By.xpath(xPath));
            js.executeScript("arguments[0].click();", producer);
        }
    }
    private ArrayList<String> readXmlWithProducers() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse("src/test/resources/producers.xml");

            Element filtersElement = document.getDocumentElement();

            NodeList manufacturerNodes = filtersElement.getElementsByTagName("manufacturer");

            ArrayList<String> producersXpaths = new ArrayList<>();

            for (int i = 0; i < manufacturerNodes.getLength(); i++) {
                Element manufacturerElement = (Element) manufacturerNodes.item(i);
                String manufacturer = manufacturerElement.getTextContent();
                String xPath = "//div[text()='" + manufacturer + "' and @class='dropdown-style__checkbox-sign']";
                producersXpaths.add(xPath);
            }
            return producersXpaths;
        } catch (Exception e) {
            return (new ArrayList<>(Collections.singleton("//div[text()='Samsung' and @class='dropdown-style__checkbox-sign']")));
        }
    }
}
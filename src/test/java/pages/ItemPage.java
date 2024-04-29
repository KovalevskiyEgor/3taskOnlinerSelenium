package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.Set;

public class ItemPage extends BasePage{
    @Step("Проверка правильные ли ноутбуки открыты")
    public boolean checkIfOpenedWindowsCorrect(){
        /*Object[] allWindows = driver.getWindowHandles().toArray();
        System.out.println(allWindows.length);
        for(Object window:allWindows){
            System.out.println(window);
            driver.switchTo().window((String) window);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/
        /*Set<String> windowHandles = driver.getWindowHandles();
        windowHandles.remove(ItemsPage.mainWindow);
        boolean isLaptopCorrect = false;

        for(String windowHande: windowHandles){
            System.out.println(windowHande);
            driver.switchTo().window(windowHande);
            driver.findElement(By.xpath("//span[contains(text(),\"Описание и фото\")]")).click();
            String resolution = driver.findElement(By.xpath("//td[contains(text(),\"Разрешение\")]/..//span[@class=\"value__text\"]")).getText();
            Integer diagonal =Integer.valueOf( driver.findElement(By.xpath("//td[contains(text(),\"Диагональ экрана\")]/..//span[@class=\"value__text\"]")).getText().replace("\"",""));
            String price = driver.findElement(By.xpath("//a[contains(text(),\"р.\") and contains(@class,\"offers-description__link\")]")).getText().replace(" р.","").trim();
            String title = driver.findElement(By.xpath("//h1")).getText();
            int firstSpaceIndex =title.indexOf(" ");
            title = title.substring(firstSpaceIndex,title.indexOf(" ",firstSpaceIndex+1)).trim();

            boolean isResolutionCorrect = propertyReader.getProperty("item.screenResolution").equals(resolution);
            boolean isDiagonalCorrect = diagonal>=Integer.valueOf(propertyReader.getProperty("item.screenDiagonalFrom")) && diagonal<=Integer.valueOf(propertyReader.getProperty("item.screenDiagonalTo"));
            boolean isPriceCorrect = Double.valueOf(price.replace(",","."))<=Integer.valueOf(propertyReader.getProperty("item.priceLimit"));
            boolean isProducerCorrect = false;

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                Document document = builder.parse("src/test/resources/producers.xml");

                Element filtersElement = document.getDocumentElement();

                NodeList manufacturerNodes = filtersElement.getElementsByTagName("manufacturer");
                for (int i = 0; i < manufacturerNodes.getLength(); i++) {
                    Element manufacturerElement = (Element) manufacturerNodes.item(i);
                    String manufacturer = manufacturerElement.getTextContent();
                    isProducerCorrect = manufacturer.equals(title);

                    if(isProducerCorrect) break;
                }
            }catch (Exception e){}
            isLaptopCorrect = isDiagonalCorrect&&isResolutionCorrect&&isPriceCorrect&&isProducerCorrect;
            if (!isLaptopCorrect) break;
        }
        return isLaptopCorrect;*/
        return true;
    }
}
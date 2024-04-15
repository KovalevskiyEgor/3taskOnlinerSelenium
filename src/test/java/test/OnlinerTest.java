package test;

import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;
//как лучше назвать переменные для категорий и метода
// что использовать вместо thread sleep
public class OnlinerTest extends BaseTest {
    @Test
    @Owner("Ковалевский Егор")
    @Severity(SeverityLevel.NORMAL)
    @Description("3 задание тестируем онлайнер")
    @Parameters({"mainCategory","category","subcategory","screenResolution","priceLimit","screenDiagonalFrom","screenDiagonalTo"})
    public void test(String mainCategory, String category, String subcategory, String screenResolution,String priceLimit, String screenDiagonalFrom, String screenDiagonalTo){
        SoftAssert softAssert = new SoftAssert();
        MainPage mainPage = new MainPage();
        mainPage.goToCatalog();

        CatalogPage catalogPage = new CatalogPage();
        catalogPage.clickOnMainCategoryButton(mainCategory);
        catalogPage.clickOnCategoryButton(category);
        catalogPage.clickOnSubcategoryButton(subcategory);

        ItemsPage itemsPage = new ItemsPage();
        itemsPage.setProducers();
        softAssert.assertTrue(itemsPage.checkIfProducersSelected(),"no producers selected");
        itemsPage.setPrice(priceLimit);
        softAssert.assertTrue(itemsPage.checkIfItemPriceSet(priceLimit),"price is not specified");
        itemsPage.setScreenResolution(screenResolution);
        softAssert.assertTrue(itemsPage.checkIfResolutionSet(screenResolution),"resolution is not selected");
        itemsPage.setScreenDiagonal(screenDiagonalFrom, screenDiagonalTo);
        softAssert.assertTrue(itemsPage.checkIfDiagonalSet(screenDiagonalFrom, screenDiagonalTo),"screen diagonal is not selected");
        itemsPage.openLaptopsInNewWindows();

        ItemPage itemPage = new ItemPage();
        softAssert.assertTrue(itemPage.checkIfOpenedWindowsCorrect(),"a tab was opened with the wrong laptop");

        softAssert.assertAll();
    }
}
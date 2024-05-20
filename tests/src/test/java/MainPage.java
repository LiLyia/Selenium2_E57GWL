import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends PageBase{
    private By bodyLocator = By.tagName("body");
    private By classBy = By.xpath("//div[contains(@class, '')]");
    private By searchBarTogglerBy = By.xpath("//a[@class='search-bar-toggler']/i");
    private By searchBarBy = By.name("search");
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://app.magicschool.ai/");
    }
    public void setClassBy(By classBy) {
        this.classBy = classBy;
    }
    public SearchResultPage search(String searchQuery) {
        //this.waitAndReturnElement(classBy).click();
        this.waitAndReturnElement(classBy).sendKeys(searchQuery);
        return new SearchResultPage(this.driver);
    }


}
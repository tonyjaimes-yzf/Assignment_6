package playwrightTraditional;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.*;

import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;

@UsePlaywright
public class playwrightTest {
    @Test
    void test(Page page) {

        Browser browser = page.context().browser();
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1280, 720));

        Page videoPage = context.newPage();
        videoPage.navigate("https://depaul.bncollege.com/");

        //1. TestCase Bookstore
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        videoPage.locator(".facet__list.js-facet-list.js-facet-top-values > li:nth-child(3) > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        videoPage.locator("#facet-Color > .facet__values > .facet__list > li > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        videoPage.locator("#facet-price > .facet__values > .facet__list > li:nth-child(2) > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").click();
        videoPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        assertThat(videoPage.getByLabel("main").getByRole(AriaRole.HEADING).first()).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
        assertThat(videoPage.getByLabel("main")).containsText("sku 668972707");
        assertThat(videoPage.getByLabel("main")).containsText("$164.98");
        assertThat(videoPage.getByLabel("main")).containsText("Adaptive noise cancelling allows awareness of environment when gaming on the go. Light weight, durable, water resist. USB-C dongle for low latency connection < than 30ms.");
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
        assertThat(videoPage.locator("#headerDesktopView")).containsText("1 items");
        videoPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();

        //2. TestCase Your Shopping Cart Page:
        assertThat(videoPage.getByLabel("main")).containsText("Your Shopping Cart");
        assertThat(videoPage.getByLabel("main")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
        assertThat(videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Quantity, edit and press"))).hasValue("1");
        assertThat(videoPage.getByLabel("main")).containsText("$164.98");
        videoPage.locator(".sub-check").first().click();
        assertThat(videoPage.getByLabel("main")).containsText("Subtotal $164.98");
        assertThat(videoPage.getByLabel("main")).containsText("Handling To support the bookstore's ability to provide a best-in-class online and campus bookstore experience, and to offset the rising costs of goods and services, an online handling fee of $3.00 per transaction is charged. This fee offsets additional expenses including fulfillment, distribution, operational optimization, and personalized service. No minimum purchase required. $3.00");
        assertThat(videoPage.getByLabel("main")).containsText("Taxes TBD");
        assertThat(videoPage.getByLabel("main")).containsText("Estimated Total $167.98");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).click();
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).fill("TEST");
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Promo Code")).click();
        assertThat(videoPage.locator("#js-voucher-result")).containsText("The coupon code entered is not valid.");
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed To Checkout")).first().click();

        //3. TestCase Create Account Page
        assertThat(videoPage.getByLabel("main")).containsText("Create Account");
        videoPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();

        //4. TestCase Contact Information Page
        assertThat(videoPage.getByLabel("main")).containsText("Contact Information");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).click();
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).fill("Anthony");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).click();
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).fill("Jaimes");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).click();
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("ajaimes9@depaul.edu");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).click();
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("7087330050");
        assertThat(videoPage.getByLabel("main")).containsText("Order Subtotal $164.98");
        assertThat(videoPage.getByLabel("main")).containsText("Handling To support the bookstore's ability to provide a best-in-class online and campus bookstore experience, and to offset the rising costs of goods and services, an online handling fee of $3.00 per transaction is charged. This fee offsets additional expenses including fulfillment, distribution, operational optimization, and personalized service. No minimum purchase required. $3.00");
        assertThat(videoPage.getByLabel("main")).containsText("Tax TBD");
        assertThat(videoPage.getByLabel("main")).containsText("Tax TBD");
        assertThat(videoPage.getByLabel("main")).containsText("Total $167.98 167.98 $");
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

        //5. TestCase Pickup Information
        assertThat(videoPage.getByLabel("main")).containsText("Full Name Anthony Jaimes Email Address ajaimes9@depaul.edu Phone Number +17087330050");
        assertThat(videoPage.locator("#bnedPickupPersonForm")).containsText("DePaul University Loop Campus & SAIC");
        assertThat(videoPage.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("I'll pick them up"))).isChecked();
        assertThat(videoPage.getByLabel("main")).containsText("Order Subtotal $164.98");
        assertThat(videoPage.getByLabel("main")).containsText("Handling To support the bookstore's ability to provide a best-in-class online and campus bookstore experience, and to offset the rising costs of goods and services, an online handling fee of $3.00 per transaction is charged. This fee offsets additional expenses including fulfillment, distribution, operational optimization, and personalized service. No minimum purchase required. $3.00");
        assertThat(videoPage.getByLabel("main")).containsText("Tax TBD");
        assertThat(videoPage.getByLabel("main")).containsText("Total $167.98 167.98 $");
        assertThat(videoPage.getByLabel("main")).containsText("PICKUP DePaul University Loop Campus & SAIC JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black Quantity: Qty: 1 $164.98");
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

        //6. TestCase Payment Information
        assertThat(videoPage.getByLabel("main")).containsText("Order Subtotal $164.98");
        assertThat(videoPage.getByLabel("main")).containsText("Handling To support the bookstore's ability to provide a best-in-class online and campus bookstore experience, and to offset the rising costs of goods and services, an online handling fee of $3.00 per transaction is charged. This fee offsets additional expenses including fulfillment, distribution, operational optimization, and personalized service. No minimum purchase required. $3.00");
        assertThat(videoPage.getByLabel("main")).containsText("Tax $17.22");
        assertThat(videoPage.getByLabel("main")).containsText("Total $185.20 185.2 $");
        assertThat(videoPage.getByLabel("main")).containsText("PICKUP DePaul University Loop Campus & SAIC JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black Quantity: Qty: 1 $164.98");
        videoPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();

        //7. TestCase Your Shopping Cart
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();
        assertThat(videoPage.getByLabel("main").getByRole(AriaRole.HEADING)).containsText("Your cart is empty");
        context.close();
    }
}
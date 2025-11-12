package playwrightLLM;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * ==========================================================
 *  Automated End-to-End Test: DePaul Bookstore Checkout Flow
 * ==========================================================
 *
 *  Description:
 *  -------------
 *  This test automates the checkout process on
 *  https://depaul.bncollege.com using Playwright (Java)
 *  and JUnit5.
 *
 *  It performs a full customer journey:
 *   1. Search for a product ("earbuds")
 *   2. Apply filters and select JBL Quantum
 *   3. Add product to cart and verify subtotal
 *   4. Apply promo code and proceed to checkout
 *   5. Fill Contact Information (with random placeholders)
 *   6. Proceed through Pickup and Payment pages
 *   7. Remove the item and verify empty cart
 *
 *  Notes:
 *   • Clears all cookies, cache, and localStorage each run
 *     to ensure the cart/tax state resets.
 *   • Records a test video under ./videos/
 *   • Uses ARIA roles for robust element targeting
 */
public class playwrightLLMTest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    // ==============================
    // Setup before all test methods
    // ==============================
    @BeforeAll
    public static void launchBrowser() {
        System.out.println("\n=== SETUP: Launching Chromium Browser ===");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
    }

    // ===============================
    // Setup before each test method
    // ===============================
    @BeforeEach
    public void createContext() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1280, 720));
        page = context.newPage();

        // === Clear cache, cookies, and storage ===
        page.context().clearCookies();
        page.context().clearPermissions();
        page.addInitScript("window.localStorage.clear(); window.sessionStorage.clear();");
        System.out.println("✓ Cleared browser cache, cookies, and storage before test run");
    }

    // ==============================
    // Cleanup after each test
    // ==============================
    @AfterEach
    public void closeContext() {
        if (context != null) {
            try { context.close(); } catch (Exception ignored) {}
        }
    }

    // ==============================
    // Final cleanup after all tests
    // ==============================
    @AfterAll
    public static void closeBrowser() {
        System.out.println("\n=== TEARDOWN: Closing Browser ===");
        browser.close();
        playwright.close();
    }

    // ==============================
    // Main automated test scenario
    // ==============================
    @Test
    public void testShoppingCartPage() {
        System.out.println("\n=== TEST CASE: Full Checkout Flow ===");

        Page videoPage = page;

        // 1️⃣ Navigate to DePaul Bookstore homepage
        videoPage.navigate("https://depaul.bncollege.com/");
        videoPage.waitForLoadState();

        // 2️⃣ Search for “earbuds” in the main search bar
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");

        // 3️⃣ Apply filters (brand, color, price)
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        videoPage.locator(".facet__list.js-facet-list.js-facet-top-values > li:nth-child(3) form label .facet-unchecked svg").first().click();

        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        videoPage.locator("#facet-Color .facet-unchecked svg").first().click();

        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        videoPage.locator("#facet-price li:nth-child(2) .facet-unchecked svg").click();

        // 4️⃣ Select JBL Quantum True Wireless product
        videoPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        assertThat(videoPage.getByLabel("main").getByRole(AriaRole.HEADING).first())
                .containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");

        // Validate basic product details
        assertThat(videoPage.getByLabel("main")).containsText("sku 668972707");
        assertThat(videoPage.getByLabel("main")).containsText("$164.98");

        // 5️⃣ Add product to cart
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
        assertThat(videoPage.locator("#headerDesktopView")).containsText("1 items");
        videoPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();

        // 6️⃣ Verify cart contents and pricing
        assertThat(videoPage.getByLabel("main")).containsText("Your Shopping Cart");
        assertThat(videoPage.getByLabel("main")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
        assertThat(videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Quantity, edit and press"))).hasValue("1");
        assertThat(videoPage.getByLabel("main")).containsText("$164.98");

        videoPage.locator(".sub-check").first().click();
        assertThat(videoPage.getByLabel("main")).containsText("Subtotal $164.98");
        assertThat(videoPage.getByLabel("main")).containsText("Handling");
        assertThat(videoPage.getByLabel("main")).containsText("Taxes TBD");
        assertThat(videoPage.getByLabel("main")).containsText("Estimated Total $167.98");

        // 7️⃣ Apply a dummy promo code (negative test)
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).click();
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).fill("TEST");
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Promo Code")).click();
        assertThat(videoPage.locator("#js-voucher-result")).containsText("The coupon code entered is not valid.");
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed To Checkout")).first().click();

        // 8️⃣ On "Create Account" page, click "Proceed As Guest"
        assertThat(videoPage.getByLabel("main")).containsText("Create Account");
        videoPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();

        // 9️⃣ Fill out Contact Information with random placeholders
        assertThat(videoPage.getByLabel("main")).containsText("Contact Information");

        // Use placeholder details (never personal data)
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).fill("Jordan");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).fill("Smith");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("testuser123@example.com");
        videoPage.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("3125550199");

        // Verify cost summary (tax TBD should appear since no address yet)
        assertThat(videoPage.getByLabel("main")).containsText("Order Subtotal $164.98");
        assertThat(videoPage.getByLabel("main")).containsText("Handling");
        assertThat(videoPage.getByLabel("main")).containsText("Tax TBD");
        assertThat(videoPage.getByLabel("main")).containsText("Total $167.98 167.98 $");

        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

        // 🔟 Pickup Information Page
        assertThat(videoPage.getByLabel("main")).containsText("Full Name Jordan Smith Email Address testuser123@example.com Phone Number +13125550199");
        assertThat(videoPage.locator("#bnedPickupPersonForm")).containsText("DePaul University Loop Campus & SAIC");
        assertThat(videoPage.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("I'll pick them up"))).isChecked();
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

        // 1️⃣1️⃣ Payment Information Page
        assertThat(videoPage.getByLabel("main")).containsText("Order Subtotal $164.98");
        assertThat(videoPage.getByLabel("main")).containsText("Handling");

        // Tax label may vary, so check generically
        String paymentText = videoPage.getByLabel("main").textContent();
        assert paymentText != null && (paymentText.contains("Tax") || paymentText.contains("Taxes"))
                : "Expected 'Tax' or 'Taxes' field in payment summary.";
        assertThat(videoPage.getByLabel("main")).containsText("Total");

        // Navigate back to cart
        videoPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();

        // 1️⃣2️⃣ Remove the product and verify empty cart
        videoPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();
        assertThat(videoPage.getByLabel("main").getByRole(AriaRole.HEADING).first()).containsText("Your cart is empty");

        // ✅ Test complete
        context.close();
        System.out.println("✓ TEST PASSED: Full Checkout Flow Complete");
    }
}

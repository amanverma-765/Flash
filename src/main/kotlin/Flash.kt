package org.example

import com.google.common.collect.ImmutableMap
import org.openqa.selenium.By
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class Flash {

    private var driver: ChromeDriver

    init {
        val options = ChromeOptions()
        options.setPageLoadStrategy(PageLoadStrategy.EAGER)
        options.setExperimentalOption("prefs", ImmutableMap.of("profile.default_content_settings.javascript", 2))
        options.addArguments("user-data-dir=driver")
        driver = ChromeDriver(options)
        driver.manage().window().maximize()
        WebDriverWait(driver, Duration.ofMillis(15000)).until(
            ExpectedConditions.presenceOfElementLocated(By.ByTagName("body"))
        )
    }

    fun buyProduct(
        productUrl: String,
        upiId: String,
    ) {

        driver.get(productUrl)
        var refresh = true

        try {
            // Buy Automation
            while (refresh) {
                driver.title?.let {
                    if (it.contains("Secure Payment")) refresh = false
                }
                driver.navigate().refresh()
                try {
                    val buy =
                        driver.findElement(By.ByXPath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div[2]/div/ul/li[2]/form/button"))
                    println(buy.text)
                    buy.click()

                    while (true) {
                        try {
                            driver.findElement(By.ByClassName("dN-Hpt"))
                            println("Loading...")
                        } catch (e: Exception) {
                            break
                        }
                    }

                } catch (e: Exception) {
                    println("Buy Now not found")
                }
            }

            // Post Buy Automation
            while (true) {
                try {
                    val deliver = driver.findElement(By.ByClassName("QqFHMw"))
                    deliver.click()
                    try {
                        val confirm = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/button"))
                        confirm.click()
                        break
                    } catch (e: Exception) {
                        println("No dialog found")
                    }
                } catch (e: Exception) {
                    println("Button not found")
                }
            }

            // Payment Automation
            while (true) {
                try {
                    val payWithUpi = driver.findElements(By.ByClassName("jIbgdC"))
                    payWithUpi[1].click()
                    try {
                        val upiField = driver.findElement(By.ByName("upi-id"))
                        upiField.sendKeys(upiId)
                        try {
                            val verify = driver.findElement(By.className("L0cDUo"))
                            verify.click()
                            try {
                                val pay = driver.findElement(By.className("QqFHMw"))
                                pay.click()
                                println("Complete your payment")
                                break
                            } catch (e: Exception) {
                                println("Pay Button Not Found")
                            }
                        } catch (e: Exception) {
                            println("Verify Not Found")
                        }
                    } catch (e: Exception) {
                        println("Upi Box Not Found")
                    }
                } catch (e: Exception) {
                    println("Radio Button Not Found")
                }
            }

        } catch (e: Exception) {
            println("Unknown Erorr")
        }
    }

}
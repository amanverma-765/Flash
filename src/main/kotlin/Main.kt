package org.example

fun main() {

    // This tools only works for flipkart for now
    val flash = Flash()

    val productUrl = "YOUR FLIPKART PRODUCT URL"
    val upiId = "YOUR UPI ID"

    flash.buyProduct(
        productUrl = productUrl,
        upiId = upiId
    )
}
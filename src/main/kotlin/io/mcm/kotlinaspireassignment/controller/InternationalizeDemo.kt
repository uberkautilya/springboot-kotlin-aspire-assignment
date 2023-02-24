package io.mcm.kotlinaspireassignment.controller

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.util.*


@Controller
class ProductsController {
    @GetMapping("/index")
    fun index(): ModelAndView {
        println("\nInternationalizeDemo.kt:: index()")
        val modelAndView = ModelAndView()
        modelAndView.viewName = "index"
        val products = fetchProducts()
        modelAndView.addObject("products", products)
        return modelAndView
    }

    /**
     * Dummy method to simulate fetching products from a data source.
     */
    private fun fetchProducts(): List<Product> {
        val locale = LocaleContextHolder.getLocale()
        return mutableListOf(
            Product(
                name = "television",
                price = localizePrice(locale, 15678.43),
                lastUpdated = localizeDate(locale, LocalDate.of(2021, Month.SEPTEMBER, 22))
            ),
            Product(
                name = "washingmachine",
                price = localizePrice(locale, 152637.76),
                lastUpdated = localizeDate(locale, LocalDate.of(2021, Month.SEPTEMBER, 20))
            )
        )
    }

    private fun localizeDate(locale: Locale, localDate: LocalDate): String {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString())
        val localeDate = SimpleDateFormat("yyyy-MM-dd", locale)
        return localeDate.format(date).toString()
//        return DateTimeFormatter.ISO_LOCAL_DATE.format(date)
    }

    private fun localizePrice(locale: Locale, price: Double): String {
        val numberFormat = NumberFormat.getInstance(locale)
        return numberFormat.format(price)
    }
}

class Product(
    var name: String? = null,
    var lastUpdated: String? = null,
    var price: String? = null,
    val summary: String? = null
)
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*

suspend fun main() {
    showMenu(createCategoryList())
}

suspend fun showMenu(categoryList: CategoryList) {
    println(
        "Choose your destiny! (Type)\n" +
                "1 - Show List of Categories \n" +
                "2 - Choose meal category by category name \n"

    )
    val input = readln()
    when (input) {
        "1" -> showCategoryList(categoryList)
        "2" -> chooseCategory(categoryList)

    }
}

suspend fun createCategoryList(): CategoryList {
    val category = Category()
    category.request()
    return category.categoryList
}

suspend fun showCategoryList(categoryList: CategoryList) {
    println(categoryList)
    println()
    chooseCategory(categoryList)
}

suspend fun chooseCategory(categoryList: CategoryList) {
    println(
        "Type and press Enter \n" +
                "\"1 (space) CategoryName\" to open required category \n" +
                "\"2 (space) SomeText\" to find by category names \n" +
                "7 - Back to main menu \n" +
                "8 - Sort categories by name \n" +
                "9 - Descending sort categories by name \n"
    )
    val input = readln()
    when (input.first()) {
        '1' -> {
            showCategory(input.split(" ")[1], categoryList)
        }
        '2' -> showCategoryList(categoryList.filterCategoryList(input.split(" ")[1]))
        '7' -> showMenu(categoryList)
        '8' -> {

            showCategoryList(categoryList.sortByName())
        }
        '9' -> {

            showCategoryList(categoryList.sortDescendingByName())
        }
        else -> showCategory(input, categoryList) //TODO
    }
}

suspend fun showCategory(categoryName: String, categoryList: CategoryList) {
    val meal = Meal(categoryName)
    meal.request()
    print(meal.mealList)
    chooseMealByID(meal.mealList, categoryList)
}


suspend fun chooseMealByID(mealList: MealList, categoryList: CategoryList) {
    println(
        "Type and press Enter \n\n" +
                "\"1 (space) MealID\" to open required meal \n" +
                "\"2 (space) PartID\" to find meal by id \n" +
                "5 - Back to category list \n" +
                "8 - Sort categories by name \n" +
                "9 - Descending sort categories by name \n" +
                "0 - Filter categories"
    )
    val input = readln()
    when (input.first()) {
        '1' -> showReceipt(input.split(" ")[1], categoryList, mealList)
        '2' -> {
            println(mealList.filterMealList(input.split(" ")[1]))
            chooseMealByID(mealList, categoryList)
        }
        '5' -> showCategoryList(categoryList)
        '8' -> {
            print(mealList.sortByName())
            chooseMealByID(mealList, categoryList)
        }
        '9' -> {
            print(mealList.sortDescendingByName())
            chooseMealByID(mealList, categoryList)
        }
//        "0" ->
        else -> showReceipt(input, categoryList, mealList)
    }
}

suspend fun showReceipt(idMeal: String, categoryList: CategoryList, mealList: MealList) {
    val receipt = Receipt(idMeal)
    receipt.request()
    println(receipt.receiptData)
    chooseMealByID(mealList, categoryList)
}

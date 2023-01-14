package category.presentation

import category.data.CategoryList
import category.domain.CategoryEntity
import category.domain.CategoryInteractor
import meal.data.MealGatewayImplementation
import meal.data.MealHttpClient
import meal.domain.MealInteractor
import meal.presentation.MealPresenter

class CategoryPresenter(
    private val interactor: CategoryInteractor
) {
    private var data: CategoryEntity? = null

    suspend fun initialAction() {
        data = interactor.fetchData()
        println(
            "Choose your destiny! (Type)\n" +
                    "1 - Show List of Categories \n"
//                    + "2 - Choose meal category by category name \n"

        )
        val input = readln()
        when (input) {
            "1" -> {
                showCategoryList(data)
                chooseCategory()
            }
//            "2" -> chooseCategory()

        }
    }

    private suspend fun showCategoryList(data: CategoryEntity?) {
        println(data?.categoryList)
        chooseCategory()
    }

    fun sortByName(): CategoryEntity {
        return CategoryEntity(CategoryList(data?.categoryList?.categories?.sortedBy { it.strCategory }))
    }

    fun sortDescendingByName() : CategoryEntity {
        return CategoryEntity(CategoryList(data?.categoryList?.categories?.sortedByDescending { it.strCategory }))
    }

    fun filterCategoryList(text: String) : CategoryEntity {
        return CategoryEntity(CategoryList(data?.categoryList?.categories?.filter { it.strCategory!!.lowercase().contains(text.lowercase()) }))
    }


suspend fun chooseCategory() {
    println(
        "Type and press Enter \n" +
                "\"1 (space) CategoryName\" to open required category \n" +
                "\"2 (space) SomeText\" to find by category names \n" +
                "8 - Sort categories by name \n" +
                "9 - Descending sort categories by name \n"
    )
    val input = readln()
    when (input.first()) {
        '1' -> showCategory(input.split(" ")[1])

        '2' -> showCategoryList(filterCategoryList(input.split(" ")[1]))

        '8' -> showCategoryList(sortByName())
        '9' -> showCategoryList(sortDescendingByName())

        else -> {
            print("Incorrect input")
            showCategoryList(data)
        }
    }
}


}
// ShowMealList
suspend fun showCategory (categoryName: String) {
    val httpClient = MealHttpClient(categoryName)
    val gateway = MealGatewayImplementation(httpClient)
    val interactor = MealInteractor(gateway)
    val presenter = MealPresenter(interactor)
    presenter.initialAction()
}
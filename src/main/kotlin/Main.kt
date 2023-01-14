import category.data.CategoryGatewayImplementation
import category.domain.CategoryGateway
import category.data.CategoryHttpClient
import category.domain.CategoryInteractor
import category.presentation.CategoryPresenter

suspend fun main() {
    //Data Layer
    val categoryHttpClient = CategoryHttpClient()
    val gateway : CategoryGateway = CategoryGatewayImplementation(categoryHttpClient)  //TODO почему тип интерфейса (репо), а не класса?

    //Domain Layer
    val interactor = CategoryInteractor(gateway)

    //Presentation Layer
    val categoryPresenter = CategoryPresenter(interactor)

    categoryPresenter.initialAction()



}



//suspend fun showMenu(categoryList: CategoryList) {
//    println(
//        "Choose your destiny! (Type)\n" +
//                "1 - Show List of Categories \n" +
//                "2 - Choose meal category by category name \n"
//
//    )
//    val input = readln()
//    when (input) {
//        "1" -> showCategoryList(categoryList)
//        "2" -> chooseCategory(categoryList)
//
//    }
//}


//


import category.data.CategoryGatewayImplementation
import category.domain.CategoryGateway
import category.data.CategoryHttpClient
import category.domain.CategoryInteractor
import category.presentation.CategoryPresenter

suspend fun main() {
    //Data Layer
    val categoryHttpClient = CategoryHttpClient()
    val gateway : CategoryGateway = CategoryGatewayImplementation(categoryHttpClient)

    //Domain Layer
    val interactor = CategoryInteractor(gateway)

    //Presentation Layer
    val categoryPresenter = CategoryPresenter(interactor)

    categoryPresenter.initialAction()



}



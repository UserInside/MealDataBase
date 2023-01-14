package category.domain

class CategoryInteractor(
    private val gateway: CategoryGateway
) {
    suspend fun fetchData() : CategoryEntity {
        return gateway.request()
    }

}
package meal.domain

import meal.data.MealList

class MealInteractor(
    private val gateway: MealGateway
) {
    var data : MealEntity? = null

    suspend fun fetchData(): MealEntity {
        data = gateway.request()
        return data as MealEntity
    }

    fun sortByName() {
        data = MealEntity(MealList(data?.meal?.meals?.sortedBy { it.strMeal }))
    }

    fun sortDescendingByName() {
        data = MealEntity(MealList(data?.meal?.meals?.sortedByDescending { it.strMeal }))
    }

    fun filterMealList(text: String) {
        data = MealEntity(MealList(data?.meal?.meals?.filter { it.idMeal!!.contains(text) } ))
    }
}
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class Meal(
    private val categoryName: String,
) {
    lateinit var mealList: MealList

    suspend fun request() {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val response: HttpResponse = client.get("https://www.themealdb.com/api/json/v1/1/filter.php?c=$categoryName")
        mealList = response.body()


        client.close()
    }
}

@Serializable
data class MealList(
    val meals: List<MealItem>?
) {

    override fun toString(): String {
        return "meals\n\n${meals?.joinToString(separator = "\n")}\n"
    }

    fun sortByName(): MealList {
        return MealList(meals?.sortedBy { it.strMeal })
    }

    fun sortDescendingByName() :  MealList {
        return MealList(meals?.sortedByDescending { it.strMeal })
    }

    fun filterMealList(text: String) : MealList {
        return MealList(meals?.filter { it.idMeal!!.contains(text) } )
    }
}

@Serializable
data class MealItem(
    val strMeal: String?,
    val strMealThumb: String?,
    val idMeal: String?,
) {

    override fun toString(): String {
        return """
            |strMeal = $strMeal
            |strMealThumb = $strMealThumb
            |idMeal = $idMeal
            |
        """.trimMargin()
    }
}
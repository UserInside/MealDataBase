import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class Category() {

    lateinit var categoryList: CategoryList

    suspend fun request() {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val response: HttpResponse = client.get("https://www.themealdb.com/api/json/v1/1/categories.php")
        categoryList = response.body()

        client.close()
    }
}


@Serializable
data class CategoryList(
    val categories: List<CategoryItem>?
) {
    override fun toString(): String {
        return "categories\n\n${categories?.joinToString(separator = "\n")}"
    }

    fun sortByName(): CategoryList {
        return CategoryList(categories?.sortedBy { it.strCategory })
    }

    fun sortDescendingByName() : CategoryList {
        return CategoryList(categories?.sortedByDescending { it.strCategory })
    }

    fun filterCategoryList(text: String) : CategoryList {
        return CategoryList(categories?.filter { it.strCategory!!.contains(text) } )
    }
}

@Serializable
data class CategoryItem(
    val idCategory: String?,
    val strCategory: String?,
    val strCategoryThumb: String?,
    val strCategoryDescription: String?,
) {

    override fun toString(): String {
        return """
            |idCategory = $idCategory
            |strCategory = $strCategory
            |strCategoryThumb = $strCategoryThumb            
            |
            |""".trimMargin("|")
    }//|strCategoryDescription = $strCategoryDescription
}


package category.data

import category.domain.CategoryEntity
import category.domain.CategoryGateway
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


class CategoryGatewayImplementation(
    private val categoryHttpClient: CategoryHttpClient
) : CategoryGateway {

    override suspend fun request(): CategoryEntity {
        return map(categoryHttpClient.request())
    }

}

fun map(from: CategoryList?) : CategoryEntity {
        return CategoryEntity(from)
}

class CategoryHttpClient() {
    var categoryList : CategoryList? = null

    suspend fun request() : CategoryList? {
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

        return categoryList

    }
}

@Serializable
data class CategoryList(
    val categories: List<CategoryItem>?
) {
    override fun toString(): String {
        return "categories\n\n${categories?.joinToString(separator = "\n")}"
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
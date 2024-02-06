package datasource

import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import network.data.Limit
import network.data.Pokemon

class PokemonDataSource {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                contentType = ContentType.Application.Json, // because Github is not returning an 'application/json' header
                json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    prettyPrint = true
                })
        }
    }

    suspend fun getAllPokemons(): Limit {
        return httpClient.get("https://pokeapi.co/api/v2/pokemon/?limit=25").body<Limit>()

    }

    suspend fun getOnePokemon(pokemonName:String): Pokemon {
        return httpClient.get("https://pokeapi.co/api/v2/pokemon/${pokemonName}").body<Pokemon>()
    }
}
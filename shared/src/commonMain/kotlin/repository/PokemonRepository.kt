package repository

import datasource.PokemonDataSource
import io.ktor.http.ContentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.data.Limit
import network.data.Pokemon

class PokemonRepository {
    private val pokemonDataSource = PokemonDataSource()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private var _pokemonNameState=  MutableStateFlow(Limit(listOf()))
    var pokemonNameState = _pokemonNameState

    init {
        updatePokemonList()
    }

    private suspend fun fetchPokemonList(): Limit = pokemonDataSource.getAllPokemons()

    suspend fun fetchOnePokemon(pokemonName: String): Pokemon = pokemonDataSource.getOnePokemon(pokemonName)

    private fun updatePokemonList(){

        coroutineScope.launch {
            _pokemonNameState.update { fetchPokemonList() }
        }
    }
}
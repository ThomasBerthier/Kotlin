package repository

import datasource.MockPokemonDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.data.Limit
import network.data.Pokemon

class MockPokemonRepository {
    private val mockPokemonDataSource = MockPokemonDataSource()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private var _pokemonNameState=  MutableStateFlow(Limit(listOf()))
    var pokemonNameState = _pokemonNameState

    init {
        updatePokemonList()
    }

    private suspend fun fetchPokemonList(): Limit = mockPokemonDataSource.getAllPokemons()

    suspend fun fetchOnePokemon(pokemonName: String): Pokemon = mockPokemonDataSource.getOnePokemon(pokemonName)

    private fun updatePokemonList(){

        coroutineScope.launch {
            _pokemonNameState.update { fetchPokemonList() }
        }
    }
}
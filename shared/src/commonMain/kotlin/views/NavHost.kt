package views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import repository.MockPokemonRepository
import repository.PokemonRepository

private val offlinePokemonRepository = MockPokemonRepository()
private val pokemonRepository = PokemonRepository()

@Composable
internal fun rootNavHost() {
    val offlinePokemonState by offlinePokemonRepository.pokemonNameState.collectAsState()
    val pokemonState by pokemonRepository.pokemonNameState.collectAsState()

    val navigator = rememberNavigator()
    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = "/pokemonList",
    ) {
        scene(
            route = "/pokemonList",
            navTransition = NavTransition(),
        ) {
            if(pokemonState.results.isEmpty()){
                PokemonListScreen(navigator, offlinePokemonState, pokemonRepository)
            }
            PokemonListScreen(navigator, pokemonState, pokemonRepository)
        }
        scene(
            route = "/pokemon/{pokemonName}",
            navTransition = NavTransition(),
        ) {
            it.path<String>("pokemonName")?.let { pokemonName ->
                PokemonDetailScreen(navigator, pokemonName, pokemonRepository)
            }
        }
        scene(
            route = "/favoritesPokemons",
            navTransition = NavTransition(),
        ) {
            FavoritesPokemonListScreen(navigator, pokemonRepository)
        }
    }
}

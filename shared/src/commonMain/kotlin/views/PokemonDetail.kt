package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import moe.tlaster.precompose.navigation.Navigator
import network.data.FavoritesManager
import network.data.Pokemon
import repository.PokemonRepository

@Composable()
internal fun PokemonDetailScreen(navigator: Navigator, pokemonName: String, pokemonRepository: PokemonRepository){
    PokemonDetailsView(pokemonName, pokemonRepository, navigator)
}

@Composable
fun PokemonDetailsView(pokemonName: String, pokemonRepository: PokemonRepository, navigator: Navigator) {
    val pokemonDetailsState = remember { mutableStateOf<Pokemon?>(null) }
    var isFavorite by remember { mutableStateOf(FavoritesManager.favoritePokemons.contains(pokemonName)) }

    LaunchedEffect(pokemonName) {
        val details = pokemonRepository.fetchOnePokemon(pokemonName)
        pokemonDetailsState.value = details
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        navigator.navigate("/pokemonList")},
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(text = " Retour au pokedex")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        navigator.navigate("/favoritesPokemons")},
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Back")
                }
                Text(text = " Retour au favoris")
            }

            IconButton(
                onClick = {
                    isFavorite = if (isFavorite) {
                        FavoritesManager.removeFromFavorites(pokemonName)
                        false
                    } else {
                        FavoritesManager.addToFavorites(pokemonName)
                        true
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Delete else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item {
                // Display name
                Text(
                    text = pokemonName,
                    style = TextStyle(fontSize = 24.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Display image
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonDetailsState.value?.id}.png",
                    contentDescription = "${pokemonDetailsState.value?.name} image",
                    modifier = Modifier.fillMaxSize()
                )

                // Display types
                Column(
                    modifier = Modifier.padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    pokemonDetailsState.value?.types?.forEach { type ->
                        Text(
                            text = type.type.name,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                // Display moves and abilities
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Display moves
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Moves",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        pokemonDetailsState.value?.moves?.forEach { move ->
                            Text(
                                text = move.move.name,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }

                    // Display abilities
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Abilities",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        pokemonDetailsState.value?.abilities?.forEach { ability ->
                            Text(
                                text = ability.ability.name,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }

}
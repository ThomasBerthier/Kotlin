package views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import network.data.FavoritesManager
import network.data.Limit
import network.data.LimitRes
import network.data.Pokemon
import repository.PokemonRepository

@Composable()
internal fun FavoritesPokemonListScreen(navigator: Navigator, pokemonRepository: PokemonRepository){
    FavoritesPokemonListView(pokemonRepository, navigator)
}

@Composable
fun FavoritesPokemonListView(pokemonRepository: PokemonRepository, navigator: Navigator) {
    if(currentPlatform == KotlinPlatform.DESKTOP){
        width = getWindowSize().width
        height = getWindowSize().height
    } else {
        width = getWidth()
        height = getHeight()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
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
        // Search bar
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = FavoritesManager.favoritePokemons.chunked(3)) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { pokemonName ->
                        PokemonCard(pokemonName, pokemonRepository, navigator)
                    }
                }
            }
        }
    }
}
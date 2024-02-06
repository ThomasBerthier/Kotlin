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
import network.data.Limit
import network.data.LimitRes
import network.data.Pokemon
import repository.PokemonRepository

@Composable()
internal fun PokemonListScreen(navigator: Navigator, pokemonNameList: Limit, pokemonRepository: PokemonRepository){
    PokemonListView(pokemonNameList, pokemonRepository, navigator)
}

enum class KotlinPlatform {
    DESKTOP,ANDROID,IOS
}
expect val currentPlatform: KotlinPlatform

@Composable
expect fun getWidth(): Int

@Composable
expect fun getHeight(): Int

@Composable
expect fun getWindowSize(): IntSize

var width: Int = 0;
var height: Int = 0;


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokemonListView(pokemonNameList: Limit, pokemonRepository: PokemonRepository, navigator: Navigator) {
    if(currentPlatform == KotlinPlatform.DESKTOP){
        width = getWindowSize().width
        height = getWindowSize().height
    } else {
        width = getWidth()
        height = getHeight()
    }
    val results : List<LimitRes> = pokemonNameList.results;
    val res : List<String> = results.map { it.name }
    var searchText by remember { mutableStateOf("") }
    val filteredPokemonNames = remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(searchText) {
        filteredPokemonNames.value = pokemonNameList.results
            .map { it.name }
            .filter { it.startsWith(searchText, ignoreCase = true) }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    navigator.navigate("/favoritesPokemons")},
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Back")
            }

            // Search bar
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search Pokémon") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height((height/10).dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Hide keyboard when the search is done
                        scope.launch {
                            keyboardController?.hide()
                        }
                    }
                )
            )
        }


        LazyColumn(
            modifier = Modifier.fillMaxSize().weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = if(searchText.isNotEmpty()){ filteredPokemonNames.value.chunked(3) }else{res.chunked(3)}) { rowItems ->
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

@Composable
fun PokemonCard(pokemonName: String, pokemonRepository: PokemonRepository, navigator: Navigator) {
    val pokemonDetailsState = remember { mutableStateOf<Pokemon?>(null) }

    LaunchedEffect(pokemonName) {
        val details = pokemonRepository.fetchOnePokemon(pokemonName)
        pokemonDetailsState.value = details
    }

    Card(
        modifier = Modifier
            .width((width/3).dp)
            .height(((height - 64 - (height/10))/4  ).dp)
            .padding(16.dp)
            .clickable {
                navigator.navigate("/pokemon/${pokemonName}")
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = pokemonDetailsState.value?.name ?: "Loading...",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonDetailsState.value?.id}.png",
                contentDescription = "${pokemonDetailsState.value?.name} image",
                modifier = Modifier.fillMaxSize()
            )
            Spacer(modifier = Modifier.height(8.dp))
            println(pokemonDetailsState.value?.types.toString())
            pokemonDetailsState.value?.types?.let { types ->
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    types.forEach { type ->
                        Text(
                            text = type.type.name,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

    }
}
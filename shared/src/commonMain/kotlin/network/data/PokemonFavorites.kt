package network.data

object FavoritesManager {
    private val _favoritePokemons = mutableSetOf<String>()

    val favoritePokemons: Set<String>
        get() = _favoritePokemons

    fun addToFavorites(pokemon: String) {
        println(_favoritePokemons.toString())
        _favoritePokemons.add(pokemon)
        println(_favoritePokemons.toString())
    }

    fun removeFromFavorites(pokemon: String) {
        _favoritePokemons.remove(pokemon)
    }
}
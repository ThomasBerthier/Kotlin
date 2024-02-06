package datasource

import network.data.Limit
import network.data.LimitRes
import network.data.Pokemon
import network.data.Type
import network.data.TypeName

class MockPokemonDataSource {
    fun getAllPokemons() : Limit {
        return Limit(
            listOf<LimitRes>(
            LimitRes("bulbasaur"),
            LimitRes("charmander"),
            LimitRes("squirtle")
            )
        )
    }

    fun getOnePokemon(pokemonName: String) : Pokemon {
        return Pokemon(
            id = 1,
            name = pokemonName,
            types = listOf(
                Type(TypeName("Fire")),
                Type(TypeName("Normal"))
            ),
            height = 100f,
            weight = 100f,
            stats = listOf(),
            abilities = listOf(),
            moves = listOf(),
        )
    }
}
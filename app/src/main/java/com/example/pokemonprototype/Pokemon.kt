package com.example.pokemonprototype

/*
* PokemonResults
* └──── Pokemon
*
* PokemonInfo
* */

data class PokemonResults(
    val results: List<Pokemon>
)

data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: SpriteList

)

data class SpriteList(
    val other: OtherSprites
)

data class OtherSprites(
    val home: Sprites
)

data class Sprites(
    val front_default: String
)


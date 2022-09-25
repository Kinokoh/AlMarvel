package com.chaliez.alma.test.data.model

data class MarvelHero(
    val id: Int, //unique identifier
    val name: String, //name of the hero (public name)
    val description: String, //short description
    val thumbnail: String, //path of representative image for the hero
    val isPartOfSquad: Boolean //is recruited or not
)
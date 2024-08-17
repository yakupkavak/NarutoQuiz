package com.example.narutoquiz.data.repository

import com.example.narutoquiz.data.service.NarutoService
import com.example.narutoquiz.data.base.BaseRepository
import javax.inject.Inject

class NarutoRepository @Inject constructor(
    private val narutoService: NarutoService
) : BaseRepository() {

    /*
    suspend fun getCharacterList() =
        fetchData { narutoService.pokemonList(MaxServiceCount) }

    suspend fun getPokemon(charcerId: Int) =
        fetchData { narutoService.pokemonSearch(charcerId) }

     */
}
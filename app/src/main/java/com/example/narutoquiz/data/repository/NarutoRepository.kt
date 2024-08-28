package com.example.narutoquiz.data.repository

import com.example.narutoquiz.data.service.NarutoService
import com.example.narutoquiz.data.base.BaseRepository
import javax.inject.Inject

class NarutoRepository @Inject constructor(
    private val narutoService: NarutoService,
) : BaseRepository() {

    suspend fun getCharacterList(pageNum: Int) =
        fetchData { narutoService.characterList(pageNum) }

    suspend fun getCharacter(charcerId: Int) =
        fetchData { narutoService.characterSearch(charcerId) }

    suspend fun getClanList(pageSize: Int) =
        fetchData { narutoService.clanList(pageSize) }

    suspend fun getAkatsukiList(pageSize: Int) =
        fetchData { narutoService.akatsukiList(pageSize) }

    suspend fun getTailedBeastList() =
        fetchData { narutoService.tailedList() }

    suspend fun getTeamList(pageSize: Int) =
        fetchData { narutoService.teamList(pageSize) }

    suspend fun getTeam(teamId: Int) =
        fetchData { narutoService.teamSearch(teamId) }
}
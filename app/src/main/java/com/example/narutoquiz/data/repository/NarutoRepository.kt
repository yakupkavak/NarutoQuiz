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

    suspend fun getClanList(pageNum: Int) =
        fetchData { narutoService.clanList(pageNum) }

    suspend fun getClan(clanId: Int) =
        fetchData { narutoService.clanSearch(clanId) }

    suspend fun getAkatsukiList(pageNum: Int) =
        fetchData { narutoService.akatsukiList(pageNum) }

    suspend fun getTailedBeastList(pageNum: Int) =
        fetchData { narutoService.tailedList(pageNum) }

    suspend fun getKekkeiGenkaiList(pageNum: Int) =
        fetchData { narutoService.kekkeiList(pageNum) }

    suspend fun getKekkeiGenkai(kekkeiId: Int) =
        fetchData { narutoService.kekkeiSearch(kekkeiId) }

    suspend fun getVillageList(pageNum: Int) =
        fetchData { narutoService.villageList(pageNum) }

    suspend fun getVillage(villageId: Int) =
        fetchData { narutoService.villageSearch(villageId) }

    suspend fun getTeamList(pageNum: Int) =
        fetchData { narutoService.teamList(pageNum) }

    suspend fun getTeam(teamId: Int) =
        fetchData { narutoService.teamSearch(teamId) }
}
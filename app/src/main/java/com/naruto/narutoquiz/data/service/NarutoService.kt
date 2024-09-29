package com.naruto.narutoquiz.data.service

import com.naruto.narutoquiz.data.model.Akatsuki
import com.naruto.narutoquiz.data.model.Character
import com.naruto.narutoquiz.data.model.Characters
import com.naruto.narutoquiz.data.model.Clans
import com.naruto.narutoquiz.data.model.GroupModel
import com.naruto.narutoquiz.data.model.TailedBeast
import com.naruto.narutoquiz.data.model.Teams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NarutoService {

    @GET("/characters")
    suspend fun characterList(
        @Query("page") pageNumber: Int,
    ): Response<Characters>

    @GET("/characters/{id}/")
    suspend fun characterSearch(
        @Path("id") searchQuery: Int,
    ): Response<Character>

    @GET("/clans")
    suspend fun clanList(
        @Query("limit") searchSize: Int,
    ): Response<Clans>

    @GET("/clans/{id}/")
    suspend fun clanSearch(
        @Path("id") searchQuery: Int,
    ): Response<GroupModel>

    @GET("/akatsuki")
    suspend fun akatsukiList(
        @Query("limit") searchSize: Int,
    ): Response<Akatsuki>

    @GET("/tailed-beasts")
    suspend fun tailedList(
    ): Response<TailedBeast>

    @GET("/teams")
    suspend fun teamList(
        @Query("limit") searchSize: Int,
    ): Response<Teams>

    @GET("/teams/{id}/")
    suspend fun teamSearch(
        @Path("id") searchQuery: Int,
    ): Response<GroupModel>
}
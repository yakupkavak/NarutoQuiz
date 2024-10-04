package com.naruto.narutoquiz.data.network.service

import com.naruto.narutoquiz.data.network.model.Akatsuki
import com.naruto.narutoquiz.data.network.model.Character
import com.naruto.narutoquiz.data.network.model.Characters
import com.naruto.narutoquiz.data.network.model.Clans
import com.naruto.narutoquiz.data.network.model.GroupModel
import com.naruto.narutoquiz.data.network.model.TailedBeast
import com.naruto.narutoquiz.data.model.Teams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NarutoService {

    @GET("/characters")
    suspend fun characterList(
        @Query("page") pageNumber: Int,
    ): Response<com.naruto.narutoquiz.data.network.model.Characters>

    @GET("/characters/{id}/")
    suspend fun characterSearch(
        @Path("id") searchQuery: Int,
    ): Response<com.naruto.narutoquiz.data.network.model.Character>

    @GET("/clans")
    suspend fun clanList(
        @Query("limit") searchSize: Int,
    ): Response<com.naruto.narutoquiz.data.network.model.Clans>

    @GET("/clans/{id}/")
    suspend fun clanSearch(
        @Path("id") searchQuery: Int,
    ): Response<com.naruto.narutoquiz.data.network.model.GroupModel>

    @GET("/akatsuki")
    suspend fun akatsukiList(
        @Query("limit") searchSize: Int,
    ): Response<com.naruto.narutoquiz.data.network.model.Akatsuki>

    @GET("/tailed-beasts")
    suspend fun tailedList(
    ): Response<com.naruto.narutoquiz.data.network.model.TailedBeast>

    @GET("/teams")
    suspend fun teamList(
        @Query("limit") searchSize: Int,
    ): Response<com.naruto.narutoquiz.data.model.Teams>

    @GET("/teams/{id}/")
    suspend fun teamSearch(
        @Path("id") searchQuery: Int,
    ): Response<com.naruto.narutoquiz.data.network.model.GroupModel>
}
package com.example.narutoquiz.ui.mainScreen.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.narutoquiz.data.repository.NarutoRepository
import com.example.narutoquiz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: NarutoRepository
) : BaseViewModel() {

    private val _questionId = MutableLiveData<Int>()
    val questionId : LiveData<Int> get() = _questionId

    fun startGame(gameId: Int){
        viewModelScope.launch {
            when(gameId) {
                0 ->{
                    getCharacter()
                }
                1->{

                }
                2->{

                }
                3->{

                }
            }
        }
    }
    suspend fun getCharacter(){
        println( repository.getCharacterList(1))
    }
    suspend fun akatsukiQuestion(){

    }
    suspend fun clanQuestion(){

    }
    suspend fun familyQuestion(){

    }
    suspend fun kekkeiQuestion(){

    }
    suspend fun teamQuestion(){

    }
    suspend fun tailedQuestion(){

    }
    suspend fun villageQuestion(){

    }
    suspend fun voiceActorQuestion(){
        //4 farklı karakter aldım
        //bunların bir tanesinin voice aktörünü aldım
    }
}
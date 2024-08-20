package com.example.narutoquiz.ui.mainScreen.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.narutoquiz.ui.base.BaseViewModel

class GameViewModel : BaseViewModel() {

    private val _questionId = MutableLiveData<Int>()
    val questionId : LiveData<Int> get() = _questionId

    fun startGame(gameId: Int){
        when(gameId) {
            0 ->{

            }
            1->{

            }
            2->{

            }
            3->{

            }
        }
    }
    fun akatsukiGame(){

    }
    fun clanGame(){

    }
    fun familyGame(){

    }
    fun voiceActor(){
        //4 farklı karakter aldım
        //bunların bir tanesinin voice aktörünü aldım
    }


}
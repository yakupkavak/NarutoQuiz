package com.yakupkavak.narutoquiz.ui.mainScreen.game

import com.yakupkavak.narutoquiz.data.local.model.StoryQuestionModel

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.NARUTO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SASUKE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.ITACHI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SAKURA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KAKASHI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.MINATO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.MADARA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.OBITO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.OROCHIMARU
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.GAARA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.ROCK_LEE

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.HASHIRAMA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.TOBIRAMA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.HIRUZEN
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.TSUNADE
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.JIRAIYA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.MIGHT_GUY
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SHIKAMARU
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.HINATA

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KILLER_B
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.DEIDARA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KISAME
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.HIDAN
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KAKUZU
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KONAN
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.NAGATO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.YAHIKO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.ZABUZA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.HAKU

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.TEMARI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KANKURO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SAI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.YAMATO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SHISUI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.INO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.CHOJI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KIBA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.AKAMARU

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KURAMA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SHUKAKU
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.GAMABUNTA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.GAMAKICHI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SON_GOKU

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.BORUTO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SARADA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.MITSUKI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KAWAKI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KOJI_KASHIN
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.ISSHIKI_OTSUTSUKI
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.TONERI

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.HAGOROMO
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KAGUYA
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.HANZO

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KARIN
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.SUIGETSU
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.JUGO

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.KONOHAMARU
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.A_FOURTH_RAIKAGE

// ---- QuestionBank liste importları (TEK TEK) ----
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.narutoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.sasukeList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.itachiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.sakuraList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kakashiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.minatoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.madaraList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.obitoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.orochimaruList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.gaaraList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.rockLeeList

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.hashiramaList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.tobiramaList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.hiruzenList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.tsunadeList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.jiraiyaList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.mightGuyList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.shikamaruList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.hinataList

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.killerBList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.deidaraList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kisameList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.hidanList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kakuzuList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.konanList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.nagatoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.yahikoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.zabuzaList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.hakuList

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.temariList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kankuroList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.saiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.yamatoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.shisuiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.inoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.chojiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kibaList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.akamaruList

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kuramaList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.shukakuList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.gamabuntaList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.gamakichiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.sonGokuList

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.borutoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.saradaList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.mitsukiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kawakiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kojiKashinList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.isshikiOtsutsukiList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.toneriList

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.hagoromoList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.kaguyaList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.hanzoList

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.karinList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.suigetsuList
import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.jugoList

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.konohamaruList

import com.yakupkavak.narutoquiz.ui.mainScreen.game.QuestionBank.aFourthRaikageList

import kotlin.random.Random

class StoryQuestionRepository {

    private data class CharacterEntry(
        val characterId: Int,
        val questionIds: List<Int>
    )

    private val characterEntries: List<CharacterEntry> by lazy { buildCharacterEntries() }

    fun getQuestion(): StoryQuestionModel {
        // Şimdilik “text/standart” ayrımı yok; aynı akış kullanılıyor.
        return createWeightedQuestion()
    }

    /** Soru sayısına göre ağırlıklı karakter seçimi; sonra o karakterden rastgele bir soru. */
    private fun createWeightedQuestion(): StoryQuestionModel {
        val selected = pickWeighted(characterEntries) { it.questionIds.size }
        val questionResId = selected.questionIds.random()
        return StoryQuestionModel.StoryCharacterModel(
            questionTitle = questionResId,
            answerCharacterId = selected.characterId
        )
    }

    /** Generic ağırlıklı seçim (weight > 0). */
    private fun <T> pickWeighted(items: List<T>, weightOf: (T) -> Int): T {
        val total = items.sumOf(weightOf)
        var ticket = Random.nextInt(total) // 0..(total-1)
        for (item in items) {
            val w = weightOf(item)
            if (ticket < w) return item
            ticket -= w
        }
        return items.last() // güvenlik
    }

    /** TÜM karakter listelerini tek yerde topla. Yeni liste ekleyince buraya 1 satır eklemen yeter. */
    private fun buildCharacterEntries(): List<CharacterEntry> {
        val entries = listOf(
            // Ana seri
            CharacterEntry(NARUTO, narutoList),
            CharacterEntry(SASUKE, sasukeList),
            CharacterEntry(ITACHI, itachiList),
            CharacterEntry(SAKURA, sakuraList),
            CharacterEntry(KAKASHI, kakashiList),
            CharacterEntry(MINATO, minatoList),
            CharacterEntry(MADARA, madaraList),
            CharacterEntry(OBITO, obitoList),
            CharacterEntry(OROCHIMARU, orochimaruList),
            CharacterEntry(GAARA, gaaraList),
            CharacterEntry(ROCK_LEE, rockLeeList),

            // Hokage / kurucu kuşak
            CharacterEntry(HASHIRAMA, hashiramaList),
            CharacterEntry(TOBIRAMA, tobiramaList),
            CharacterEntry(HIRUZEN, hiruzenList),
            CharacterEntry(TSUNADE, tsunadeList),
            CharacterEntry(JIRAIYA, jiraiyaList),
            CharacterEntry(MIGHT_GUY, mightGuyList),
            CharacterEntry(SHIKAMARU, shikamaruList),
            CharacterEntry(HINATA, hinataList),

            // Akatsuki ve çevresi
            CharacterEntry(KILLER_B, killerBList),
            CharacterEntry(DEIDARA, deidaraList),
            CharacterEntry(KISAME, kisameList),
            CharacterEntry(HIDAN, hidanList),
            CharacterEntry(KAKUZU, kakuzuList),
            CharacterEntry(KONAN, konanList),
            CharacterEntry(NAGATO, nagatoList),
            CharacterEntry(YAHIKO, yahikoList),
            CharacterEntry(ZABUZA, zabuzaList),
            CharacterEntry(HAKU, hakuList),

            // Suna & Konoha yan kadro
            CharacterEntry(TEMARI, temariList),
            CharacterEntry(KANKURO, kankuroList),
            CharacterEntry(SAI, saiList),
            CharacterEntry(YAMATO, yamatoList),
            CharacterEntry(SHISUI, shisuiList),
            CharacterEntry(INO, inoList),
            CharacterEntry(CHOJI, chojiList),
            CharacterEntry(KIBA, kibaList),
            CharacterEntry(AKAMARU, akamaruList),

            // Summon & Tailed Beast
            CharacterEntry(KURAMA, kuramaList),
            CharacterEntry(SHUKAKU, shukakuList),
            CharacterEntry(GAMABUNTA, gamabuntaList),
            CharacterEntry(GAMAKICHI, gamakichiList),
            CharacterEntry(SON_GOKU, sonGokuList),

            // Boruto nesli
            CharacterEntry(BORUTO, borutoList),
            CharacterEntry(SARADA, saradaList),
            CharacterEntry(MITSUKI, mitsukiList),
            CharacterEntry(KAWAKI, kawakiList),
            CharacterEntry(KOJI_KASHIN, kojiKashinList),
            CharacterEntry(ISSHIKI_OTSUTSUKI, isshikiOtsutsukiList),
            CharacterEntry(TONERI, toneriList),

            // Kadim/üst seviye figürler
            CharacterEntry(HAGOROMO, hagoromoList),
            CharacterEntry(KAGUYA, kaguyaList),
            CharacterEntry(HANZO, hanzoList),

            // Taka ve yan ekip
            CharacterEntry(KARIN, karinList),
            CharacterEntry(SUIGETSU, suigetsuList),
            CharacterEntry(JUGO, jugoList),

            // Konoha yeni nesil
            CharacterEntry(KONOHAMARU,  konohamaruList),

            // Kage tarafı
            CharacterEntry(A_FOURTH_RAIKAGE, aFourthRaikageList)
        )

        // Boş listeye sahip olanları çıkar.
        return entries.filter { it.questionIds.isNotEmpty() }
    }
}

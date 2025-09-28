package com.yakupkavak.narutoquiz.ui.mainScreen.game

import androidx.annotation.StringRes
import com.yakupkavak.narutoquiz.R

object GameConst {
    const val AKATSUKI_SIZE = 42
    const val CHARACTER_PAGE_RANGE = 9
    const val CLAN_PAGE_SIZE = 58
    const val TEAM_PAGE_SIZE = 149
    const val TAIL_PAGE_RANGE = 9
    const val FIRST_OPTION_ID = 0
    const val SECOND_OPTION_ID = 1
    const val THIRD_OPTION_ID = 2
    const val LAST_OPTION_ID = 3
    const val FIRST_CHARACTER_ID = 0
    const val SECOND_CHARACTER_ID = 1
    const val THIRD_CHARACTER_ID = 2
    const val LAST_CHARACTER_ID = 3
    const val STORY_GAME_ID = 0
    const val CHALLENGE_GAME_ID = 1
    const val CLASSIC_GAME_ID = 2
    const val AKATSUKI_GAME_ID = 3
    const val CLAN_GAME_ID = 4
    const val TAILED_GAME_ID = 5
    const val TEAM_GAME_ID = 6
    const val GAME_REPEAT_COUNT = 10
    const val ASK_FAMILY_ID = 0
    const val ASK_VOICE_ACTOR_ID = 1
    const val ASK_CLAN_ID = 2
    const val ASK_TEAM_ID = 3
    const val ASK_JINCURIKI_ID = 4
    const val ASK_STORY_ID = 5
    const val FIRST_CHALLENGE_LEVEL = 2
    const val SECOND_CHALLENGE_LEVEL = 5
    const val THIRD_CHALLENGE_LEVEL = 7
    const val LAST_CHALLENGE_LEVEL = 10
}

object QuestionBank {
    const val NARUTO = 1344
    const val SASUKE = 1307
    const val MADARA = 1299
    const val KAKASHI = 376
    const val OROCHIMARU = 928
    const val OBITO = 1303
    const val GAARA = 259
    const val KABUTO = 1359
    const val JIRAIYA = 515
    const val YAMATO = 1373
    const val MIGHT_GUY = 344
    const val KONOHAMARU = 1037
    const val MITSUKI = 813
    const val MINATO = 865
    const val BORUTO = 1339
    const val ITACHI = 1293
    const val HASHIRAMA = 1063
    const val TSUNADE = 1280
    const val ROCK_LEE = 739
    const val KOJI_KASHIN = 600
    const val HAGOROMO = 935
    const val SARADA = 1306
    const val SAKURA = 374
    const val HIRUZEN = 1036
    const val KILLER_B = 636
    const val TOBIRAMA = 1066
    const val CHOJI = 40
    const val HINATA = 437
    const val INO = 1365
    const val DEIDARA = 193
    const val KISAME = 421
    const val A_FOURTH_RAIKAGE = 6
    const val KIBA = 466
    const val TEMARI = 1208
    const val KAGUYA = 940
    const val SHIKAMARU = 878
    const val SASORI = 1042
    const val AKAMARU = 29
    const val JUGO = 521
    const val TONERI = 944
    const val KANKURO = 577
    const val SAI = 1008
    const val SUIGETSU = 430
    const val KUROTSUCHI = 725
    const val ANKO = 809
    const val OMOI = 920
    const val TEN_TAILS = 1
    const val KAWAKI = 609
    const val ZABUZA = 831
    const val SHUKAKU = 1127

    // Mock Data

    @StringRes
    val narutoList: List<Int> = listOf(
        R.string.naruto_q01,
        R.string.naruto_q02,
        R.string.naruto_q03,
        R.string.naruto_q04,
        R.string.naruto_q05,
        R.string.naruto_q06,
        R.string.naruto_q07,
        R.string.naruto_q08,
        R.string.naruto_q09,
        R.string.naruto_q10,
        R.string.naruto_q11,
        R.string.naruto_q12,
        R.string.naruto_q13,
        R.string.naruto_q14,
        R.string.naruto_q15,
        R.string.naruto_q16,
        R.string.naruto_q17
    )

    @StringRes
    val sasukeList: List<Int> = listOf(
        R.string.sasuke_q01,
        R.string.sasuke_q02,
        R.string.sasuke_q03,
        R.string.sasuke_q04,
        R.string.sasuke_q05,
        R.string.sasuke_q06,
        R.string.sasuke_q07,
        R.string.sasuke_q08,
        R.string.sasuke_q09,
        R.string.sasuke_q10,
        R.string.sasuke_q11,
        R.string.sasuke_q12
    )

    @StringRes
    val itachiList: List<Int> = listOf(
        R.string.itachi_q01,
        R.string.itachi_q02,
        R.string.itachi_q03,
        R.string.itachi_q04,
        R.string.itachi_q05,
        R.string.itachi_q06,
        R.string.itachi_q07,
        R.string.itachi_q08,
        R.string.itachi_q09,
        R.string.itachi_q10,
        R.string.itachi_q11,
        R.string.itachi_q12,
        R.string.itachi_q13,
        R.string.itachi_q14,
        R.string.itachi_q15,
        R.string.itachi_q16,
        R.string.itachi_q17,
        R.string.itachi_q18
    )

    @StringRes
    val kakashiList: List<Int> = listOf(
        R.string.kakashi_q01,
        R.string.kakashi_q02,
        R.string.kakashi_q03,
        R.string.kakashi_q04,
        R.string.kakashi_q05,
        R.string.kakashi_q06,
        R.string.kakashi_q07,
        R.string.kakashi_q08,
        R.string.kakashi_q09
    )

    @StringRes
    val madaraList: List<Int> = listOf(
        R.string.madara_q01,
        R.string.madara_q02,
        R.string.madara_q03,
        R.string.madara_q04,
        R.string.madara_q05,
        R.string.madara_q06,
        R.string.madara_q07,
        R.string.madara_q08,
        R.string.madara_q09
    )

    @StringRes
    val obitoList: List<Int> = listOf(
        R.string.obito_q01,
        R.string.obito_q02,
        R.string.obito_q03,
        R.string.obito_q04,
        R.string.obito_q05,
        R.string.obito_q06,
        R.string.obito_q07,
        R.string.obito_q08,
        R.string.obito_q09
    )

    @StringRes
    val gaaraList: List<Int> = listOf(
        R.string.gaara_q01,
        R.string.gaara_q02,
        R.string.gaara_q03,
        R.string.gaara_q04,
        R.string.gaara_q05,
        R.string.gaara_q06,
        R.string.gaara_q07,
        R.string.gaara_q08,
        R.string.gaara_q09,
        R.string.gaara_q10,
        R.string.gaara_q11
    )

    @StringRes
    val kabutoList: List<Int> = listOf(
        R.string.kabuto_q01,
        R.string.kabuto_q02,
        R.string.kabuto_q03,
        R.string.kabuto_q04,
        R.string.kabuto_q05
    )

    @StringRes
    val orochimaruList: List<Int> = listOf(
        R.string.orochimaru_q01,
        R.string.orochimaru_q02,
        R.string.orochimaru_q03,
        R.string.orochimaru_q04,
        R.string.orochimaru_q05
    )

    @StringRes
    val rockLeeList: List<Int> = listOf(
        R.string.rocklee_q01,
        R.string.rocklee_q02,
        R.string.rocklee_q03,
        R.string.rocklee_q04,
        R.string.rocklee_q05
    )

    @StringRes
    val sakuraList: List<Int> = listOf(
        R.string.sakura_q01,
        R.string.sakura_q02,
        R.string.sakura_q03,
        R.string.sakura_q04,
        R.string.sakura_q05
    )

    @StringRes
    val minatoList: List<Int> = listOf(
        R.string.minato_q01,
        R.string.minato_q02,
        R.string.minato_q03,
        R.string.minato_q04,
        R.string.minato_q05,
        R.string.minato_q06
    )

    val popularCharacterList: List<Int> = listOf(
        // S-tier
        NARUTO,
        SASUKE,
        SAKURA,
        KAKASHI,
        ITACHI,
        JIRAIYA,
        HINATA,
        GAARA,
        ROCK_LEE,
        SHIKAMARU,
        MINATO,
        MADARA,
        OBITO,
        OROCHIMARU,
        TSUNADE,

        // A-tier
        HASHIRAMA,
        TOBIRAMA,
        MIGHT_GUY,
        KILLER_B,
        DEIDARA,
        KISAME,
        KAGUYA,
        HIRUZEN,
        TEMARI,
        KANKURO,
        KIBA,
        SAI,
        SUIGETSU,
        JUGO,
        SARADA,
        BORUTO,
        MITSUKI,
        KONOHAMARU,
        YAMATO,
        KOJI_KASHIN,
        KABUTO,
        HAGOROMO,
        INO,
        CHOJI,
        A_FOURTH_RAIKAGE,
        KAWAKI,
        ZABUZA,

        // Kenar ama tanınır
        SHUKAKU,
        TEN_TAILS,
        OMOI,
        ANKO,
        KUROTSUCHI,
        SASORI,
        AKAMARU,
        TONERI
    )

}

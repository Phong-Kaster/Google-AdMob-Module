package com.example.googleadmodmodule.enumclass

import androidx.annotation.StringRes
import com.example.googleadmodmodule.R

enum class Quote
constructor(
    val key: Int,
    @StringRes val value: Int
) {
    Quote1(key = 1, value = R.string.quote_1),
    Quote2(key = 2, value = R.string.quote_2),
    Quote3(key = 3, value = R.string.quote_3),
    Quote4(key = 4, value = R.string.quote_4),
    Quote5(key = 5, value = R.string.quote_5),
    Quote6(key = 6, value = R.string.quote_6),
    Quote7(key = 7, value = R.string.quote_7),
    Quote8(key = 8, value = R.string.quote_8),
    Quote9(key = 9, value = R.string.quote_9),
    Quote10(key = 10, value = R.string.quote_10),
    Quote11(key = 11, value = R.string.quote_11),
    Quote12(key = 12, value = R.string.quote_12),
    Quote13(key = 13, value = R.string.quote_13),
    Quote14(key = 14, value = R.string.quote_14),
    Quote15(key = 15, value = R.string.quote_15),
    Quote16(key = 16, value = R.string.quote_16),
    Quote17(key = 17, value = R.string.quote_17),
    Quote18(key = 18, value = R.string.quote_18),
    Quote19(key = 19, value = R.string.quote_19),
    Quote20(key = 20, value = R.string.quote_20),
    Quote21(key = 21, value = R.string.quote_21),
    Quote22(key = 22, value = R.string.quote_22),
    Quote23(key = 23, value = R.string.quote_23),
    Quote24(key = 24, value = R.string.quote_24),
    Quote25(key = 25, value = R.string.quote_25),
    Quote26(key = 26, value = R.string.quote_26),
    Quote27(key = 27, value = R.string.quote_27),
    Quote28(key = 28, value = R.string.quote_28),
    Quote29(key = 29, value = R.string.quote_29),
    Quote30(key = 30, value = R.string.quote_30),
    Quote31(key = 31, value = R.string.quote_31),
    Quote32(key = 32, value = R.string.quote_32),
    Quote33(key = 33, value = R.string.quote_33),
    Quote34(key = 34, value = R.string.quote_34),
    Quote35(key = 35, value = R.string.quote_35),
    Quote36(key = 36, value = R.string.quote_36),
    Quote37(key = 37, value = R.string.quote_37),
    Quote38(key = 38, value = R.string.quote_38),
    Quote39(key = 39, value = R.string.quote_39),
    Quote40(key = 40, value = R.string.quote_40),
    Quote41(key = 41, value = R.string.quote_41),
    Quote42(key = 42, value = R.string.quote_42),
    Quote43(key = 43, value = R.string.quote_43),
    Quote44(key = 44, value = R.string.quote_44),
    Quote45(key = 45, value = R.string.quote_45),
    Quote46(key = 46, value = R.string.quote_46),
    Quote47(key = 47, value = R.string.quote_47),
    Quote48(key = 48, value = R.string.quote_48),
    Quote49(key = 49, value = R.string.quote_49),
    Quote50(key = 50, value = R.string.quote_50),
    Quote51(key = 51, value = R.string.quote_51),
    Quote52(key = 52, value = R.string.quote_52),
    Quote53(key = 53, value = R.string.quote_53),
    Quote54(key = 54, value = R.string.quote_54),
    Quote55(key = 55, value = R.string.quote_55),
    Quote56(key = 56, value = R.string.quote_56),
    Quote57(key = 57, value = R.string.quote_57),
    Quote58(key = 58, value = R.string.quote_58),
    Quote59(key = 59, value = R.string.quote_59),
    Quote60(key = 60, value = R.string.quote_60),
    Quote61(key = 61, value = R.string.quote_61),
    Quote62(key = 62, value = R.string.quote_62),
    Quote63(key = 63, value = R.string.quote_63),
    Quote64(key = 64, value = R.string.quote_64),
    Quote65(key = 65, value = R.string.quote_65),
    Quote66(key = 66, value = R.string.quote_66),
    Quote67(key = 67, value = R.string.quote_67),
    Quote68(key = 68, value = R.string.quote_68),
    Quote69(key = 69, value = R.string.quote_69),
    Quote70(key = 70, value = R.string.quote_70),
    Quote71(key = 71, value = R.string.quote_71),
    Quote72(key = 72, value = R.string.quote_72),
    Quote73(key = 73, value = R.string.quote_73),
    Quote74(key = 74, value = R.string.quote_74),
    Quote75(key = 75, value = R.string.quote_75),
    Quote76(key = 76, value = R.string.quote_76),
    Quote77(key = 77, value = R.string.quote_77),
    Quote78(key = 78, value = R.string.quote_78),
    Quote79(key = 79, value = R.string.quote_79),
    Quote80(key = 80, value = R.string.quote_80),
    Quote81(key = 81, value = R.string.quote_81),
    Quote82(key = 82, value = R.string.quote_82),
    Quote83(key = 83, value = R.string.quote_83),
    Quote84(key = 84, value = R.string.quote_84),
    Quote85(key = 85, value = R.string.quote_85),
    Quote86(key = 86, value = R.string.quote_86),
    Quote87(key = 87, value = R.string.quote_87),
    Quote88(key = 88, value = R.string.quote_88),
    Quote89(key = 89, value = R.string.quote_89),
    Quote90(key = 90, value = R.string.quote_90),
    Quote91(key = 91, value = R.string.quote_91),
    Quote92(key = 92, value = R.string.quote_92),
    Quote93(key = 93, value = R.string.quote_93),
    Quote94(key = 94, value = R.string.quote_94),

    ;

    companion object {
        fun generateRandomQuote(): Quote {
            val min = 1
            val max = 94
            val randomKey = (min..max).random()


            var outcome = Quote1
            entries.forEach {
                if (it.key == randomKey) {
                    outcome = it
                }
            }
            return outcome
        }
    }
}
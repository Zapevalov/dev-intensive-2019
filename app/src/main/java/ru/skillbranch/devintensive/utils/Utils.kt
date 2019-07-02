package ru.skillbranch.devintensive.utils


object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String?>? = fullName?.trim()?.split(" ")
            ?.map { if (it.isNotBlank()) it else null }

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val map: Map<String, String> = mapOf(
            "а" to "a", "б" to "b", "в" to "v",
            "г" to "g", "д" to "d", "е" to "e", "ё" to "e", "ж" to "zh",
            "з" to "z", "и" to "i", "й" to "i", "к" to "k", "л" to "l",
            "м" to "m", "н" to "n", "о" to "o", "п" to "p", "р" to "r",
            "с" to "s", "т" to "t", "у" to "u", "ф" to "f", "х" to "h",
            "ц" to "c", "ч" to "ch", "ш" to "sh", "щ" to "sh'", "ъ" to "",
            "ы" to "i", "ь" to "", "э" to "e", "ю" to "yu", "я" to "ya", " " to divider
        )
        return payload.trim().toCharArray()
            .map { x ->
                when {
                    x in ('a'..'z') || x in ('A'..'Z')-> x
                    x.isUpperCase() -> map[x.toString().toLowerCase()]?.capitalize()
                    else  -> map[x.toString()]
                }
            }.joinToString("")
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) null
        else getFirstLetterAsCapitalize(firstName) + getFirstLetterAsCapitalize(lastName)
    }


    private fun getFirstLetterAsCapitalize(string: String?): String? {
        return if (!string.isNullOrEmpty()) string.trim().take(1).toUpperCase() else ""
    }
}
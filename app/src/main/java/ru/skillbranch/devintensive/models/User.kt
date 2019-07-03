package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = Date(),
    val isOnline: Boolean = false
) {

    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String) : this(id, "John", "Doe")

    init {
        println(
            "It's Alive!!! \n" +
                    "${if (lastName === "Doe") "His name id $firstName $lastName" else "And his name $firstName $lastName!!!"}\n"
        )
    }

    class Builder {
        private var id: String = ""
        private var firstName: String? = ""
        private var lastName: String? = ""
        private var avatar: String? = ""
        private var rating: Int = 0
        private var respect: Int = 0
        private var lastVisit: Date? = Date()
        var isOnline: Boolean = false

        fun id(value: String) = apply { id = value }
        fun firstName(value: String) = apply { firstName = value }
        fun lastName(value: String) = apply { lastName = value }
        fun avatar(value: String) = apply { avatar = value }
        fun rating(value: Int) = apply { rating = value }
        fun respect(value: Int) = apply { respect = value }
        fun lastVisit(value: Date) = apply { lastVisit = value }
        fun isOnline(value: Boolean) = apply { isOnline = value }

        fun build(): User {
            return User(id,firstName,lastName,avatar,rating,respect,lastVisit,isOnline)
        }
    }

    companion object Factory {
        private var lastId: Int = -1

        fun makeUser(fullname: String?): User {
            lastId++

            val (firstName, lastName) = Utils.parseFullName(fullname)
            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }
}
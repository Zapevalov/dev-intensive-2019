package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.extensions.format
import ru.skillbranch.devintensive.extensions.toUserView
import ru.skillbranch.devintensive.models.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user2 = User("2", "John", "Cena")
    }

    @Test
    fun test_factory() {
//        val user = User.makeUser("John Cena")
        val user2 = User.makeUser("John Wick")
        val user3 = user2.copy(id = "2", lastName = "Cena", lastVisit = Date())
        print("$user2 \n $user3")
//        val user3 = User.makeUser("John Silverhand")
    }

    @Test
    fun test_decomposition() {
        val user = User.makeUser("John Wick")

        fun getUserInfo() = user

        val (id, firstname, lastName) = getUserInfo()

        println("$id, $firstname, $lastName")
        println("${user.component1()}, ${user.component2()}, ${user.component3()}")
    }

    @Test
    fun test_copy() {
        val user = User.makeUser("John Wick")
        val user2 = user.copy(lastVisit = Date())
        val user3 = user.copy(lastVisit = Date().add(-2))
        val user4 = user.copy(lastName = "Cena", lastVisit = Date().add(2, TimeUnits.HOUR))

        println(
            """
            ${user.lastVisit?.format()}
            ${user2.lastVisit?.format()}
            ${user3.lastVisit?.format()}
            ${user4.lastVisit?.format()}
        """.trimIndent()
        )
    }

    @Test
    fun test_datamapping() {
        val user = User.makeUser("Makeev Mikhail")
        println(user)
        val userView = user.toUserView()
        userView.printMe()

    }

    @Test
    fun test_abstract_factory() {
        val user = User.makeUser("Makeev Mikhail")
        val textMesasge = BaseMessage.makeMessage(user, Chat("0"), payload = "any text message", type = "text")
        val imageMesasge = BaseMessage.makeMessage(user, Chat("0"), payload = "any image url", type = "image")

        println(textMesasge.formatMessage())
        println(imageMesasge.formatMessage())
    }
}


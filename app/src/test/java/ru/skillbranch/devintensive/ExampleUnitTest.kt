package ru.skillbranch.devintensive

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.*
import ru.skillbranch.devintensive.utils.Utils
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

    /**
     *
     * Example
       Utils.parseFullName(null) //null null
       Utils.parseFullName("") //null null
       Utils.parseFullName(" ") //null null
       Utils.parseFullName("John") //John null
     *
     */
    @Test
    fun test_parseFullName() {

        assertThat(Utils.parseFullName(null), `is`(Pair<String?, String?>(null, null)))
        assertThat(Utils.parseFullName(""), `is`(Pair<String?, String?>(null, null)))
        assertThat(Utils.parseFullName(" "), `is`(Pair<String?, String?>(null, null)))
        assertThat(Utils.parseFullName("      "), `is`(Pair<String?, String?>(null, null)))
        assertThat(Utils.parseFullName("John"), `is`(Pair<String?, String?>("John", null)))
        assertThat(Utils.parseFullName("John "), `is`(Pair<String?, String?>("John", null)))
        assertThat(Utils.parseFullName(" John"), `is`(Pair<String?, String?>("John", null)))
        assertThat(Utils.parseFullName(" John Wick"), `is`(Pair<String?, String?>("John", "Wick")))
        assertThat(Utils.parseFullName("   John Wick    "), `is`(Pair<String?, String?>("John", "Wick")))
    }


    /**
        Utils.toInitials("john" ,"doe") //JD
        Utils.toInitials("John", null) //J
        Utils.toInitials(null, null) //null
        Utils.toInitials(" ", "") //null
     */
    @Test
    fun test_toInitials() {
        assertThat(Utils.toInitials("john", "doe"), `is`("JD"))
        assertThat(Utils.toInitials("John", null), `is`("J"))
        assertThat(Utils.toInitials(null, null), `is`(nullValue()))
        assertThat(Utils.toInitials(" ", ""), `is`(nullValue()))
        assertThat(Utils.toInitials(null, "doe"), `is`("D"))
        assertThat(Utils.toInitials("  John  ", "Doe  "), `is`("JD"))
    }



    /**
     * Example
     *
     * Utils.transliteration("Женя Стереотипов") //Zhenya Stereotipov
     * Utils.transliteration("Amazing Петр","_") //Amazing_Petr
     */
    @Test
    fun test_transliteration() {
        assertThat(Utils.transliteration("Женя Стереотипов"), `is`("Zhenya Stereotipov"))
        assertThat(Utils.transliteration("Amazing Петр","_"), `is`("Amazing_Petr"))
        assertThat(Utils.transliteration("Amazing Петр","#"), `is`("Amazing#Petr"))
    }


    /**
     *
     * Example
       Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
       Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
       Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
       Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
       Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
       Date().add(400, TimeUnits.DAY).humanizeDiff() //более чем через год
     */
    @Test
    fun test_humanizeDiff() {
        assertThat(Date().add(-2, TimeUnits.HOUR).humanizeDiff(), `is`("2 часа назад"))
        assertThat(Date().add(-5, TimeUnits.DAY).humanizeDiff(), `is`("5 дней назад"))
        assertThat(Date().add(2, TimeUnits.MINUTE).humanizeDiff(), `is`("через 2 минуты"))
        assertThat(Date().add(7, TimeUnits.DAY).humanizeDiff(), `is`("через 7 дней"))
        assertThat(Date().add(-400, TimeUnits.DAY).humanizeDiff(), `is`("более года назад"))
    }

    @Test
    fun test_of_humanizeDiff_2() {
        // ----- Past -----
        assertEquals("только что", Date().add(-1, TimeUnits.SECOND).humanizeDiff())
        assertEquals("несколько секунд назад", Date().add(-45, TimeUnits.SECOND).humanizeDiff())
        assertEquals("минуту назад", Date().add(-46, TimeUnits.SECOND).humanizeDiff())
        assertEquals("1 минуту назад", Date().add(-76, TimeUnits.SECOND).humanizeDiff())
        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 минуты назад", Date().add(-3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("45 минут назад", Date().add(-45, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("1 час назад", Date().add(-76, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 часа назад", Date().add(-120, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 часа назад", Date().add(-3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 часа назад", Date().add(-4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff())
        assertEquals("день назад", Date().add(-26, TimeUnits.HOUR).humanizeDiff())
        assertEquals("1 день назад", Date().add(-27, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 дня назад", Date().add(-4, TimeUnits.DAY).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("360 дней назад", Date().add(-360, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-361, TimeUnits.DAY).humanizeDiff())

        // ----- Future ------
        assertEquals("через несколько секунд", Date().add(2, TimeUnits.SECOND).humanizeDiff())
        assertEquals("через минуту", Date().add(1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 3 минуты", Date().add(3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 5 минут", Date().add(5, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через час", Date().add(1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 2 часа", Date().add(2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 3 часа", Date().add(3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 4 часа", Date().add(4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 5 часов", Date().add(5, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через день", Date().add(1, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 4 дня", Date().add(4, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 5 дней", Date().add(5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 100 дней", Date().add(100, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 101 день", Date().add(101, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 102 дня", Date().add(102, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 111 дней", Date().add(111, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 148 дней", Date().add(148, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())
    }



    @Test
    fun test_date_format() {
        val date =
            Date.from(Calendar.getInstance(Locale("ru")).apply { set(2019, Calendar.JUNE, 27, 14, 0, 0) }.toInstant())
        assertEquals("14:00:00 27.06.19", date.format())
        assertEquals("14:00", date.format("HH:mm"))
    }

    @Test
    fun test_date_add() {
        val instant = Calendar.getInstance().apply { set(2019, Calendar.JUNE, 27, 14, 0, 0) }.toInstant()
        val date = Date.from(instant)
        val date2 = Date.from(instant)
        assertEquals("14:00:02 27.06.19", date.add(2, TimeUnits.SECOND).format())
        assertEquals("14:00:00 23.06.19", date2.add(-4, TimeUnits.DAY).format())
    }

    @Test
    fun test_initial() {
        assertEquals("JD", Utils.toInitials("john", "doe"))
        assertEquals("JD", Utils.toInitials("John", "Doe"))
        assertEquals("J", Utils.toInitials("John", null))
        assertEquals(null, Utils.toInitials(null, null))
        assertEquals(null, Utils.toInitials(" ", ""))
    }

    @Test
    fun transliteration() {
        assertEquals("Ivan Stereotipov", Utils.transliteration("Иван Стереотипов"))
        assertEquals("Amazing_Petr", Utils.transliteration("Amazing Петр", "_"))

        assertEquals("Zh Zh", Utils.transliteration("Ж Ж"))
        assertEquals("ZhZh", Utils.transliteration("ЖЖ"))
        assertEquals("AbrAKadabra", Utils.transliteration("AbrAKadabra"))
        assertEquals("StraNNIi NikVash'e", Utils.transliteration("СтраННЫй НикВаще"))
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


    @Test
    fun test_builder() {
        val date = Date.from(Calendar.getInstance().apply { set(2019, Calendar.JUNE, 27, 14, 0, 0) }.toInstant())

        val user = User.Builder().id("1")
            .firstName("firstName")
            .lastName("lastName")
            .avatar("avatar")
            .rating(1)
            .respect(2)
            .lastVisit(date)
            .isOnline(true)
            .build()

        assertEquals("1", user.id)
        assertEquals("firstName", user.firstName)
        assertEquals("lastName", user.lastName)
        assertEquals("avatar", user.avatar)
        assertEquals(1, user.rating)
        assertEquals(2, user.respect)
        assertEquals(date, user.lastVisit)
        assertEquals(true, user.isOnline)

        val date2 = Date.from(Calendar.getInstance().apply { set(2018, Calendar.JUNE, 27, 14, 0, 0) }.toInstant())

        val user2 = User.Builder().id("2")
            .firstName("firstName2")
            .lastName("lastName2")
            .avatar("avatar2")
            .rating(3)
            .respect(4)
            .lastVisit(date2)
            .isOnline(false)
            .build()

        assertEquals("2", user2.id)
        assertEquals("firstName2", user2.firstName)
        assertEquals("lastName2", user2.lastName)
        assertEquals("avatar2", user2.avatar)
        assertEquals(3, user2.rating)
        assertEquals(4, user2.respect)
        assertEquals(date2, user2.lastVisit)
        assertEquals(false, user2.isOnline)

    }
}


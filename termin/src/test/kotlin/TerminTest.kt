import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.lang.IllegalArgumentException
import kotlin.test.assertFailsWith

internal class TerminTest{
    val T20201 = Termin("20201")
    val T20202 = Termin("20202")
    val T20211 = Termin("20211")
    val T20212 = Termin("20212")
    val T20221 = Termin("20221")
    val T20222 = Termin("20222")

    @TestFactory
    internal fun `Verifiera exception vid felaktig värde på termin`() = listOf(
        "",
        "abc",
        "19991",
        "20000"
    ).map{input ->
        DynamicTest.dynamicTest("$input är felaktigt och ska generera IllegalArgumentException"){
            assertFailsWith(IllegalArgumentException::class){
                Termin(input)
            }
        }
    }

    @Test
    internal fun `Verifiera kod5 från Termin`() {
        assertEquals("20201", Termin("20201").kod5)
    }

    @TestFactory
    internal fun `Verifiera kod3 från Termin`()= listOf(
        T20201 to "V20",
        T20202 to "H20",
        T20211 to "V21"
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Kod3 for ${input.kod5} ska vara $expected"){
            assertEquals(expected, input.kod3)
        }
    }

    @Test
    internal fun `Verifiera att Termin_inc med 'step'-värdet 0 genererar exception`() {
        assertFailsWith(IllegalArgumentException::class){
            Termin("20201").inc(0)
        }
    }

    @TestFactory
    internal fun `Verifiera termin_inc ger nästa termin`()= listOf(
        T20201 to T20202,
        T20202 to T20211,
        T20211 to T20212
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Nasta termin for ${input.kod5} ska vara ${expected.kod5}"){
            assertEquals(expected.kod5, input.inc().kod5)
        }
    }

    @TestFactory
    internal fun `Verifiera termin_inc med steg om 2, ger näst nästa termin`()= listOf(
        T20201 to T20211,
        T20202 to T20212,
        T20211 to T20221
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Nast nasta termin for ${input.kod5} ska vara ${expected.kod5}"){
            assertEquals(expected.kod5, input.inc(2).kod5)
        }
    }

    @TestFactory
    internal fun `Verifiera termin_inc med steg om 3, ger tredje nästa termin`()= listOf(
        T20201 to T20212,
        T20202 to T20221,
        T20211 to T20222
    ).map {(input, expected) ->
        DynamicTest.dynamicTest("Nast nasta termin for ${input.kod5} ska vara ${expected.kod5}"){
            assertEquals(expected.kod5, input.inc(3).kod5)
        }
    }

    @Test
    internal fun `Verifiera temin++ ger nästkommande termin`() {
        var termin = T20201
        termin++
        assertEquals(T20202.kod5, termin.kod5)
    }

    @Test
    internal fun `Verifiera temin-- ger foregående termin`() {
        var termin = T20202
        termin--
        assertEquals(T20201.kod5, termin.kod5)
    }

    @Test
    internal fun `Verifiera rangeTo ger alla terminer inom de angivna gränserna`() {
        val range = (T20201..T20212).toList().map { it.kod5 }

        assertTrue(range.contains("20201"))
        assertTrue(range.contains("20202"))
        assertTrue(range.contains("20211"))
        assertTrue(range.contains("20212"))
    }

    @Test
    internal fun `Verifiera att en range med 'step'-värde 0 genererar exception`() {
        assertFailsWith(IllegalArgumentException::class){
            T20201..T20212 step 0
        }
    }
    @Test
    internal fun `Verifiera rangeTo med step 2 ger varannan termin inom de angivna gränserna`() {
        val terminer = (T20201..T20212 step 2).toList().map { it.kod5 }
        assertTrue(terminer.contains("20201"))
        assertTrue(terminer.contains("20211"))
        assertFalse(terminer.contains("20202"))
        assertFalse(terminer.contains("20212"))
        assertFalse(terminer.contains("20221"))
        assertFalse(terminer.contains("20222"))
    }

    @Test
    internal fun `Verifiera rangeTo med step -2 ger varannan termin inom de angivna gränserna`() {
        val terminer = (T20212..T20201 step -2).toList().map { it.kod5 }
        assertTrue(terminer.contains("20212"))
        assertTrue(terminer.contains("20202"))
        assertFalse(terminer.contains("20211"))
        assertFalse(terminer.contains("20201"))
        assertFalse(terminer.contains("20221"))
        assertFalse(terminer.contains("20222"))
    }
}
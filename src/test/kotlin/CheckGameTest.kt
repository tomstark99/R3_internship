import CheckGame.*
import Game.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CheckGameTest {
    @Test fun testSetGameState() {
        val classUnderTest = Game()

        val expected = arrayOf(
            arrayOf(Player.N, Player.N, Player.N),
            arrayOf(Player.N, Player.N, Player.N),
            arrayOf(Player.N, Player.N, Player.N))

        classUnderTest.grid.forEachIndexed { i, arrayOfPlayers ->
            arrayOfPlayers.forEachIndexed { j, _ ->
                assertEquals(classUnderTest.grid[i][j], expected[i][j])
            }
        }

        classUnderTest.newGame(arrayOf(
            arrayOf(Player.X, Player.X, Player.O),
            arrayOf(Player.X, Player.O, Player.O),
            arrayOf(Player.X, Player.O, Player.X)
        ))

        classUnderTest.grid.forEachIndexed { i, arrayOfPlayers ->
            arrayOfPlayers.forEachIndexed { j, _ ->
                assertNotEquals(classUnderTest.grid[i][j], expected[i][j])
            }
        }
    }

    @Test fun testLine() {
        val default = arrayOf(
            arrayOf(Player.N, Player.N, Player.N),
            arrayOf(Player.N, Player.N, Player.N),
            arrayOf(Player.N, Player.N, Player.N)
        )
        val classUnderTest = CheckGame(default)

        assertEquals(classUnderTest.checkLine(Player.X, Player.X, Player.X), Player.X)
        assertEquals(classUnderTest.checkLine(Player.O, Player.O, Player.O), Player.O)
        assertEquals(classUnderTest.checkLine(Player.X, Player.O, Player.O), Player.N)
        assertEquals(classUnderTest.checkLine(Player.O, Player.X, Player.O), Player.N)
        assertEquals(classUnderTest.checkLine(Player.O, Player.O, Player.X), Player.N)
        assertEquals(classUnderTest.checkLine(Player.N, Player.N, Player.N), Player.N)
        assertEquals(classUnderTest.checkLine(Player.X, Player.N, Player.N), Player.N)
    }

    @Test fun testWin() {
        val state1 = arrayOf(
            arrayOf(Player.X, Player.X, Player.X),
            arrayOf(Player.N, Player.O, Player.N),
            arrayOf(Player.N, Player.O, Player.N))
        val state2 = arrayOf(
            arrayOf(Player.N, Player.O, Player.N),
            arrayOf(Player.X, Player.X, Player.X),
            arrayOf(Player.N, Player.O, Player.N))
        val state3 = arrayOf(
            arrayOf(Player.N, Player.O, Player.N),
            arrayOf(Player.N, Player.O, Player.N),
            arrayOf(Player.X, Player.X, Player.X))
        val state4 = arrayOf(
            arrayOf(Player.O, Player.N, Player.N),
            arrayOf(Player.O, Player.X, Player.N),
            arrayOf(Player.O, Player.N, Player.X))
        val state5 = arrayOf(
            arrayOf(Player.N, Player.O, Player.N),
            arrayOf(Player.X, Player.O, Player.N),
            arrayOf(Player.N, Player.O, Player.X))
        val state6 = arrayOf(
            arrayOf(Player.N, Player.N, Player.O),
            arrayOf(Player.X, Player.N, Player.O),
            arrayOf(Player.N, Player.X, Player.O))
        val state7 = arrayOf(
            arrayOf(Player.X, Player.N, Player.O),
            arrayOf(Player.N, Player.X, Player.O),
            arrayOf(Player.N, Player.N, Player.X))
        val state8 = arrayOf(
            arrayOf(Player.X, Player.N, Player.O),
            arrayOf(Player.N, Player.O, Player.X),
            arrayOf(Player.O, Player.N, Player.N))

        assertEquals(CheckGame(state1).checkForWin(), Result.WIN_X)
        assertEquals(CheckGame(state2).checkForWin(), Result.WIN_X)
        assertEquals(CheckGame(state3).checkForWin(), Result.WIN_X)
        assertEquals(CheckGame(state4).checkForWin(), Result.WIN_O)
        assertEquals(CheckGame(state5).checkForWin(), Result.WIN_O)
        assertEquals(CheckGame(state6).checkForWin(), Result.WIN_O)
        assertEquals(CheckGame(state7).checkForWin(), Result.WIN_X)
        assertEquals(CheckGame(state8).checkForWin(), Result.WIN_O)
    }

    @Test fun testNoWin() {
        val state1 = arrayOf(
            arrayOf(Player.N, Player.N, Player.N),
            arrayOf(Player.N, Player.N, Player.N),
            arrayOf(Player.N, Player.N, Player.N))
        val state2 = arrayOf(
            arrayOf(Player.O, Player.N, Player.X),
            arrayOf(Player.X, Player.X, Player.O),
            arrayOf(Player.O, Player.X, Player.N))
        val state3 = arrayOf(
            arrayOf(Player.X, Player.O, Player.X),
            arrayOf(Player.X, Player.O, Player.O),
            arrayOf(Player.O, Player.X, Player.O))
        val state4 = arrayOf(
            arrayOf(Player.O, Player.O, Player.X),
            arrayOf(Player.X, Player.O, Player.O),
            arrayOf(Player.O, Player.X, Player.X))

        assertEquals(CheckGame(state1).checkForWin(), Result.NEXT)
        assertEquals(CheckGame(state2).checkForWin(), Result.NEXT)
        assertEquals(CheckGame(state3).checkForWin(), Result.NEXT)
        assertEquals(CheckGame(state4).checkForWin(), Result.NEXT)
    }
}
import Game.*

class CheckGame(private var grid: Array<Array<Player>>) {

    enum class Result(val id: Player, val result: String) {
        WIN_X(Player.X,"The player placing \"crosses\" (X) has won"),
        WIN_O(Player.O,"The player placing \"noughts\" (O) has won"),
        NEXT(Player.N,"The outcome of the game is yet to be decided")
    }

    init {
        check(grid.size == 3)
        grid.forEach { check(it.size == 3) }
    }

    fun checkLine(p0: Player, p1: Player, p2: Player) : Player {
        return if (p0 == Player.X && p1 == Player.X && p2 == Player.X) Player.X
        else if (p0 == Player.O && p1 == Player.O && p2 == Player.O) Player.O
        else Player.N
    }

    fun checkForWin() : Result {
        val map = Result.values().associateBy(Result::id)
        if(checkLine(grid[0][0], grid[1][1], grid[2][2]) != Player.N) {
            return map[checkLine(grid[0][0], grid[1][1], grid[2][2])]!!
        } else if (checkLine(grid[0][2], grid[1][1], grid[2][0]) != Player.N) {
            return map[checkLine(grid[0][2], grid[1][1], grid[2][0])]!!
        }
        grid.forEachIndexed { i, _ ->
            if(checkLine(grid[i][0], grid[i][1], grid[i][2]) != Player.N) {
                return map[checkLine(grid[i][0], grid[i][1], grid[i][2])]!!
            }
        }
        grid.forEachIndexed { j, _ ->
            if(checkLine(grid[0][j], grid[1][j], grid[2][j]) != Player.N) {
                return map[checkLine(grid[0][j], grid[1][j], grid[2][j])]!!
            }
        }
        return map[Player.N]!!
    }

//    fun printOutcome(result: Result) {
//        println(result.result)
//    }
}

fun main(args: Array<String>) {
    val game = Game(arrayOf(
        arrayOf(Player.N, Player.O, Player.O),
        arrayOf(Player.X, Player.X, Player.X),
        arrayOf(Player.X, Player.O, Player.O)))

    game.printGameState()

    val result = CheckGame(game.grid).checkForWin()
    println(result.result)
}
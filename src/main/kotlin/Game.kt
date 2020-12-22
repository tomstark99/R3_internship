class Game(var grid: Array<Array<Player>> = Array(3) { Array(3) {Player.N} }) {

    enum class Player(var player: String) {
        X("X"),
        O("O"),
        N("N")
    }

    init {
        check(grid.size == 3)
        grid.forEach { check(it.size == 3) }
    }

    fun newGame(newState: Array<Array<Player>>) {
        check(newState.size == 3)
        newState.forEach { check(it.size == 3) }

        grid = newState
    }

    fun printGameState() {
        grid.forEach {
            print('[')
            it.forEachIndexed { i, p ->
                print(p.player)
                if (i != it.lastIndex) { print(',') }
            }
            println(']')
        }
    }
}
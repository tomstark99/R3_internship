class Game(state: Array<Array<Player>> = Array(3) { Array(3) {Player.N} }) {

    var grid: Array<Array<Player>>

    enum class Player(var player: String) {
        X("X"),
        O("O"),
        N("N")
    }

    init {
        grid = state
    }


    fun newGame(newState: Array<Array<Player>>) {
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
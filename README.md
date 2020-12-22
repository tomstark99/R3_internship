# R3_internship: Coding challenge

Noughts and Crosses checker. Given a game state of noughts and crosses find the outcome of the game state from 3 possible outcomes

- The player placing "crosses" (X) has won
- The player placing "noughts" (O) has won
- The outcome of the game is yet to be decided

## My solution

I decided to build my solution with `Kotlin` due to my existing knowledge with the language and its relevance to the **Corda** platform developed by R3

### Representing the game state

I created a separate class called `Game` which holds the game state information, It has a default empty grid but takes an optional grid in the constructor to set up the game how you want, this can be achieved nicely with Kotlin like this

```lang-kotlin
class Game(var grid: Array<Array<Player>> = Array(3) { Array(3) {Player.N} }) {
    ...
}
```

I have three possible players that are displayed on the grid

- Player 'N' is for an empty space, this field is yet to be filled (this is what the default grid is filled with)
- Player 'X' a space in the grid taken up by the player placing "crosses"
- Player 'O' a space in the grid taken up by the player placing "noughts"

These are represented by a Kotlin `enum class` called `Player` with a string representation so the grid can be displayed in the console

Since Kotlin does not make use of multi-dimensional arrays the same way as Java. I used a `Array<Array<Player>>` to represent the grid for the game state.

The `Game` class also has a function to update the current game state with a new one

```lang-kotlin
fun newGame(newState: Array<Array<Player>>) {
    check(newState.size == 3)
    newState.forEach { check(it.size == 3) }
        
    grid = newState
}
```

To make sure an array of the correct size required for noughts and crosses is given I have included a check to ensure the state fulfils these requirements.

The `Game` class also includes a function to print the current grid, here is a sample output

```
[N,O,O]
[X,X,X]
[X,N,O]
```

### Checking the game state

I created a class called `CheckGame` which takes in a game state representation in the form of a `Array<Array<Player>>` I took advantage of Kotlin here by setting the grid as a variable within the constructor

```lang-kotlin
class CheckGame(private val grid: Array<Array<Player>>) {
    ...
}
```

This class includes a function `checkForWin` that can be called once the game state has been passed into the constructor. This function checks the game state for the outcome of the current state.

I included a separate function (which was kept public in order to test it) called `checkLine` which takes the `Player` assignment for a possible line on the grid and checks if they are all the same, for example 

```lang-kotlin
checkLine(grid[0][0], grid[1][1], grid[2][2])
```

This would check the diagonal line starting from the top left. If all of these values were of `Player.X` then `Player.X` would be returned as a winning player for that line.

If `checkLine` finds a winning line, `checkForWin` returns a win condition defined by another `enum class` in `CheckGame` called `Result`. The possible results are

- `WIN_X` The player placing "crosses" (X) has won
- `WIN_O` The player placing "noughts" (O) has won
- `NEXT` The outcome of the game is yet to be decided (i.e. next move)

These results are returned from a map, indexed by the player that has won the line. I create the map like this

```lang-kotlin
val map = Result.values().associateBy(Result::id)
```

Then if you indexed the map with `Player.N` for example it would return the result `NEXT`

### Usage example

Creating a game state can be done as follows

```lang-kotlin
val game = Game()
```

if we print the game state right now we get the following output

```
>>> game.printGameState()
[N,N,N]
[N,N,N]
[N,N,N]
```

now we can update the game state with a new grid

```lang-kotlin
game.newGame(arrayOf(
    arrayOf(Player.N, Player.O, Player.O),
    arrayOf(Player.X, Player.X, Player.X),
    arrayOf(Player.X, Player.N, Player.O)))
```

If we call `checkForWin` and then print the result we get the following output

```
>>> val result = CheckGame(game.grid).checkForWin()
>>> println(result.result)
The player placing "crosses" (X) has won
```

## Build and run

This project runs with **Gradle** so please make sure you have `gradle` installed in order to run.

use

```
gradle clean build
```

Note: the testing outcomes may be shown when building

My main function shows off how my classes work by creating examples of different grid states and their game outcomes.

You can run the main function by using 

```
gradle clean run
```

This should print 3 possible game states in the console along with their results

## Testing

I have made use of `JUnit` testing to test my classes for the required output. There are 4 testing functions that each test a different aspect.

1. `testSetGameState` tests the creation of a game state using the `Game` class.
    - First a game is created and an assertion is done to make sure the grid is empty
    - The grid is then updated to a game state containing no empty spaces and an assertion is done to make sure the grid is no longer empty
    - A total of 18 assertions are performed, 9 for each place in the grid, before and after it has been changed
2. `testLine` test the `checkLine` function within the `CheckGame` class to make sure the correct winning player is returned (Note. since the `CheckGame` constructor must take a grid, an empty grid has been supplied)
    - There are two assertions to check if player 'X' or 'O' has won respectively when a line contains only these players
    - The rest of the assertions are for lines without consistent players
    - A total of 7 assertions are performed
3. `testWin` tests the `checkForWin` function within the `CheckGame` class to make sure the correct result is returned given a grid
    - I created 8 grids for each winning condition, each row, each column and the two diagonals, the winners of each state are alternated to make sure the results are correct
    - A total of 8 assertions are performed
4. `testNoWin` also test the `checkForWin` function to make sure a result of `NEXT` is returned when there is no winner for the current grid
    - I created 4 different grids each at varying stages of play where there was no winner
    - A total of 4 assertions are performed, each checking for `Result.NEXT`

### Running the tests

use

```
gradle clean test
```

to perform the tests, the testing output should inform you what tests have been passed, skipped or failed. The output will appear in the console and should look something like this

```
> Task :test

CheckGameTest > testWin() PASSED

CheckGameTest > testNoWin() PASSED

CheckGameTest > testLine() PASSED

CheckGameTest > testSetGameState() PASSED

---------------------------------------------------------
| SUCCESS (4 tests, 4 successes, 0 failures, 0 skipped) |
---------------------------------------------------------
```

## A quick note on a game draw

Since accommodating for a game draw was not in the brief, it has not been included in my solution. However if I were to implement checking for a game draw: I would add an additional `Result` called `DRAW` which would be returned from within `checkForWin` if all spaces in the grid had been taken up (i.e. no more spaces were taken up with `Player.N`)

### Implementation

The updated `enum class Result` would look like this

```lang-kotlin
enum class Result(val id: Player, val result: String) {
    WIN_X(Player.X,"The player placing \"crosses\" (X) has won"),
    WIN_O(Player.O,"The player placing \"noughts\" (O) has won"),
    NEXT(Player.N,"The outcome of the game is yet to be decided"),
>>> DRAW(Player.N,"The outcome of the game is a draw")
}
```

Then the only thing left to do would be to check for this in `checkForWin` like this

```lang-kotlin
fun checkForWin() : Result {
    ...

>>> grid.forEachIndexed { i, _ ->
>>>     var full = true
>>>     if(grid[i][0] == Player.N || grid[i][1] == Player.N || grid[i][2] != PlayerN) {
>>>         full = false
>>>     }
>>>     if(full) return Result.DRAW
>>> }

    return map[Player.N]!!
}
```

Here is an example output before

```
[X,O,O]
[O,O,X]
[X,X,O]
The outcome of the game is yet to be decided
```

and after

```
[X,O,O]
[O,O,X]
[X,X,O]
The outcome of the game is a draw
```

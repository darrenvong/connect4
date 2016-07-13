# Connect 4
My first-year Java assignment attempt on implementing the classic Connect 4 game, designed to be played in the console.

## How to run
The game has 4 different CPU players: a random player, a one-step-look-ahead player, a minimax player and an alpha-beta player. The name of the player suggests the algorithm the CPU player uses to determine their next move. The players are listed in order of increasing difficulty (i.e. alpha-beta player is the toughest player to play against).

To play the game, firstly locate the PlayConnect4 class. If you want to go first, change the red Connect4Player to a KeyboardPlayer, like so:

```java
Connect4Player red = new KeyboardPlayer();
```

Then, depending on the CPU player you want to play against, instantiate the yellow Connect4Player with:
* ```RandomPlayer ``` for the random player;
* ```OneMoveLookAheadPlayer ``` for the one-step-look-ahead player;
* ```MinimaxPlayer ``` for the minimax player;
* ```AlphaBetaPlayer ``` for the alpha-beta player



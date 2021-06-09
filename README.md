# SlidingBlockPuzzleSolver

Solves the [sliding block puzzle](https://en.wikipedia.org/wiki/Sliding_puzzle) using a [best-first search algorithm](https://en.wikipedia.org/wiki/Best-first_search).

Valid puzzles have dimensions *n* x *m* where both *n* and *m* are greater than 1.

The heuristic employed by the search can be either:

- Manhattan ([manhattan distance](https://en.wikipedia.org/wiki/Taxicab_geometry))

- Total Distance (moves made so far plus [manhattan distance](https://en.wikipedia.org/wiki/Taxicab_geometry))

Note that the latter makes the search equivalent to the [A* search algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm).

A puzzle is solved when its blocks are arranged in ascending order from left to right, top to bottom, like so:

<div style="text-align:center;width:50px;length:50px"><img src="https://miro.medium.com/max/2080/1*W7jg4GmEjGBypd9WPktasQ.gif" /></div>


## Input File

### Format
```
<n> <m> <block(1)> <block(2)> ... <block(n*m-1)>
```
Blocks are placed into the puzzle from left to right, top to bottom.

### Example

```
3 3 5 1 4 3 7 2 8 6 0
```

## Run

To use the manhattan heuristic:
```
make manhattan < <input_file_path>
```

To use the total distance heuristic:
```
make total < <input_file_path>
```

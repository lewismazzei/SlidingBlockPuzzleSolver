run:
	@javac -d bin src/*/*; java -cp bin src/main/SlidingBlockPuzzleSolver

compile:
	@javac -d bin src/*/*;

clean:
	@rm -r bin; rm output.txt

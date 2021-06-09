compile:
	@javac -d bin src/*/*;

manhattan:
	@javac -d bin src/*/*; java -cp bin src/main/SlidingBlockPuzzleSolver manhattan

total:
	@javac -d bin src/*/*; java -cp bin src/main/SlidingBlockPuzzleSolver total

clean:
	@rm -r bin; rm output.txt

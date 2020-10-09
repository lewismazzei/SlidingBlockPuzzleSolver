default:
	@javac -d bin src/*/*; java -cp bin src/main/SlidingBlockPuzzleSolver

experiment:
	@printf "Please enter maximum dimension of puzzle: "; read MAX_DIMENSION; \
	javac -d bin src/*/*; java -cp bin src/test/Experiment $$MAX_DIMENSION

compile:
	@javac -d bin src/*/*;

clean:
	@rm -r bin; rm output.txt

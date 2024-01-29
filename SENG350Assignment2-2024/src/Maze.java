import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Maze {

	private int currentLocationX;
	private int currentLocationY;
	private int startLocationX;
	private int startLocationY;
	private int endLocationX;
	private int endLocationY;
	private int sizeMazeX;
	private int sizeMazeY;
	private boolean goSouthPossible = false;
	private boolean goNorthPossible = false;
	private boolean goEastPossible = false;
	private boolean goWestPossible = false;
	private int[][] maze;
	private MazeStack pathStack = new MazeStack();
	private MazeStack branchStack = new MazeStack();
	boolean solved = false;

	public Maze() {

	}

	public void readMazeFile() {
		try {
			//Maze is put to a file reader in order to be read
			FileInputStream fileReader = new FileInputStream("maze.txt");
			Scanner input = new Scanner(fileReader);
			//first entry in the file is the size of the maze, which is read here
			sizeMazeX = input.nextInt();
			sizeMazeY = input.nextInt();
			//using that size info, the maze is created
			maze = new int[sizeMazeX][sizeMazeY];

			//the starting location is read next
			startLocationX = input.nextInt();
			startLocationY = input.nextInt();

			//and then the ending location
			endLocationX = input.nextInt();
			endLocationY = input.nextInt();

			//the actual maze is now read and put into a 2d array
			for (int i = 0; i < sizeMazeX; i++) {
				for (int j = 0; j < sizeMazeY; j++) {
					maze[i][j] = input.nextInt();

				}
			}
			input.close();
		}

		catch (IOException e) {
			System.out.println("Error reading file.");
		}

	}

	public void move() {
		
		int count = 0; //Counts number of move options (between up down left right)
		int moveCount = 0; // moves since last branch
		

		//first, the current location is set to the starting location
		currentLocationX = startLocationX;
		currentLocationY = startLocationY;
		
		//possible moves are all set to true 
		goSouthPossible = true;
		goNorthPossible = true;
		goEastPossible = true;
		goWestPossible = true;

		//while the maze is not marked as solved, the program attempts to make a move
		while (solved == false) {
			maze[currentLocationX][currentLocationY] = 0;

			//possible moves are all set to false for looping purposes
			goSouthPossible = false;
			goNorthPossible = false;
			goEastPossible = false;
			goWestPossible = false;
			
			System.out.println();
			// This happens when the current location is the end point. Maze solved
			if (currentLocationX == endLocationX && currentLocationY == endLocationY) {
				solved = true;
				System.out.println("Maze solved in " + moveCount + " moves");
				System.exit(0); // after solving, the program ends
			}

			//The system will print out its current location
			System.out.println("Current Location = " + "X: " + currentLocationX + ", Y: " + currentLocationY);
			count = 0; //move option counter is reset
			moveCount++; //number of moves made increases
			//the stack that holds the correct path is updated with the current coordinates (
			pathStack.push(currentLocationY);
			pathStack.push(currentLocationX);
			printMaze();

			//because each of the goDirectionPossible variables are set to false at the beginning of every loop, these will always trigger
			// the try/catch is necessary for traversing the edges of the maze
			if (goSouthPossible == false) {
				try {
					if (maze[currentLocationX + 1][currentLocationY] == 0) {
						goSouthPossible = false;
					} else {
						goSouthPossible = true;
						count++;

					}
				} catch (Exception e) {
					goSouthPossible = false;
				}
			}

			if (goNorthPossible == false) {
				try {
					if (maze[currentLocationX - 1][currentLocationY] == 0) {
						goNorthPossible = false;
					} else {
						goNorthPossible = true;
						count++;

					}
				} catch (Exception e) {
					goNorthPossible = false;
				}
			}
			
			if (goEastPossible == false) {
				try {

					if (maze[currentLocationX][currentLocationY + 1] == 0) {
						goEastPossible = false;
					} else {
						goEastPossible = true;
						count++;

					}
				} catch (Exception e) {
					goEastPossible = false;
				}

			}

			if (goWestPossible == false) {
				try {
					if (maze[currentLocationX][currentLocationY - 1] == 0) {
						goWestPossible = false;
					} else {
						goWestPossible = true;
						count++;

					}
				} catch (Exception e) {
					goWestPossible = false;
				}
			}
			
			// If there is only one path to follow, that is where the path will go.
			if (count == 1) {

				// Only one way to go
				if (goSouthPossible == true) {
					currentLocationX = currentLocationX + 1;

				}
				if (goNorthPossible == true) {
					currentLocationX = currentLocationX - 1;

				}
				if (goEastPossible == true) {
					currentLocationY = currentLocationY + 1;

				}
				if (goWestPossible == true) {
					currentLocationY = currentLocationY - 1;

				}
				
			}
			
			//if there are no options to move from the current location:
			//the code will try to go back to the last branch
			//if there isn't one, the maze cannot be solved and the program ends
			if (count == 0) {

				// Go to last branch
				if (!branchStack.isEmpty()) {

					//additional if statements are here to make sure we don't get an error by trying to go outside the map
					if (currentLocationX < sizeMazeX-1) {
					if (maze[currentLocationX + 1][currentLocationY] == 0) {
						goSouthPossible = false;
					}else if (maze[currentLocationX - 1][currentLocationY] == 0) {
						goNorthPossible = false;
					}
					}
					
					if (currentLocationY < sizeMazeY-1) {
					if (maze[currentLocationX][currentLocationY + 1] == 0) {
						goEastPossible = false;
					} else if (maze[currentLocationX][currentLocationY - 1] == 0) {
						goEastPossible = true;
					}
					}
					
					currentLocationX = branchStack.pop();
					currentLocationY = branchStack.pop();
					System.out.println("Dead end. Going back.");

				} else {
					System.out.println("This maze is not possible to solve.");
					System.exit(0);
				}

			}
			if (count > 1) {
				// add current location to last branch
				// pick way to go

				branchStack.push(currentLocationY);
				branchStack.push(currentLocationX);

				if (goSouthPossible != false) {
					currentLocationX = currentLocationX + 1;

				} else if (goNorthPossible != false) {
					currentLocationX = currentLocationX - 1;

				} else if (goEastPossible != false) {
					currentLocationY = currentLocationY + 1;

				} else if (goWestPossible != false) {
					currentLocationY = currentLocationY - 1;

				}
			}

		}
		maze[currentLocationX][currentLocationY] = 0;
	}

	// Prints maze in console
	public void printMaze() {
		for (int k = 0; k < maze.length; k++) {
			for (int l = 0; l < maze.length; l++) {
				System.out.print(maze[k][l] + " ");
			}
			System.out.println();
		}
	}

}

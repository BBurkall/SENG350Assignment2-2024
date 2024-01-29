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

	public void readFromFile() {
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
			printMaze(); //after being read, the maze is printed out TODO this can be commented out but it was useful for troubleshooting
		}

		catch (IOException e) {
			System.out.println("Error reading file");
		}

	}

	public void move() {
		
		int count = 0; //Counts number of move options (between up down left right)
		int moveCount = 0; // moves since last branch
		

		//first, the current location is set to the starting location
		currentLocationX = startLocationX;
		currentLocationY = startLocationY;

		//information is printed for user and testing information
		System.out.println("currentlocationx = " + currentLocationX);
		System.out.println("currentlocationy = " + currentLocationY);
		System.out.println("symbol at maze current location: " + maze[currentLocationX][currentLocationY]);

		//the stack that stores branches stores the current location
//		branchStack.push(currentLocationY);
//		branchStack.push(currentLocationX);
		
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
				System.out.println("Current location: " + endLocationX + ", " + endLocationY);
				System.exit(0); // after solving, the program ends
			}

			//The system will print out its current location
			System.out.println("Current Location " + "X: " + currentLocationX + " Y: " + currentLocationY);
			count = 0; //move option counter is reset
			moveCount++; //number of moves made increases
			//the stack that holds the correct path is updated with the current coordinates (
			pathStack.push(currentLocationY);
			pathStack.push(currentLocationX);
			printMaze();

			//because each of the goDirectionPossible variables are set to false at the beginning of every loop, these will always trigger
			//for some reason if this if loop wasnt here it made a loop in the code
			//
			if (goSouthPossible == false) {
				try {
					if (maze[currentLocationX + 1][currentLocationY] == 0) {
						goSouthPossible = false;
						System.out.println("Go south False");
					} else {
						goSouthPossible = true;
						System.out.println("Go south True");
						count++;

					}
				} catch (Exception e) {
					goSouthPossible = false;
					System.out.println("Cannot go south");
				}
			}

			if (goNorthPossible == false) {
				try {
					if (maze[currentLocationX - 1][currentLocationY] == 0) {
						goNorthPossible = false;
						System.out.println("Go north False");
					} else {
						goNorthPossible = true;
						System.out.println("Go north True");
						count++;

					}
				} catch (Exception e) {
					goNorthPossible = false;
					System.out.println("Cannot go north");
				}
			}
			
			if (goEastPossible == false) {
				try {

					if (maze[currentLocationX][currentLocationY + 1] == 0) {
						goEastPossible = false;
						System.out.println("Go east False");
					} else {
						goEastPossible = true;
						System.out.println("Go east True");
						count++;

					}
				} catch (Exception e) {
					goEastPossible = false;
					System.out.println("Cannot go east");
				}

			}

			if (goWestPossible == false) {
				try {
					if (maze[currentLocationX][currentLocationY - 1] == 0) {
						goWestPossible = false;
						System.out.println("Go west False");
					} else {
						goWestPossible = true;
						System.out.println("Go west True");
						count++;

					}
				} catch (Exception e) {
					goWestPossible = false;
					System.out.println("Cannot go west");
				}
			}
			
			// After checking which next step is possible goes to check if there is just 1,
			// 2 or zero options, and prints that number
			System.out.println("ways to go: " + count);
			
			// If there is only one path to follow. Updates location to that one
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
					//DEV NOTE removing this section did not stop the loop in the default maze output
					if (currentLocationX < sizeMazeX-1) {
					if (maze[currentLocationX + 1][currentLocationY] == 0) {
						goSouthPossible = false;
						System.out.println("Go south False");
					}else if (maze[currentLocationX - 1][currentLocationY] == 0) {
						goNorthPossible = false;
						System.out.println("Go north False");
					}
					}
					
					if (currentLocationY < sizeMazeY-1) {
					if (maze[currentLocationX][currentLocationY + 1] == 0) {
						goEastPossible = false;
						System.out.println("Go east False");
					} else if (maze[currentLocationX][currentLocationY - 1] == 0) {
						goEastPossible = true;
						System.out.println("Go west False");
					}
					}
					
					currentLocationX = branchStack.pop();
					currentLocationY = branchStack.pop();
					System.out.println("Popping from branch stack and going back");

				} else {
					System.out.println("Not possible");
					System.exit(0);
				}

			}
			if (count > 1) {
				// add current location to last branch
				// pick way to go

				branchStack.push(currentLocationY);
				branchStack.push(currentLocationX);
				System.out.println("pushing current location to branch stack");

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

	// Prints maze in console inside the class
	public void printMaze() {
		for (int k = 0; k < maze.length; k++) {
			for (int l = 0; l < maze.length; l++) {
				System.out.print(maze[k][l]);
			}
			System.out.println();
		}
	}

	// Override for toString method works only outside the class
	@Override
	public String toString() {
		String line = "";
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				line += " " + maze[row][col];
				if (col == 9) {
					line += "\n";
				}
			}
		}
		return line;
	}

}

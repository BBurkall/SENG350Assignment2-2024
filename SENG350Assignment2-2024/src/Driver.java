
public class Driver {

	public static void main(String[] args) {
		
		Maze maze = new Maze();
		maze.readFromFile();
//		maze.move();
//		maze.printMaze();
		System.out.println(maze);
		System.out.println();
		maze.move();
		
	}

}

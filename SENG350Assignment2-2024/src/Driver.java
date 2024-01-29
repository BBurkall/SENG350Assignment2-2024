
public class Driver {

	public static void main(String[] args) {
		
		Maze maze = new Maze();
		maze.readMazeFile();
		System.out.println();
		maze.move();
		
	}

}

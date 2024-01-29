import java.util.ArrayList;
import java.util.LinkedList;

// Task 3 and 4
public class MazeStack {

	private ArrayList<Integer> arrayList;
//	private LinkedList<Integer> linkedList;
	private int index;

	public MazeStack() {
		arrayList = new ArrayList<>();
		index = -1;
	}

//	public MazeStack() {
//		linkedList = new LinkedList<>();
//		index = -1;
//	}

	public void push(Integer x) {
		index++;
		arrayList.add(x);
	}

//	public void push(Character x) {
//		index++;
//		linkedList.add(x);
//	}

	public Integer pop() {
		Integer deletedInt = arrayList.remove(index);
		index--;
		return deletedInt;
	}

//	public Character pop() {
//		Character deletedChar = linkedList.remove(index);
//		index--;
//		return deletedChar;
//	}

	public Integer peek() {
		return arrayList.get(index);

	}

//	public Character peek() {
//		return linkedList.get(index);
//
//	}

	public boolean isEmpty() {
		if (arrayList.isEmpty()) {
			return true;
		}
		return false;
	}

//	public boolean isEmpty() {
//		if (linkedList.isEmpty()) {
//			return true;
//		}
//		return false;
//	}

	@Override
	public String toString() {
		String result = "";
		result += arrayList;
		return result;
	}

//	@Override
//	public String toString() {
//		String result = "";
//		result += linkedList;
//		return result;
//	}

	public static void main(String[] args) {

	}
}

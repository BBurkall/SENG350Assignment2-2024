import java.util.ArrayList;
import java.util.LinkedList;

public class MazeStack {

	private ArrayList<Integer> arrayList;
	private int index;

	public MazeStack() {
		arrayList = new ArrayList<>();
		index = -1;
	}

	public void push(Integer x) {
		index++;
		arrayList.add(x);
	}

	public Integer pop() {
		Integer deletedInt = arrayList.remove(index);
		index--;
		return deletedInt;
	}

	public Integer peek() {
		return arrayList.get(index);

	}

	public boolean isEmpty() {
		if (arrayList.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String result = "";
		result += arrayList;
		return result;
	}

	public static void main(String[] args) {

	}
}

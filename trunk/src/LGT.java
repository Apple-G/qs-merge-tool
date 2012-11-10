import java.util.ArrayList;

public class LGT {

	private ArrayList<String> lgtList;
	private int index;

	public LGT() {
		lgtList = new ArrayList<String>();
	}

	public void generateLGT() {
		index = 0;
		TestLGT();
	}

	private void TestLGT() {
		lgtList.add("A");
		lgtList.add("B");
		lgtList.add("C");
		lgtList.add("D");
		lgtList.add("E");
	}

	public String getLGTPos(int pos) {
		if (lgtList.size() <= pos) {
			return lgtList.get(pos);
		}
		return null;
	}

	public String getNextLGT() {
		return getLGTPos(index++);
	}

}

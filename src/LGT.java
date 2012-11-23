import java.util.ArrayList;

public class LGT {

	private ArrayList<String> lgtList;
	private int index;

	public LGT() {
		lgtList = new ArrayList<String>();
		index = 0;
	}

	public void generateLGT(ArrayList<String> org, ArrayList<String> file1, ArrayList<String> file2) {
		ArrayList<String> temp = calculate(org, file1);
		lgtList = calculate(temp, file2);
	}

	public ArrayList<String> calculate(ArrayList<String> links, ArrayList<String> rechts) {

		if (links.size() < rechts.size()) {
			ArrayList<String> tmp = null;
			tmp = rechts;
			rechts = links;
			links = tmp;

		}
		int c[][] = new int[links.size() + 1][rechts.size() + 1];

		for (int i = 0; i < links.size(); i++) {
			c[i][0] = 0;
		}
		for (int j = 0; j < rechts.size(); j++) {
			c[0][j] = 0;
		}

		// länge rausfinden
		for (int i = 1; i <= links.size(); ++i) {
			for (int j = 1; j <= rechts.size(); ++j) {
				if (links.get(i - 1).equals(rechts.get(j - 1))) {
					c[i][j] = c[i - 1][j - 1] + 1;

				} else if (c[i - 1][j] >= c[i][j - 1]) {
					c[i][j] = c[i - 1][j];
				} else {
					c[i][j] = c[i][j - 1];

				}

				System.out.print("i:" + i + "j:" + j + " " + c[i][j] + "   ");
			}
			System.out.print("\n");
		}

		// reverse, inhalt rausfinde
		ArrayList<String> v = new ArrayList<String>();
		int i = links.size();
		int j = rechts.size();
		while (i != 0 || j != 0) {

			// Nur für die debugHilfe
			if (i != 0 && j != 0) {
				String aa = links.get(i - 1);
				String bb = rechts.get(j - 1);
			}
			// ende Hilfe

			if (i != 0 && j != 0 && links.get(i - 1).equals(rechts.get(j - 1))) {
				v.add(links.get(i - 1));
				i--;
				j--;
			} else if (j != 0 && c[i][j] == c[i][j - 1]) {
				j--;
			} else if (i != 0 && c[i][j] == c[i - 1][j]) {
				i--;
			}
		}
		// umdrehen

		ArrayList<String> erg = new ArrayList<String>();
		for (int x = v.size()-1; x >= 0; x--) {
			erg.add(v.get(x));
		}
		return erg;
	}

	public String getLGTPos(int pos) {
		if (lgtList.size() > pos) {
			return lgtList.get(pos);
		}
		return null;
	}

	public String getNextLGT() {
		return getLGTPos(index++);
	}

}

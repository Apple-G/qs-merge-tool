import java.util.ArrayList;
import java.util.List;

public class Merge {

	private ArrayList<String> mergeOutput;

	private FileHandler fileOrg;
	private FileHandler file1;
	private FileHandler file2;

	private LGT lgt;

	public Merge() {
		mergeOutput = new ArrayList<String>();
	}

	public boolean LoadFiles(String fileOrgPath, String file1Path, String file2Path) {
		fileOrg = new FileHandler(fileOrgPath);
		file1 = new FileHandler(file1Path);
		file2 = new FileHandler(file2Path);

		if (fileOrg.readFile() && file1.readFile() && file2.readFile()) {

			lgt = new LGT();
			lgt.generateLGT();

			return true;
		} else {
			System.out.println("Fehler beim lesen der Datei");
			return false;
		}
	}

	public ArrayList<String> startMerge() {
		int row = 1;

		String rowLGT = lgt.getNextLGT();
		String rowFile1 = file1.GetNextRow();
		String rowFile2 = file2.GetNextRow();
		String rowFileOrg = fileOrg.GetNextRow();

		while (rowLGT == null && rowFileOrg == null && rowFile1 == null && rowFile2 == null) {

			// Fall 1:
			// alles Gelich
			// => LGT
			if (rowLGT.equals(rowFileOrg) && rowLGT.equals(rowFile1) && rowLGT.equals(rowFile2)) {
				mergeOutput.add(rowLGT);

				rowLGT = lgt.getNextLGT();
				rowFile1 = file1.GetNextRow();
				rowFile2 = file2.GetNextRow();
				rowFileOrg = fileOrg.GetNextRow();
			}

			// Fall 2:
			// Datei 1 oder Datei 2 unterschiedlich, LGT und Orginal gleich
			else if (rowLGT.equals(rowFileOrg)) {

				// Fall 2.1 Datei 1 unterschiedlich zu LGT
				// => Datei 1
				if (!rowLGT.equals(rowFile1) && rowLGT.equals(rowFile2)) {
					mergeOutput.add(rowFile1);
					rowFile1 = file1.GetNextRow();
				}

				// Fall 2.2 Datei 2 unterschiedlich zu LGT
				// => Datei 2
				else if (rowLGT.equals(rowFile1) && !rowLGT.equals(rowFile2)) {
					mergeOutput.add(rowFile2);
					rowFile2 = file2.GetNextRow();
				}

				// Fall 2.3 Datei 1 & Datei 2 unterschiedlich zu LGT
				// => Konflikt oder Datei1 und Datei 2 geli => Datei 1
				else {
					if (rowFile1.equals(rowFile2)) {
						mergeOutput.add(rowFile1);
					} else {
						mergeOutput.add("##Konflikt##");
						mergeOutput.add("##Datei1: " + rowFile1);
						mergeOutput.add("##Datei2: " + rowFile2);
						rowFile1 = file1.GetNextRow();
						rowFile2 = file2.GetNextRow();
					}
				}
			}
			// Fall 3:
			// zwei unterschiede zur LGT

			// Fall X:
			// Konflikt
			else {

			}
		}
		return mergeOutput;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Merge merg = new Merge();

		if (merg.LoadFiles("D:\\Daten\\Tobias\\workspace\\hska.qs.MergeTool\\files\\fileOrg.txt", "D:\\Daten\\Tobias\\workspace\\hska.qs.MergeTool\\files\\file1.txt", "D:\\Daten\\Tobias\\workspace\\hska.qs.MergeTool\\files\\file2.txt")) {
			merg.startMerge();
		}

	}
}

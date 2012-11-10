import java.util.ArrayList;

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
			lgt.generateLGT(fileOrg.getList(), file1.getList(), file2.getList());

			return true;
		} else {
			System.out.println("Fehler beim lesen der Datei");
			return false;
		}
	}

	public ArrayList<String> startMerge() {

		String rowLGT = lgt.getNextLGT();
		String rowFile1 = file1.getNextRow();
		String rowFile2 = file2.getNextRow();
		String rowFileOrg = fileOrg.getNextRow();

		while (rowLGT != null || rowFileOrg != null || rowFile1 != null || rowFile2 != null) {

			// Fall 1
			// LGT ist nicht Null
			// => LGT
			if (rowLGT != null) {

				// Fall 1.1
				// LGT und Orginal gleich
				if (rowLGT.equals(rowFileOrg)) {

					// Fall 1.1.1
					// Alle Gleich
					if (rowLGT.equals(rowFile1) && rowLGT.equals(rowFile2)) {
						mergeOutput.add(rowLGT);

						rowLGT = lgt.getNextLGT();
						rowFile1 = file1.getNextRow();
						rowFile2 = file2.getNextRow();
						rowFileOrg = fileOrg.getNextRow();

					}

					// Fall 1.1.2 Datei 1 unterschiedlich zu LGT
					// => Datei 1
					else if (!rowLGT.equals(rowFile1) && rowLGT.equals(rowFile2)) {
						mergeOutput.add(rowFile1);
						rowFile1 = file1.getNextRow();
					}

					// Fall 1.1.3 Datei 2 unterschiedlich zu LGT
					// => Datei 2
					else if (rowLGT.equals(rowFile1) && !rowLGT.equals(rowFile2)) {
						mergeOutput.add(rowFile2);
						rowFile2 = file2.getNextRow();
					}

					// Fall 1.1.4 Datei 1 & Datei 2 unterschiedlich zu LGT
					// => Konflikt oder Datei1 und Datei 2 gleich => Datei 1
					else {
						// Fall 1.1.4.1 ein der beiden Dateien Null
						if (rowFile1 == null || rowFile2 == null) {
							// Datei 2 ist NULL aenderungen von Datei 1 werden
							// uebernommen
							if (rowFile1 != null) {
								mergeOutput.add(rowFile1);
								rowFile1 = file1.getNextRow();
							}
							// Datei 1 ist NULL aenderungen von Datei 2 werden
							// uebernommen
							else if (rowFile2 != null) {
								mergeOutput.add(rowFile2);
								rowFile2 = file2.getNextRow();
							}
						}
						// Fall 1.1.4.2
						// beide nicht null -> aenderung in beiden dateien
						else {
							if (rowFile1.equals(rowFile2)) { // gleiche aenderung in beiden dateien, uebernehmen
								mergeOutput.add(rowFile1);
							} else {// Konflikt, aenderungen sind nicht gleich
								System.out.println("Konflikt , 1.1.4.2");
								mergeOutput.add("##Konflikt##");
								mergeOutput.add("##Datei1: " + rowFile1);
								mergeOutput.add("##Datei2: " + rowFile2);
							}
							rowFile1 = file1.getNextRow();
							rowFile2 = file2.getNextRow();

						}
					}

				} else {// Fall 1.2
					// LGT und orig sind unterschiedlich -> es wurde was gelöscht oder geändert
					if (rowFileOrg != null) {
						if (rowFileOrg.equals(rowFile1)) {//änderungen an Datei 2. änderungen übernehmen
							mergeOutput.add(rowFileOrg);
							rowFile1 = file1.getNextRow();
						} else if (rowFileOrg.equals(rowFile2)) {//änderungen an datei 1.  änderungen übernehmen
							mergeOutput.add(rowFileOrg);
							rowFile2 = file2.getNextRow();
						}
//						 else {//änderungen an beiden dateien.
//							// Konflikt
//							System.out.println("Konflikt , 1.2");
//							mergeOutput.add("##Konflikt bei löschen##");
//							mergeOutput.add("##Datei1: " + rowFile1);
//							mergeOutput.add("##Datei2: " + rowFile2);
//						}
					}// Else nicht nötig, da übersprungen wird
					rowFileOrg = fileOrg.getNextRow();

				}
			} else {// Fall 2
				// LGT==null
				// Fall 2.1 ende der übereinstimmung, einer datei wurde was hinzugefügt
				if (rowFile1 == null || rowFile2 == null) {
					// Datei 2 ist NULL aenderungen von Datei 1 werden
					// uebernommen
					if (rowFile1 != null) {
						mergeOutput.add(rowFile1);
						rowFile1 = file1.getNextRow();
					}
					// Datei 1 ist NULL aenderungen von Datei 2 werden
					// uebernommen
					else if (rowFile2 != null) {
						mergeOutput.add(rowFile2);
						rowFile2 = file2.getNextRow();
					} else {
						rowFileOrg = null;// in beiden dateien wurde alles nachfolgende gelöscht, deswegen EOF

					}
				}
				// Fall 2.2
				// beide nicht null -> aenderung in beiden dateien
				else {
					if (rowFile1.equals(rowFile2)) { // gleiche aenderung in beiden dateien, uebernehmen
						mergeOutput.add(rowFile1);
					} else {// Konflikt, aenderungen sind nicht gleich
						System.out.println("Konflikt , 1.1.4.2");
						mergeOutput.add("##Konflikt##");
						mergeOutput.add("##Datei1: " + rowFile1);
						mergeOutput.add("##Datei2: " + rowFile2);
					}
					rowFile1 = file1.getNextRow();
					rowFile2 = file2.getNextRow();

				}

			}
		}// end while

		return mergeOutput;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Merge merg = new Merge();
		
		if (merg.LoadFiles("D:\\Daten\\Tobias\\workspace\\hska.qs.MergeTool\\files\\fileOrg.txt", "D:\\Daten\\Tobias\\workspace\\hska.qs.MergeTool\\files\\file1.txt", "D:\\Daten\\Tobias\\workspace\\hska.qs.MergeTool\\files\\file2.txt")) {
			ArrayList<String> list = merg.startMerge();
			System.out.println(list);
		}

	}
}

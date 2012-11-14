import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

			System.out.println(" ### Start ### ");
			System.out.println("rowLGT > " + rowLGT);
			System.out.println("rowFile1 > " + rowFile1);
			System.out.println("rowFile2 > " + rowFile2);
			System.out.println("rowFileOrg > " + rowFileOrg);

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
						System.out.println("Fall 1.1.1 -> Alle Gleich");
						mergeOutput.add(rowLGT);

						rowLGT = lgt.getNextLGT();
						rowFile1 = file1.getNextRow();
						rowFile2 = file2.getNextRow();
						rowFileOrg = fileOrg.getNextRow();
					}

					// Fall 1.1.2 Datei 1 unterschiedlich zu LGT
					// => Datei 1
					else if (!rowLGT.equals(rowFile1) && rowLGT.equals(rowFile2)) {
						System.out.println("Fall 1.1.2 Datei 1 unterschiedlich zu LGT");
						mergeOutput.add(rowFile1);
						rowFile1 = file1.getNextRow();
					}

					// Fall 1.1.3 Datei 2 unterschiedlich zu LGT
					// => Datei 2
					else if (rowLGT.equals(rowFile1) && !rowLGT.equals(rowFile2)) {
						System.out.println("Fall 1.1.3 Datei 2 unterschiedlich zu LGT");
						mergeOutput.add(rowFile2);
						rowFile2 = file2.getNextRow();
					}

					// Fall 1.1.4 Datei 1 & Datei 2 unterschiedlich zu LGT
					// => Konflikt oder Datei1 und Datei 2 gleich => Datei 1
					else {
						// Fall 1.1.4.1 ein der beiden Dateien Null
						if (rowFile1 == null || rowFile2 == null) {
							// Datei 2 ist NULL aenderungen von Datei 1 werden uebernommen
							if (rowFile1 != null) {
								System.out.println("Fall 1.1.4.1 ein der beiden Dateien Null (Datei 2 ist NULL)");
								mergeOutput.add(rowFile1);
								rowFile1 = file1.getNextRow();
							}
							// Datei 1 ist NULL aenderungen von Datei 2 werden uebernommen
							else if (rowFile2 != null) {
								System.out.println("Fall 1.1.4.1 ein der beiden Dateien Null (Datei 1 ist NULL)");
								mergeOutput.add(rowFile2);
								rowFile2 = file2.getNextRow();
							}
						}
						// ## Beide Null kann micht vorkommen, da LGT

						// Fall 1.1.4.2
						// beide nicht null -> aenderung in beiden dateien
						else {
							if (rowFile1.equals(rowFile2)) { // gleiche aenderung in beiden dateien, uebernehmen
								System.out.println("Fall 1.1.4.2 gleiche aenderung in beiden dateien");
								mergeOutput.add(rowFile1);
							} else {// Konflikt, aenderungen sind nicht gleich
								System.out.println("Fall 1.1.4.2 Konflikt, aenderungen sind nicht gleich");

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
					// LGT und orig sind unterschiedlich -> es wurde was gelöscht, eingefügt oder geändert

					if (rowFileOrg != null) {

						// Fall 1.2.1 einseitige aendernungen in Datei 2
						if (rowFileOrg.equals(rowFile1)) {// änderungen an Datei 2. änderungen übernehmen oder Fehler wenn Datei 2 = LGT

							if (rowLGT.equals(rowFile2)) {

								System.out.println("Fall 1.2.1.1 LGT und orig sind unterschiedlich, org und File 1 gleich, aber File 2 = LGT....");
								mergeOutput.add("##TODO - kp was machen vorraussichtlich Konflikt");
								rowFile1 = file1.getNextRow();
								rowFileOrg = fileOrg.getNextRow();

							} else {

								System.out.println("Fall 1.2.1.1 LGT und orig sind unterschiedlich, org und File 1 gleich, änderungen an Datei 2");
								mergeOutput.add(rowFile2);
								rowFile1 = file1.getNextRow();
								rowFile2 = file2.getNextRow();
								rowFileOrg = fileOrg.getNextRow();

							}

							// Fall 1.2.2 einseitige aendernungen in Datei 1
						} else if (rowFileOrg.equals(rowFile2)) {// änderungen an datei 1. änderungen übernehmen oder Fehler wenn Datei 1 = LGT
							if (rowLGT.equals(rowFile1)) {

								System.out.println("Fall 1.2.1 LGT und orig sind unterschiedlich, org und File2 gleich, aber File 1 = LGT....");
								mergeOutput.add("##TODO - kp was machen vorraussichtlich Konflikt");
								rowFile2 = file2.getNextRow();
								rowFileOrg = fileOrg.getNextRow();

							} else {

								System.out.println("Fall 1.2.1 LGT und orig sind unterschiedlich, org und File 2 gleich, änderungen an Datei 1");
								mergeOutput.add(rowFile1);
								rowFile1 = file1.getNextRow();
								rowFile2 = file2.getNextRow();
								rowFileOrg = fileOrg.getNextRow();

							}

							// //Fall 1.2.3 gleiche aenderung in Datei 1 & 2 => überschreiben der OrginalDatei
						} else if (rowFile1.equals(rowFile2)) {
							System.out.println("Fall 1.2.3 LGT und orig sind unterschiedlich, gleiche aenderung in Datei 1 & 2 => überschreiben der OrginalDatei");
							mergeOutput.add(rowFile1);
							rowFile1 = file1.getNextRow();
							rowFile2 = file2.getNextRow();
							rowFileOrg = fileOrg.getNextRow();

							// Fall 1.2.4 unterschiedliche änderungen in Datei 1 & 2 => Fehler
						} else {
							// Konflikt
							System.out.println("Fall 1.2.4 LGT und orig sind unterschiedlich, änderungen an beiden Dateien.");

							System.out.println("Konflikt , 1.2.4");
							mergeOutput.add("##Konflikt bei löschen##");
							mergeOutput.add("##Datei1: " + rowFile1);
							mergeOutput.add("##Datei2: " + rowFile2);

							rowFile1 = file1.getNextRow();
							rowFile2 = file2.getNextRow();

							rowFileOrg = fileOrg.getNextRow();
						}
					}

				}
				// ### ORG = Null && LGT != Null => nicht möglich!
			} else {// Fall 2
				// LGT==null
				// Fall 2.1 ende der übereinstimmung, einer datei wurde was hinzugefügt
				if (rowFile1 == null || rowFile2 == null) {
					// Datei 2 ist NULL aenderungen von Datei 1 werden
					// uebernommen
					if (rowFile1 != null) {
						System.out.println("Fall 2.1 ende der übereinstimmung, einer datei wurde was hinzugefügt, änderungen von Datei 1.");
						mergeOutput.add(rowFile1);
						rowFile1 = file1.getNextRow();
					}
					// Datei 1 ist NULL aenderungen von Datei 2 werden
					// uebernommen
					else if (rowFile2 != null) {
						System.out.println("Fall 2.1 ende der übereinstimmung, einer datei wurde was hinzugefügt, änderungen von Datei 2.");
						mergeOutput.add(rowFile2);
						rowFile2 = file2.getNextRow();
					} else {
						System.out.println("Fall 2.1 EOF");
						rowFileOrg = null;// in beiden dateien wurde alles nachfolgende gelöscht, deswegen EOF

					}
				}
				// Fall 2.2
				// beide nicht null -> aenderung in beiden dateien
				else {
					if (rowFile1.equals(rowFile2)) { // gleiche aenderung in beiden dateien, uebernehmen
						System.out.println("Fall 2.2 gleiche aenderung in beiden dateien, uebernehmen.");
						mergeOutput.add(rowFile1);
					} else {// Konflikt, aenderungen sind nicht gleich
						System.out.println("Fall 2.2 gleiche aenderung in beiden dateien nicht gleich, Konflikt.");
						System.out.println("Konflikt , 2.2");
						mergeOutput.add("##Konflikt##");
						mergeOutput.add("##Datei1: " + rowFile1);
						mergeOutput.add("##Datei2: " + rowFile2);
					}
					rowFile1 = file1.getNextRow();
					rowFile2 = file2.getNextRow();

				}

			}
			System.out.println("Last MergeOutput > " + mergeOutput.get(mergeOutput.size() - 1));
			System.out.println(" ### Ende ### ");
		}// end while

		return mergeOutput;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Merge merg = new Merge();

		String path = "C:\\Temp\\";

		System.out.println();
		if (merg.LoadFiles(path + "Source.txt", path + "file1.txt", path + "file2.txt")) {

			//Mege
			System.out.println("Start Mergeing");
			ArrayList<String> list = merg.startMerge();
			
			//Generate outPath
			SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
			String uhrzeit = sdf.format(new Date());			
			String outPath = path + "Output_" + uhrzeit + ".txt";
			
			//Write to File
			System.out.println("Write to File: " +outPath);			
			if (!FileHandler.writeToFile(outPath, list)) {
				System.out.println("Error beim AusgabeDatei schreiben.");
				System.out.println(list);
			}
		}
		
		System.out.println("End of Prog!");

	}
}

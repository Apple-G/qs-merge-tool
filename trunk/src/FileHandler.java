import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FileHandler {

	private int index;
	private String filePath;
	private ArrayList<String> fileList;

	public FileHandler(String file) {
		index = 0;
		filePath = file;
		fileList = new ArrayList<String>();
	}

	public boolean readFile() {
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filePath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				fileList.add(strLine);

			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			return false;
		}
		return true;
	}

	public ArrayList<String> getList() {
		return fileList;
	}

	public String getRow(int row) {
		if (fileList.size() > row) {
			return fileList.get(row);
		}

		return null;
	}

	public String getNextRow() {
		return getRow(index++);
	}

	public static boolean writeToFile(String outputFilePath, ArrayList<String> rowList) {
		try {

			BufferedWriter out = new BufferedWriter(new FileWriter(outputFilePath));
			for (String str : rowList) {
				out.write(str);
				out.newLine();
			}
			out.close();

		} catch (Exception e) {// Catch exception if any
			return false;
		}
		return true;
	}
}

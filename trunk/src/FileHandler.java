import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FileHandler {

	private int index;
	private String filePath;
	private HashMap<Integer, String> fileMap;

	public FileHandler(String file) {
		index = 0;
		filePath = file;
		fileMap = new HashMap<Integer, String>();
	}

	public boolean readFile() {
		int row = 1;
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
				fileMap.put(row, strLine);
				row++;

			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			return false;
		}
		return true;
	}

	public String GetRow(int row) {
		if (fileMap.containsKey(row)) {
			return fileMap.get(row);
		}

		return null;
	}

	public String GetNextRow() {
		return GetRow(index++);
	}
}

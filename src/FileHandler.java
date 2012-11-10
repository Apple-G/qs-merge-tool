import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FileHandler {

	private int index;
	private String filePath;
	private ArrayList<String> fileList;

	public FileHandler(String file) {
		index = 1;
		filePath = file;
		fileList = new ArrayList<String>();
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
				fileList.add(strLine);
				row++;

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
}

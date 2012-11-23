import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;


public class MergeTest {
	

	@Test
	public void mainTest()
	{		
		boolean loadFieles = false;	
		boolean saveFieles = false;		
		Merge merg = null;
		ArrayList<String> list = null;
		String path = "files\\3\\";
		
		merg = new Merge();	
		
		loadFieles = merg.LoadFiles(path + "Source.txt", path + "file1.txt", path + "file2.txt");

		if (loadFieles) {

			list = merg.startMerge();

			// Generate outPath
			SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
			String uhrzeit = sdf.format(new Date());
			String outPath = path + "Output_" + uhrzeit + ".txt";

			// Write to File
			System.out.println("Write to File: " + outPath);
			
			saveFieles = FileHandler.writeToFile(outPath, list);
		}

		assertTrue("Load Fieles", loadFieles);
		assertNotNull("", list);
		assertTrue("SaveFieles", saveFieles);
	}
	

	@Test
	public void mainTest2()
	{		
		boolean loadFieles = false;	
		boolean saveFieles = false;		
		Merge merg = null;
		ArrayList<String> list = null;
		String path = "files\\4\\";
		
		merg = new Merge();	
		
		loadFieles = merg.LoadFiles(path + "Source.txt", path + "file1.txt", path + "file2.txt");

		if (loadFieles) {

			list = merg.startMerge();

			// Generate outPath
			SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
			String uhrzeit = sdf.format(new Date());
			String outPath = path + "Output_" + uhrzeit + ".txt";

			// Write to File
			System.out.println("Write to File: " + outPath);
			
			saveFieles = FileHandler.writeToFile(outPath, list);
		}

		assertTrue("Load Fieles", loadFieles);
		assertNotNull("", list);
		assertTrue("SaveFieles", saveFieles);
	}
	
	@Test
	public void LoadFieleFail()
	{
		Merge merg = new Merge();			
		assertFalse(merg.LoadFiles("", "", ""));
	}
	
	@Test
	public void SaveFieleFail()
	{

		assertFalse(FileHandler.writeToFile("", null));
	}

}

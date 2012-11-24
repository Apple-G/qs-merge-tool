import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class MemoryTest {

	@Test
	public void memoryTest() 
	{
		boolean loadFieles = false;
		Merge merg = null;
		ArrayList<String> list = null;
		String path = "files\\1\\";

		merg = new Merge();

		loadFieles = merg.LoadFiles(path + "Source.txt", path + "file1.txt", path + "file2.txt");


			assertFalse("Load Files", loadFieles);
	}

}

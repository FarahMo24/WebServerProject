package ServerResponse;

import java.io.*;
import java.util.*;
import java.text.*;

public class ResourceOperation {

	public static ArrayList<Byte> readContents(String path) throws IOException {
		ArrayList<Byte> body = new ArrayList<>();
		FileInputStream fin = new FileInputStream(path);
		int i = 0;
		while ((i = fin.read()) != -1) {
			body.add((byte) i);
		}
		fin.close();
		return body;
	}

	public static void createFile(String path, byte[] contents) throws IOException {
		File f = new File(path);
		if(f.createNewFile()) {
			FileWriter myWriter = new FileWriter(path);
			for(byte element : contents) {
				myWriter.write(element);
			}
			myWriter.close();
		} else {
			deleteFile(path);
			createFile(path, contents);
		}
	}

	public static void deleteFile(String path) {
		File f = new File(path);
		f.delete();
	}

	public static String formatDate(Date dateToModify) {
		String formattedDate = new SimpleDateFormat("EE, dd MMM yyyy kk:mm:ss z").format(dateToModify);
		return formattedDate;
	}

	public static String lastModified(String path) {
		File f = new File(path);
		Date lastModifiedDate = new Date(f.lastModified());
		return formatDate(lastModifiedDate);
	}
}

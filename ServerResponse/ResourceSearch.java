package ServerResponse;

import java.io.*;
import java.util.*;
import java.text.*;

public class ResourceSearch {

	public static ArrayList<Byte> readContents(String path) {
		try {
			System.out.println("Attempting to read file contents");
			ArrayList<Byte> body = new ArrayList<>();
			FileInputStream fin = new FileInputStream(path);
			System.out.println("FileInputStream Made");
			int i = 0;
			while ((i = fin.read()) != -1) {
				body.add((byte) i);
			}
			fin.close();
			return body;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void createFile(String path, byte[] contents) {
		try {
			File f = new File(path);
			if(f.createNewFile()) {
				FileWriter myWriter = new FileWriter(path);
				for(byte element : contents) {
					System.out.println(element);
					myWriter.write(element);
				}
				myWriter.close();
			} else {
				System.out.println("File already exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(String path) {
		System.out.println(path);
		File f = new File(path);
		if(f.delete()) {
			System.out.print("File successfully deleted!");
		} else {
			System.out.print("File at path " + path + " could not be deleted");
		}
	}

	public static String lastModified(String path) {
		File f = new File(path);
		Date lastModifiedDate = new Date(f.lastModified());
		String dayName = new SimpleDateFormat("EE, dd MMM yyyy kk:mm:ss z").format(lastModifiedDate);
		System.out.println(dayName);
		return dayName;
	}
}

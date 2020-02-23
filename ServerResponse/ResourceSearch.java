package ServerResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

class ResourceSearch {

	public static ArrayList<Byte> readContents(String path) {
		try {
			ArrayList<Byte> body = new ArrayList<>();
			FileInputStream fin = new FileInputStream(path);
			int i = 0;
			while ((i = fin.read()) != -1) {
				body.add((byte) i);
			}
			fin.close();
			return body;
		} catch (IOException e) {
			System.out.println("Error reading file to bytes");
		}
		return null;
	}
}

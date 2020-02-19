package ServerRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {

	public static String httpMethod;
	public static String URI;
	public static String httpVersion;
	public static String param;
	public static String body;
	public String firstLine;
	public String header_line;

	HashMap<String, String> paramMap = new HashMap<String, String>();
	HashMap<String,String> header = new HashMap<String, String>();



	public RequestParser(BufferedReader in)throws IOException {

		firstLine = in.readLine();
		// System.out.println("> " + firstLine);
		parseFirstLine(firstLine);

		header_line = in.readLine();

		while(!header_line.equals("")){

			parseHeader(header_line);
			header_line = in.readLine();
		}

		// Content-length header
		if(header.containsKey("Content-Length")){
			// if Content-Length is not 0
			if(!header.get("Content-Length").equals("0")){
				int length = Integer.parseInt(header.get("Content-Length"));
				for(int i = 0; i < length; i++){
					header_line = in.readLine();
					//body = parseBody(header_line);
				}
			}
		}
	}



	public void parseFirstLine(String line){
		// Parsing Request line and handling query
		try{

			String[] lineSplit = line.split("\\s+");
			httpMethod = lineSplit[0];
			URI = lineSplit[1];
			httpVersion = lineSplit[2];

			// Handling query for Get request
			if(httpMethod.equals("GET") && URI.contains("?")){
				String[] uriSplit = URI.split("\\?");
				URI = uriSplit[0];

				// if there is a second part after "?"
				if(uriSplit.length == 2){
					parseGet(uriSplit[1]);
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}


	}

	public void parseGet(String s) {
		// Handling querys
		// ?name1=value1&name2=value2   name1=value1  name2=value2
		try{
			String[] parameters = s.split("\\&");
			//  name1=value1  name2=value2
			for(int i = 0; i < parameters.length; i ++){

				String[] singleParam = parameters[i].split("\\=");
				// name1 value1
				paramMap.put(singleParam[0],singleParam[1]);
			}

		}catch(Exception e){
			System.out.println(e);
		}
	}

	public void parseHeader(String header_line) {
		// Handling header
		try{
			// Host: net.tutsplus.com
			String[] headerSplit = header_line.split(": ");
			if(headerSplit.length == 2 ){
				header.put(headerSplit[0],headerSplit[1]);

			}

			//System.out.println("hostkey: " + header.get("Host"));
		}catch(Exception e){
			System.out.println(e);
		}

	}

}
package ServerRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class Request {

	private final HashMap<String, String> requestValues;
	private String body;
	private final HashMap<String, String> paramMap = new HashMap<>();
	private final HashMap<String,String> headers = new HashMap<>();

	public String getHttpMethod() {
		return requestValues.get("httpMethod");
	}

	public String getURI() {
		return requestValues.get("URI");
	}

	public String getHttpVersion() {
		return requestValues.get("httpVersion");
	}

	public Request(BufferedReader in) throws IOException {

		requestValues = new HashMap<>();

		//TODO: Extract the following from the constructor
		String firstLine = in.readLine();
		parseFirstLine(firstLine);
		String header_line = in.readLine();
		while(!header_line.equals("")) {
			parseHeader(header_line);
			header_line = in.readLine();
		}
		if(headers.containsKey("Content-Length")){
			if(!headers.get("Content-Length").equals("0")){
				int length = Integer.parseInt(headers.get("Content-Length"));
				for(int i = 0; i < length; i++){
					header_line = in.readLine();
				}
			}
		}
	}

	private void parseFirstLine(String line){
		try{
			String[] lineSplit = line.split("\\s+");
			requestValues.put("httpMethod", lineSplit[0]);
			requestValues.put("URI", lineSplit[1]);
			requestValues.put("httpVersion", lineSplit[2]);

			if(requestValues.get("httpMethod").equals("GET") && requestValues.get("URI").contains("?")){
				String[] uriSplit = requestValues.get("URI").split("\\?");
				requestValues.put("URI", uriSplit[0]);
				if(uriSplit.length == 2){
					parseParameters(uriSplit[1]);
				}
			}
		}catch(Exception e){
			System.out.println("Error parsing first line of request");
		}
	}

	private void parseParameters(String s) {
		try{
			String[] parameters = s.split("&");
			for (String parameter : parameters) {
				String[] singleParam = parameter.split("=");
				paramMap.put(singleParam[0], singleParam[1]);
			}
		}catch(Exception e){
			System.out.println("Error parsing parameters for GET request");
		}
	}

	private void parseHeader(String header_line) {
		try{
			String[] headerSplit = header_line.split(": ");
			if(headerSplit.length == 2 ){
				headers.put(headerSplit[0],headerSplit[1]);
			}
		}catch(Exception e){
			System.out.println("Error parsing request headers");
		}
	}

	public void outputHeaders() {
		for (HashMap.Entry<String, String> entry: headers.entrySet()) {
			System.out.println(entry.getKey() + "   " + entry.getValue());
		}
	}
}

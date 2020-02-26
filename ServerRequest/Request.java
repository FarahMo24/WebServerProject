package ServerRequest;

import ServerUtils.*;
import ResponseStatusCode.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

public class Request {

	private final HashMap<String, String> requestValues;
	private byte[] body;
	private final HashMap<String, String> paramMap = new HashMap<>();
	private final HashMap<String,String> headers = new HashMap<>();
	public boolean isBadRequest = false;

	public String getHttpMethod() {
		return requestValues.get("httpMethod");
	}

	public String getURI() {
		return requestValues.get("URI");
	}

	public String getHttpVersion() {
		return requestValues.get("httpVersion");
	}

	public byte[] getBody() {
		return body;
	}

	public Request(BufferedReader in) throws IOException {
		requestValues = new HashMap<>();
		String firstLine = in.readLine();
		parseFirstLine(firstLine);
		parseHeaders(in);
		parseBody(in);
	}

	private void parseBody(BufferedReader in) throws IOException {
		if(headers.containsKey("Content-Length")){
			if(!headers.get("Content-Length").equals("0")){
				int length = Integer.parseInt(headers.get("Content-Length")); //Number of bytes in body
				byte[] bodyData = new byte[length];
				for(int i = 0; i < length; i++){
					byte bodyElement = (byte) in.read();
					bodyData[i] = bodyElement;
				}
				body = bodyData;
			}
		}
	}

	private void parseHeaders(BufferedReader in) throws IOException {
		String header_line = in.readLine();
		while(!header_line.equals("")) {
			parseHeader(header_line);
			header_line = in.readLine();
		}
	}

	private void parseFirstLine(String line){
		try {
			String[] lineSplit = line.split("\\s+");
			if(lineSplit.length == 3) {
				requestValues.put("httpMethod", lineSplit[0]);
				requestValues.put("URI", lineSplit[1]);

				System.out.println(lineSplit[1]);

				requestValues.put("httpVersion", lineSplit[2]);

				if(requestValues.get("httpMethod").equals("GET") && requestValues.get("URI").contains("?")){
					String[] uriSplit = requestValues.get("URI").split("\\?");
					requestValues.put("URI", uriSplit[0]);
					if(uriSplit.length == 2){
						parseParameters(uriSplit[1]);
					}
				}

			} else {
				isBadRequest = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void parseParameters(String s) {
		try {
			String[] parameters = s.split("&");
			for (String parameter : parameters) {
				String[] singleParam = parameter.split("=");
				paramMap.put(singleParam[0], singleParam[1]);
			}
		} catch(Exception e) {
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

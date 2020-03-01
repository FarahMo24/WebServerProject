package ServerRequest;

import ServerUtils.*;
import ResponseStatusCode.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

public class Request {

	//TODO: Make sure all 400 error cases are accounted for here

	private final HashMap<String, String> requestValues;
	private byte[] body;
	private final HashMap<String,String> headers = new HashMap<>();
	public boolean isBadRequest = false;
	public boolean isResourceScript = false;

	public String getHttpMethod() {
		return requestValues.get("HTTP_VERB");
	}

	public String getURI() {
		return requestValues.get("URI");
	}

	public String getHttpVersion() {
		return requestValues.get("HTTP_PROTOCOL");
	}

	public byte[] getBody() {
		return body;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public String getQueryString() {
		return requestValues.get("QUERY_STRING");
	}

	public Request(BufferedReader in) throws IOException {
		requestValues = new HashMap<>();
		String firstLine = in.readLine();
		parseFirstLine(firstLine);
		parseHeaders(in);
		parseBody(in);
	}

	private void parseBody(BufferedReader in) throws IOException {
		if(headers.containsKey("CONTENT_LENGTH")){
			if(!headers.get("CONTENT_LENGTH").equals("0")){
				int length = Integer.parseInt(headers.get("CONTENT_LENGTH")); //Number of bytes in body
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
				requestValues.put("HTTP_VERB", lineSplit[0]);
				requestValues.put("URI", lineSplit[1]);

				System.out.println("HTTP VERB: " + lineSplit[0]);

				requestValues.put("HTTP_PROTOCOL", lineSplit[2]);

				parseParameters();

			} else {
				isBadRequest = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void parseParameters() {
		requestValues.put("QUERY_STRING", "");
		if(requestValues.get("HTTP_VERB").equals("GET") && requestValues.get("URI").contains("?")){
			String[] uriSplit = requestValues.get("URI").split("\\?");
			requestValues.put("URI", uriSplit[0]);
			if(uriSplit.length == 2){
				requestValues.put("QUERY_STRING", uriSplit[1]);
			}
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

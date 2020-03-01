package CGI;

import java.util.*;
import ServerRequest.Request;
import ServerRequest.UriHandler;
import java.io.*;

public class CGI {

	//TODO: Figure out how to hands 500 status codes

	private Request request;
	private HashMap<String, String> headers = new HashMap<>();
	private ArrayList<Byte> body;

	public CGI(Request request) {
		this.request = request;
		this.body = new ArrayList<>();
		executeCGIScript();
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public ArrayList<Byte> getBody() {
		return body;
	}

	private void executeCGIScript() {
		try {
			ProcessBuilder pb = new ProcessBuilder("perl", "-T",  UriHandler.resolveURI(request.getURI()));
			Map<String, String> env = pb.environment();
			String queryString = request.getQueryString();
			env.put("QUERY_STRING", queryString);
			for(Map.Entry<String, String> header : request.getHeaders().entrySet()) {
				env.put(formatForCGI(header.getKey()), header.getValue());
			}
			env.put("HTTP_PROTOCOL", request.getHttpVersion());
			env.put("HTTP_VERB", request.getHttpMethod());
			env.put("HTTP_URI", request.getURI());
			final Process p = pb.start();
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));

			p.waitFor();
			String header_line = br.readLine();
			System.out.println(header_line);
			while(!header_line.equals("")) {
				System.out.println(header_line);
				parseHeader(header_line);
				header_line = br.readLine();
			}
			byte byteData;
			while((byteData=(byte)br.read())!=-1) {
				body.add(byteData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseHeader(String header_line) {
		try{
			String[] headerSplit = header_line.split(":");
			if(headerSplit.length == 2 ){
				headers.put(headerSplit[0],headerSplit[1]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private String formatForCGI(String value) {
		String cgiFormattedString = "HTTP_";
		if(!value.startsWith("HTTP_")) {
			cgiFormattedString = cgiFormattedString + value;
		} else {
			cgiFormattedString = value;
		}
		return cgiFormattedString.toUpperCase();
	}
}

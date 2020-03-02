package CGI;

import java.util.*;
import ServerRequest.Request;
import ServerRequest.UriHandler;
import java.io.*;

public class CGI {

	private Request request;
	private HashMap<String, String> headers = new HashMap<>();
	private ArrayList<Byte> body;

	public CGI(Request request) throws IOException, InterruptedException {
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

	private void executeCGIScript() throws IOException, InterruptedException {
		createBuilderForProcess();
		ProcessBuilder pb = createBuilderForProcess();
		setupProcessBuilderEnvironment(pb);
		final Process p = pb.start();
		BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
		p.waitFor();
		String header_line = br.readLine();
		while(!header_line.equals("")) {
			parseHeader(header_line);
			header_line = br.readLine();
		}
		byte byteData;
		while((byteData=(byte)br.read())!=-1) {
			body.add(byteData);
		}
	}

	private void setupProcessBuilderEnvironment(ProcessBuilder pb) {
		Map<String, String> env = pb.environment();
		String queryString = request.getQueryString();
		env.put("QUERY_STRING", queryString);
		for(Map.Entry<String, String> header : request.getHeaders().entrySet()) {
			env.put(formatForCGI(header.getKey()), header.getValue());
		}
		env.put("HTTP_PROTOCOL", request.getHttpVersion());
		env.put("HTTP_VERB", request.getHttpMethod());
		env.put("HTTP_URI", request.getURI());
	}

	private ProcessBuilder createBuilderForProcess() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(UriHandler.resolveURI(request.getURI())));
		String shebang = br.readLine();
		String[] splitShebang = shebang.split("\\s+", 2);
		String interpreter = splitShebang[0].substring(splitShebang[0].lastIndexOf("/") + 1, splitShebang[0].length());
		if(interpreter.equals("perl")) {
			return new ProcessBuilder("perl", "-T",  UriHandler.resolveURI(request.getURI()));
		} else if(interpreter.equals("bash")) {
			return new ProcessBuilder("sh", UriHandler.resolveURI(request.getURI()));
		} else {
			return new ProcessBuilder();
		}
	}

	private void parseHeader(String header_line) {
		String[] headerSplit = header_line.split(":");
		if(headerSplit.length == 2 ){
			headers.put(headerSplit[0],headerSplit[1]);
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

package ServerUtils;

import ServerConfig.Configuration;
import ServerRequest.Request;
import ServerResponse.ResourceOperation;
import ServerResponse.Response;

import java.io.*;
import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static String user = "-";

	public static void outputLog(Request request, Response response) throws IOException {
		String ip = Inet4Address.getLocalHost().toString();
		String userId = user;
		String formattedDate = new SimpleDateFormat("dd/MMM/yyyy:kk:mm:ss Z").format(new Date());
		String size = "-";
		if(response.getResponseHeaders().containsKey("CONTENT-LENGTH")) {
			size = response.getResponseHeaders().get("CONTENT-LENGTH");
		}
		String fullLog = ip + " - " + userId + " [" + formattedDate + "] \"" + request.getHttpMethod() + " " + request.getURI() + " " + request.getHttpVersion() + "\" " + response.getStatusCode() + " " + size + "\n";
		System.out.print(fullLog);
		outputToLogFile(fullLog);
	}

	private static void outputToLogFile(String logOutput) throws IOException{
		File f = new File(Configuration.getLogFilePath());
		if(!f.exists()) {
			ResourceOperation.createFile(Configuration.getLogFilePath(), logOutput.getBytes());
		} else {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(Configuration.getLogFilePath(), true));
			out.write(logOutput);
			out.close();
		}
	}

	public static void setUser(String userId) {
		user = userId;
	}
}

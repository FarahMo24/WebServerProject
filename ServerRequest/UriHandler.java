package ServerRequest;

import ServerConfig.*;
import java.util.HashMap;

public class UriHandler {

	RequestParser request;

	static String URI;
	static String absolutePath;
	static String addressResolved;

	static HashMap<String,String> ScriptAlias = new HashMap<>();
	static HashMap<String,String> Alias = new HashMap<>();





	public UriHandler(RequestParser request){
		this.request = request;
		URI = request.URI;
		// Fill in ScriptAlias HashMap
		for(HashMap.Entry<String,String> Scripts : Configuration.getScriptAlias().entrySet()){
			ScriptAlias.put(Scripts.getKey(),Scripts.getValue());
		}
		// Fill in Alias HashMap
		for(HashMap.Entry<String,String> Aliases : Configuration.getAlias().entrySet()){
			Alias.put(Aliases.getKey(),Aliases.getValue());
		}
	}

	public static String resolveURI(String uri){

		// TODO: Resolve for Script Aliases

		// TODO: Resolve for normal Aliases
		return "";
	}
}
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class ServerMimeMap {

    private HashMap<String, String> mapExt;

    public ServerMimeMap(File mimeFile){
        createMapping(mimeFile);
    }

    public String getMime(String ext){
        return mapExt.get(ext);
    }

    private void createMapping(File mimeFile){
        HashMap<String,String> extMap = new HashMap<>();
        try{

            Scanner fileReader = new Scanner(mimeFile);
            while(fileReader.hasNext()) {
                String str = fileReader.nextLine();
                if (str.startsWith("#") || str.startsWith(" ")) {
                    continue;
                }
                String mimeType="";
                String[] splitArr = str.split("\\s+");
                mimeType = splitArr[0];
                for(int i = 1; i < splitArr.length;i++){
                    extMap.put(splitArr[i],mimeType);
                }
            }
        }catch (IOException e){
            System.out.println("File not ");
        }
        this.mapExt = extMap;
    }
}

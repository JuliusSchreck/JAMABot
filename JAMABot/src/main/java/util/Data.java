package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.apache.commons.io.FileUtils;

public class Data {
    
    public static float volume = 1;
    
    public static String[][] CommandData;
    static final File COMMANDSETTINGSFILE = new File("data\\commands.conf");
    
    public static void loadCommandControl()throws FileNotFoundException{
        
        System.out.println("Reloading commands...");
        
        Scanner Reader = new Scanner(COMMANDSETTINGSFILE);
        String Contents = "";
        
        while(Reader.hasNext()){
            
            Contents = Contents+Reader.nextLine();
            
        }
        
        String[] FirstSplit = Contents.split(";");
        CommandData = new String[FirstSplit.length][4];
        
        for(int i = 0; i < FirstSplit.length; i++){
            
            CommandData[i] = FirstSplit[i].split(",");
            
        }
        
        System.out.println("Found and initialised "+FirstSplit.length+" command(s).");
        
    }
    
    public static int getClipTime(File clip) throws Exception{
        
        AudioInputStream ais = AudioSystem.getAudioInputStream(clip);
        AudioFormat format = ais.getFormat();
        long frames = ais.getFrameLength();
        double dis = (frames+0.0) / format.getFrameRate();
        
        Double time = dis*1000;
        
        return time.intValue()+500;
        
    }
    
    public static String defineCommand(String commandName, String commandType, String commandArgs)throws Exception{
        
        if(commandName.contains(";")||commandType.contains(";")||commandArgs.contains(";")){
            System.out.println("Command failed, contains invalid character");
            return "Invalid command. Command data cannot contain \";\"";
        }else if(commandName.contains(",")||commandType.contains(",")||commandArgs.contains(",")){
            System.out.println("Command failed, contains invalid character");
            return "Invalid command. Command data cannot contain \",\"";
        
        }else{
            
            if(commandType.equals("urlsound")){
                commandArgs = downloadFromURL(new URL(commandArgs));
            }
            
            PrintWriter PrintWriter = new PrintWriter(new FileOutputStream(Data.COMMANDSETTINGSFILE,true));
            PrintWriter.append("\n!"+commandName+","+commandType+","+commandArgs+";");
            PrintWriter.close();
            
        }
        
        Data.loadCommandControl();
        return "Command added successfully!";
        
    }
    
    public static String downloadFromURL(URL url)throws Exception{
        
        Random random = new Random();
        
        String fileName = "data\\sound\\"+random.nextInt()+".wav";
        
        FileUtils.copyURLToFile(url, new File(fileName));
        
        return fileName;
        
    }
}

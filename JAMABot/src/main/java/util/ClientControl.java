package util;

import java.io.File;
import java.util.*;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import sx.blah.discord.api.*;
import sx.blah.discord.handle.AudioChannel;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.*;

public class ClientControl {
    
    public static AudioChannel playAudioChannel;
    public static IVoiceChannel playVoiceChannel;
    
    public IDiscordClient getClient(String token) throws DiscordException{
        
        return new ClientBuilder().withToken(token).login();
        
    }
    
    public static void playSound(String toPlay,MessageReceivedEvent event) throws Exception{
        
        
        File filePlay = new File(toPlay);
        
        playVoiceChannel = event.getMessage().getAuthor().getVoiceChannel().get();
        playAudioChannel = new AudioChannel(playVoiceChannel.getGuild());
        
        playAudioChannel.setVolume(Data.volume);
        
        playVoiceChannel.join();
        playAudioChannel.queueFile(filePlay);
        Thread.sleep(Data.getClipTime(filePlay));
        playVoiceChannel.leave();
        
    }
    
}

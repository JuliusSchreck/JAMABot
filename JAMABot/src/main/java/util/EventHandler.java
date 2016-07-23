package util;

import java.util.List;
import main.JAMABot;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.IRole;

public class EventHandler {

    
    @EventSubscriber
    public void onReadyEvent(ReadyEvent event)throws Exception{
       
    }
    
    @EventSubscriber
    public static void onMessageEvent(MessageReceivedEvent event)throws Exception{
        
        String MessageContents = event.getMessage().getContent();
        String ChannelIDString = event.getMessage().getChannel().toString();
        
        ChannelIDString = ChannelIDString.replace("<#", "");
        ChannelIDString = ChannelIDString.replace(">", "");
        
        String[] MessageParse = MessageContents.split(" ");
        
        if(MessageParse.length==1){
            
        }

        boolean IsCommand = false;
        int index = -1;

        for(int i = 0; i < Data.CommandData.length; i++){
            if(Data.CommandData[i][0].equals(MessageParse[0].toLowerCase())){
                index = i;
                IsCommand = true;
            }
        }
        
        if(IsCommand){
            System.out.println("Found command!");
            if(Data.CommandData[index][1].equals("sound")){
                if(Data.CommandData[index][2].equals("stop")){
                    ClientControl.playAudioChannel.clearQueue();
                    ClientControl.playVoiceChannel.leave();
                    event.getMessage().delete();
                }else{
                    System.out.println("Sound command!");
                    event.getMessage().delete();
                    ClientControl.playSound(Data.CommandData[index][2], event);
                }
            }else if(Data.CommandData[index][1].equals("urlsound")){
                System.out.println("URLSound command!");
                event.getMessage().delete();
                ClientControl.playSound(Data.CommandData[index][2], event);
            }else if(Data.CommandData[index][1].equals("util")){
                System.out.println("Util command!");
                
                List<IRole> userRoles = event.getMessage().getAuthor().getRolesForGuild(event.getMessage().getGuild());
                String permissions = "";
                for(int x=0; x < userRoles.size();x++){
                    permissions = permissions+userRoles.get(x).getPermissions();
                }
                
                if(permissions.contains("MANAGE_ROLES")){
                    switch(Data.CommandData[index][0]){

                        case "!comreload":
                            Data.loadCommandControl();
                            JAMABot.discordClient.getChannelByID(ChannelIDString).sendMessage("Commands re-initialized!");
                            break;
                        case"!comdefine":
                            if(MessageParse.length==4){
                                JAMABot.discordClient.getChannelByID(ChannelIDString).sendMessage(Data.defineCommand(MessageParse[1],MessageParse[2],MessageParse[3]));
                            }else{
                                JAMABot.discordClient.getChannelByID(ChannelIDString).sendMessage("Invalid syntax. use \"!comdefine NAME TYPE ARGS\"");
                            }
                            break;
                        case "!volume":
                            Data.volume = Float.parseFloat(MessageParse[1]);
                            ClientControl.playAudioChannel.setVolume(Data.volume);
                            event.getMessage().delete();
                            break;
                    }         
                }else{
                    JAMABot.discordClient.getChannelByID(ChannelIDString).sendMessage("You do not have permission to execute utility commands. [REQUIRES MANAGE_ROLES]");
                }
            }else if(MessageParse[0].equals("!gethelp")){
                String message = "JAMABot commands -\n";
                for(int x=0; x<Data.CommandData.length;x++){
                    message = message+Data.CommandData[x][0]+" - "+Data.CommandData[x][1]+"\n";
                }
                JAMABot.discordClient.getChannelByID(ChannelIDString).sendMessage(message);
            }
            else if (MessageParse[0].equals("!randomsound")){
                int ind = 0;
                while (true){
                    ind = rnd.next(Data.CommandData);
                    if (CommandData[ind][1] != "util") break;
                }
                ClientControl.playSound(Data.CommandData[ind][2], event);
            }
        }  
    }  
}

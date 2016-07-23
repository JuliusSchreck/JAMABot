package util;

import sx.blah.discord.api.events.*;
import sx.blah.discord.handle.impl.events.*;

public class ReadyEventListener implements IListener<ReadyEvent>{
    
    @Override
    public void handle(ReadyEvent event){
        
        System.out.println("JAMABot is ready to go!");
        
    }
    
    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event){
        
        
        
    }
    
}

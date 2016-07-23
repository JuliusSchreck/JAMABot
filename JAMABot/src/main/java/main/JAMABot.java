package main;

import sx.blah.discord.api.*;
import util.*;

public class JAMABot {
    
    final static String TOKEN = "MjA0MzU4MjMyNjE1OTQ0MTky.CnKrYA.-5pEa7_e-IC0QGhj3ugjJAVeZKU";
    
    public static IDiscordClient discordClient;
    
    public static void main(String[] args) throws Exception{
        
        ClientControl control = new ClientControl();
        
        util.Data.loadCommandControl();
        
        discordClient = control.getClient(TOKEN);
        
        discordClient.getDispatcher().registerListener(new EventHandler());
        discordClient.getDispatcher().registerListener(new ReadyEventListener());

    }
    
}

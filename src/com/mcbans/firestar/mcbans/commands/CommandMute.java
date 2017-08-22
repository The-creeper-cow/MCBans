package com.mcbans.firestar.mcbans.commands;

import org.bukkit.command.CommandSender;

import com.mcbans.firestar.mcbans.callBacks.AltLookupCallback;
import com.mcbans.firestar.mcbans.permission.Perms;
import com.mcbans.firestar.mcbans.request.AltLookupRequest;

public class CommandMute extends BaseCommand{
	public CommandMute() {
		bePlayer = false;
		name = "MuteCommand";
		argLength = 2;
		usage = "Use to mute players";
		banning = true;
	}
    @Override
    public void execute() {
    	boolean isPerm = true;
    	args.remove(0); // remove target
    	if(args.size()>0) {
    		if(args.get(0).equalsIgnoreCase("t")) {
    			isPerm = false;
    		}else {
    			isPerm = true;
    		}
    	}
    	if(isPerm==true) {
    		
    	}
    }

    @Override
    public boolean permission(CommandSender sender) {
        return Perms.PERM_MUTE.has(sender);
    }
}

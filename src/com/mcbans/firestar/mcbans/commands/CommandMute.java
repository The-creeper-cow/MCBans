package com.mcbans.firestar.mcbans.commands;

import com.mcbans.firestar.mcbans.request.Mute;
import com.mcbans.firestar.mcbans.util.Util;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.command.CommandSender;

import com.mcbans.firestar.mcbans.callBacks.AltLookupCallback;
import com.mcbans.firestar.mcbans.permission.Perms;
import com.mcbans.firestar.mcbans.request.AltLookupRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
		Mute mute = null;
		args.remove(0); // remove target
		if (args.size() > 0) {
			if (args.get(0).equalsIgnoreCase("t")) {
				isPerm = false;

			} else {
				isPerm = true;
			}
		}
		if (isPerm == true) {
			String reason = config.getDefaultLocal();
			if (args.size() > 0) {
				reason = Util.join(args, " ");
			}
			mute = new Mute(target, targetUUID, senderName, senderUUID, reason, true, null);
		} else {
			String reason = config.getDefaultLocal();
			if (args.size() > 0) {
				reason = Util.join(args, " ");
			}
			String duration = args.remove(0);
			Date dateObj = new Date();

			try {
				Pattern regex = Pattern.compile("([0-9]+)(minute(s|)|m|hour(s|)|h|day(s|)|d|week(s|)|w)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
				Matcher regexMatcher = regex.matcher(duration);
				while (regexMatcher.find()) {
					duration = regexMatcher.group(1);

					switch (regexMatcher.group(2).toLowerCase().charAt(0)) {
						case 'm':
							dateObj = DateUtils.addMinutes(dateObj, Integer.parseInt(duration));
							break;
						case 'h':
							dateObj = DateUtils.addHours(dateObj, Integer.parseInt(duration));
							break;
						case 'd':
							dateObj = DateUtils.addDays(dateObj, Integer.parseInt(duration));
							break;
						case 'w':
							dateObj = DateUtils.addWeeks(dateObj, Integer.parseInt(duration));
							break;
						default:
							//TODO
							System.out.print(duration);
							break;
					}
				}
			} catch (PatternSyntaxException ex) {
			}
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateObj);

			mute = new Mute(target, targetUUID, senderName, senderUUID, reason, false, date);
			Thread triggerThread = new Thread(mute);
			triggerThread.start();
		}
	}

    @Override
    public boolean permission(CommandSender sender) {
        return Perms.PERM_MUTE.has(sender);
    }
}

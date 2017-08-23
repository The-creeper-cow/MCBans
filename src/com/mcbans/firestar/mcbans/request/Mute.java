package com.mcbans.firestar.mcbans.request;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mcbans.firestar.mcbans.MCBans;
import com.mcbans.firestar.mcbans.util.DBConnection;

public class Mute implements Runnable{
	private Date CurrentDate = new Date();
	private String playerName, PlayerUUID, senderName, senderUUID, reason, duration = null;
	private boolean isPerma = true;
	private boolean isUnmute = false;
	public Mute(String playerName, String PlayerUUID, String senderName, String senderUUID, String reason,boolean isPerma, String duration, boolean isUnmute) {
		this.playerName = playerName;
		this.PlayerUUID = PlayerUUID;
		this.senderName = senderName;
		this.senderUUID = senderUUID;
		this.reason = reason;
		this.isPerma = isPerma;
		this.duration = duration;
		this.isUnmute = isUnmute;
	}
	public void PermaMute() {
		Connection conn = DBConnection.Connect();
		// INSERT INTO `mcbans_mutes` (`id`, `uuid`, `perm`, `reason`, `until`) VALUES (NULL, 'test', '1', 'test', NULL);
		String sql = "INSERT INTO 'mcbans_mutes' ('id', 'uuid', 'perm', 'reason', 'until') VALUES (NULL, '"+PlayerUUID+"', '1', '"+reason+"', NULL";
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void TempMute() {
		Connection conn = DBConnection.Connect();
		//SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String sql = "INSERT INTO 'mcbans_mutes' ('id', 'uuid', 'perm', 'reason', 'until') VALUES (NULL, '"+PlayerUUID+"', '0', '"+reason+"', '"+duration+"'";
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void UnMute(){
		Connection conn = DBConnection.Connect();
		String sql = "DELETE FROM 'mcbans_mutes' WHERE";
	}
	@Override
	public void run() {
		if(isPerma == true) {
			PermaMute();
		}else if(isUnmute== true){

		}
	}
	
}

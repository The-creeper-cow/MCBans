package com.mcbans.firestar.mcbans.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.mcbans.firestar.mcbans.ConfigurationManager;

public class DBConnection {
	private static ConfigurationManager config;
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("MCBans");
	public static void Setup() {
		Logger logger = Logger.getLogger("Minecraft");
		if(config.isMysqlEnabled() == true) {
			final String driver = "com.mysql.jdbc.Driver";
			final String host = config.getDBaddr();
			final String DB = config.getDBName();
			final String url = "JDBC:mysql://"+host+DB;
			final String user = config.getDBUser();
			final String password = config.getDBPass();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, user, password);
				String table_mutes = "CREATE TABLE IF NOT EXISTS MCBans_Mutes \n"
						+"(id INTERGER NOT NULL AUTO_INCREMENT,\n"
						+"uuid TEXT(25) NOT NULL,\n"
						+"perm INTERGER DEFAULT 0,\n"
						+"reason TEXT(225)"
						+"until DATETIME,\n"
						+"PRIMARY KEY (id));";
				String table_warn = "CREATE TABLE IF NOT EXISTS MCBans_Warn \n"
						+"(id INTERGER NOT NULL AUTO_INCREMENT,\n"
						+"uuid TEXT(25) NOT NULL,\n"
						+"warnID INTERGER NOT NULL,\n"
						+"reason TEXT(225)";
				Statement stmt = conn.createStatement();
				stmt.execute(table_mutes);
				stmt.execute(table_warn);
				conn.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			final String url = "jdbc:sqlite:"+ plugin.getDataFolder().getAbsolutePath()+ File.separatorChar+"DB.db";
			try {
				Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				String table_mutes = "CREATE TABLE IF NOT EXISTS MCBans_Mutes \n"
						+"(id INTEGER NOT NULL AUTO_INCREMENT,\n"
						+"uuid TEXT(25) NOT NULL,\n"
						+"perm INTEGER DEFAULT 0,\n"
						+"reason TEXT(225),"
						+"until DATETIME,\n"
						+"PRIMARY KEY (id));";
				String table_warn = "CREATE TABLE IF NOT EXISTS MCBans_Warn \n"
						+"(id INTEGER NOT NULL AUTO_INCREMENT,\n"
						+"uuid TEXT(25) NOT NULL,\n"
						+"warnID INTEGER NOT NULL,\n"
						+"reason TEXT(225)";
				stmt.execute(table_mutes);
				stmt.execute(table_warn);
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static Connection Connect() {
		if(config.isMysqlEnabled() == true) {
			final String driver = "com.mysql.jdbc.Driver";
			final String host = config.getDBaddr();
			final String DB = config.getDBName();
			final String url = "JDBC:mysql://"+host+DB;
			final String user = config.getDBUser();
			final String password = config.getDBPass();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, user, password);
				return conn;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			final String url = "jdbc:sqlite:"+ plugin.getDataFolder().getAbsolutePath()+ File.separatorChar+"DB.db";
			try {
				Connection conn = DriverManager.getConnection(url);
				return conn;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}

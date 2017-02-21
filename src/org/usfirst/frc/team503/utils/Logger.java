package org.usfirst.frc.team503.utils;

import java.util.Calendar;
/*
 * Writes a formated message to the system log 
 */

public class Logger {

	public static void froglog(String contents, boolean isErr) 
	{
		if(isErr)
			System.err.println("[ERRLog] [" + Calendar.MINUTE + "mi " +Calendar.SECOND + "sec " + Calendar.MILLISECOND + "ms] " +contents);
		else
			System.out.println("[Log] [" + Calendar.MINUTE + "mi " +Calendar.SECOND + "sec " + Calendar.MILLISECOND + "ms] " +contents);
	}
	
	public static void froglog(String contents) 
	{
		froglog(contents, false);
	}
	
	public static void froglog(String contents, Exception e) 
	{
		froglog(contents, true);
		e.printStackTrace();
	}
}

package com.tdcrawl.tdc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.badlogic.gdx.utils.Logger;

public final class Reference
{
	public static boolean isDebug() { return true; };
	
	public static final int WINDOW_WIDTH = 720;
	public static final int WINDOW_HEIGHT = 480;
	public static final int FPS = 60;
	public static final String NAME = "The Dungeon Crawl";
	
	private static Logger debugLogger = new Logger("debug", Logger.DEBUG);
	private static Logger defaultLogger = new Logger("log", Logger.INFO);
	
	public static void handleError(Throwable ex)
	{
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		String fileName = "crash report - " + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE) + "-" + cal.get(Calendar.YEAR) + "_" + 
												cal.get(Calendar.HOUR_OF_DAY) + "-" + cal.get(Calendar.MINUTE) + "-" + cal.get(Calendar.SECOND) + ".txt";
		
		new File("crashes/").mkdirs();
		
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append(ex.toString() + System.lineSeparator());
		
		for(StackTraceElement e : ex.getStackTrace())
		{
			messageBuilder.append("\tat " + e + System.lineSeparator());
		}
		
		String message = messageBuilder.toString();
		
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter("crashes/" + fileName));
			bw.write(message);
			bw.close();
			
			ex.printStackTrace();
			
			System.err.println("Crash report saved to crashes/" + fileName);
		}
		catch (IOException ioEx)
		{
			System.err.println("UNABLE TO SAVE CRASH REPORT!");
			System.err.println("Saving error:");
			ioEx.printStackTrace();
			System.err.println("Crash report:");
			ex.printStackTrace();
		}
		
		System.exit(1);
	}

	public static void debugLog(String string, Object obj)
	{
		if(obj == null)
			debugLog("null> " + string);
		else
			debugLog(string, obj.getClass());
	}
	
	public static void debugLog(String string, Class<?> clazz)
	{
		debugLog(clazz.getName() + "> " + string);
	}
	
	private static void debugLog(String s)
	{
		debugLogger.info(s);
	}
	
	public static void log(String s)
	{
		defaultLogger.info(s);
	}
}

package com.tdcrawl.tdc.temp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Textifier
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter the directory to start doing the 1337 copies: ");
		String dir = scan.nextLine();
		scan.close();
		
		File folder = new File(dir);
		
		if(!folder.exists())
		{
			System.out.println("Bad directory.");
			return;
		}
		
		List<File> allFiles = new ArrayList<>();
		
		addFiles(folder, allFiles);
		
		String bigHunkOfFiles = "";
		
		for(File f : allFiles)
		{
			if(f.getName().endsWith(".java"))
			{
				try
				{
					BufferedReader br = new BufferedReader(new FileReader(f));
					
					bigHunkOfFiles += "\n=== " + f.getName().substring(0, f.getName().indexOf(".java")) + " ===\n";
					
					for(String line = br.readLine(); line != null; line = br.readLine())
					{
						bigHunkOfFiles += line + "\n";
					}
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		try
		{
			BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"));
			output.write(bigHunkOfFiles);
			output.close();
			System.out.println("Written to output.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
			System.out.println("Unable to write output file so just printing it instead.");
			System.out.println("===");
			System.out.println(bigHunkOfFiles);
		}
	}
	
	private static void addFiles(File folder, List<File> fileList)
	{
		List<File> directories = new ArrayList<>(); // So directories are added afterwards
		
		for(File file : folder.listFiles())
		{
			if(file.isDirectory())
			{
				directories.add(file);
			}
			else
			{
				fileList.add(file);
			}
		}
		
		// This way it lists the files in the directory it's currently in first before delving deeper too avoid a disorganized mess
		for(File file : directories)
		{
			addFiles(file, fileList);
		}
	}
}

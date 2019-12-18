package tow.engine;


import java.io.*;
import java.lang.ClassLoader;
import java.lang.String;
import java.lang.System;
import java.lang.reflect.Field;

public class Tester {


	public Tester(){
		try{
			System.out.println("1");
			File file = new File(getClass().getResource("doc.txt").getFile());
			System.out.println(file.getAbsolutePath());
			
			InputStream in = getClass().getResourceAsStream("doc.txt"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			System.out.println(reader.readLine());
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try{
			System.out.println("2");
			File file = new File(getClass().getResource("tow/engine/doc.txt").getFile());
			System.out.println(file.getAbsolutePath());
			
			InputStream in = getClass().getResourceAsStream("tow/engine/doc.txt"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			System.out.println(reader.readLine());
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try{
			System.out.println("3");
			File file = new File(getClass().getResource("/tow/engine/doc.txt").getFile());
			System.out.println(file.getAbsolutePath());
			
			InputStream in = getClass().getResourceAsStream("/tow/engine/doc.txt"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			System.out.println(reader.readLine());
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try{
			System.out.println("4");
			File file = new File(Thread.currentThread().getContextClassLoader().getResource("doc.txt").getFile());
			System.out.println(file.getAbsolutePath());
			
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("doc.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			System.out.println(reader.readLine());
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try{
			System.out.println("5");
			File file = new File(Thread.currentThread().getContextClassLoader().getResource("tow/engine/doc.txt").getFile());
			System.out.println(file.getAbsolutePath());
			
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("tow/engine/doc.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			System.out.println(reader.readLine());
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try{
			System.out.println("6");
			File file = new File(Thread.currentThread().getContextClassLoader().getResource("/tow/engine/doc.txt").getFile());
			System.out.println(file.getAbsolutePath());
			
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/tow/engine/doc.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			System.out.println(reader.readLine());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}

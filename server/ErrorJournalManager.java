package mnadyukov.server;

import java.io.*;
import java.util.*;

/**
*<p>
*��������� �������� ������.
*</p>
*� ������� ������ ������������ ������ ���� ������ ErrorJournalManager.
*<b>����� �������� �������, ������ ������ ���� ������ ����� {@link #init}.</b>
*<br>
*������ �� ������� �������� � ������ ������� ������.
*������������ ������ ������������ ���������� ERROR_JOURNAL_PATH (�������� ��������� ������ ������������� �������� '/').
*������������ ������ ������ ����� ������� ������������ ���������� ERROR_JOURNAL_MAX_FILE_SIZE (� ��).
*��� ���������� ������� ����� ������� ������������� �������, ��������� ����� ���� �������, ���������� ������ ���������� � ����� ����.
*��� ����� ������� ����� ��� ��������������������_��������������.err, ��� �������������������� ������������ ���������� SYSTEM_IDENTIFICATOR.
*��� ������ ������ � ������� ����� ������ ������ ��������� � System.err.
*<br>
*��������� ERROR_JOURNAL_PATH, ERROR_JOURNAL_MAX_FILE_SIZE, SYSTEM_IDENTIFICATOR ������ ���� ������� � ����� ��������� ����������.
*/
public class ErrorJournalManager{
	
	private static RandomAccessFile raf=null;
	private static long maxFileLen;
	private static Hashtable<String,String> SP;
	
	/**
	*<p>
	*��������� ������������� ������  ErrorJournalManager.
	*</p>
	*<b>������ ���������� ������, �� ������������� ������� ������</b>.
	*��� ������������� ������ � ������, ������ � ������� ������ �� ��������.
	*@param sp ������� ���������� �������.<br>
	*������������ ����� ����� ��� ���=�������� ����������, ������������ � ����� ��������� ����������.
	*@return true, ���� ������������� ������ �������, ����� - false.
	*/
	public static boolean init(Hashtable<String,String> sp){
		try{
			SP=sp;
			maxFileLen=(long)Integer.parseInt(SP.get("ERROR_JOURNAL_MAX_FILE_SIZE"))*1048576;
			File f=new File(SP.get("ERROR_JOURNAL_PATH"));
			if(!f.exists())f.mkdir();
			String[] fNames=f.list();
			if(fNames.length==0){
				if(!addJournalFile())return(false);
			}else{
				Arrays.sort(fNames);
				raf=new RandomAccessFile(SP.get("ERROR_JOURNAL_PATH")+fNames[fNames.length-1],"rw");
			}
			return(true);
		}catch(Exception e){
			System.err.println("mnadyukov.server.ErrorJournalManager.init: "+e);
			return(false);
		}
	}
	
	/**
	*<p>
	*������ ������ � ������� ������.
	*</p>
	*��� ������������� ������ � ������ ������, ������ � ������� ������ �� ��������.
	*@param err ����� ������ �� ������.
	*@return true, ���� ������ �����������, ����� - false.
	*/
	public static boolean write(String err){
		try{
			if(raf.length()>maxFileLen)if(!addJournalFile())return(false);
			String txt="At "+now()+": "+err+"\n";
			raf.write(txt.getBytes(SP.get("SERVER_ENCODING")));
			System.err.print(txt);
			return(true);
		}catch(Exception e){
			System.err.println("mnadyukov.server.ErrorJournalManager.write: "+e);
			return(false);
		}
	}
	
	/**
	*<p>
	*���������� ������ �� ������� ��������� ����������.
	*<p>
	*������������ ����� ������ Hashtable&lt;String,String&gt;, ��� ���� - ��� ���������, �������� - �������� ���������.
	*@return ������ �� ������� ��������� ����������.
	*/
	public static Hashtable<String,String> getSystemParameters(){
		return(SP);
	}
	
	/**
	*<p>
	*������� ����� ���� �������.
	*</p>
	*������ ����� ����� ��. � �������� ������.
	*@return true, ���� ���� ������ �������, ����� - false.
	*/
	private static boolean addJournalFile(){
		try{
			if(raf!=null)raf.close();
			raf=new RandomAccessFile(SP.get("ERROR_JOURNAL_PATH")+SP.get("SYSTEM_IDENTIFICATOR")+"_"+now()+".err","rw");
			return(true);
		}catch(Exception e){
			System.err.println("mnadyukov.server.ErrorJournalManager.addJournalFile: "+e);
			return(false);
		}
	}
	
	/**
	*<p>
	*���������� ������� ������ ������� � ������� ��������������.
	*</p>
	*@return �������� �������� ������� ������� � ��������� �������.
	*/
	private static String now(){
		int h,m,s,M,d;
		Calendar C=Calendar.getInstance();
		String res="";
		M=C.get(C.MONTH)+1;
		d=C.get(C.DAY_OF_MONTH);
		h=C.get(C.HOUR_OF_DAY);
		m=C.get(C.MINUTE);
		s=C.get(C.SECOND);
		res+=C.get(C.YEAR)+(M<10?"0"+M:""+M)+(d<10?"0"+d:""+d)+(h<10?"0"+h:""+h)+(m<10?"0"+m:""+m)+(s<10?"0"+s:""+s);		
		return(res);
	}
	
}

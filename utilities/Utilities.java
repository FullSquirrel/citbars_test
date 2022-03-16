package mnadyukov.utilities;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;
import mnadyukov.server.ErrorJournalManager;

/**
*<p>
*����� �������� ��������� ��������������� �������.
*</p>
*@author ������� ������
*/
public class Utilities{

	/**
	*<p>
	*������������� ���������� � ����� ������.
	*</p>
	*����� ���������� ��������� ��������� ���������:<ul><li>
	*	USER - ��� ������������ ���� ������;</li><li>
	*	PASSWORD - ������ ������� � ���� ������;</li><li>
	*	DB_ENCODING - ��������� ������ � ���� ������;</li><li>
	*	DB_HOST - ip ����� ���� ������;</li><li>
	*	DB_PORT - ����� ����� ���� ������;</li><li>
	*	DB_FILE - ������ ��� ����� ���� ������;</li></ul>
	*<br>
	*��������� USER, PASSWORD, DB_ENCODING, DB_HOST, DB_PORT, DB_FILE ������ ���� ������� � ����� ��������� ���������� (��. {@link #getSystemParameters getSystemParameters}).
	*@param SP ������� ��������� ����������.<br>
	*������������ ����� ������ Hashtable&lt;String,String&gt;, ��� ���� - ��� ���������, �������� - �������� ���������.
	*@return ������ Connection ���������� � ����� ������ ��� null, ���� � ������ ��������� ������ (����������).
	*/
	public static Connection openDBConnection(Hashtable<String,String> SP){
		Connection conn=null;
		try{
			Class.forName("org.firebirdsql.jdbc.FBDriver");
			Properties props=new Properties();
			props.setProperty("user",SP.get("USER"));
			props.setProperty("password",SP.get("PASSWORD"));
			props.setProperty("encoding",SP.get("DB_ENCODING"));
			conn=DriverManager.getConnection("jdbc:firebirdsql:"+SP.get("DB_HOST")+"/"+SP.get("DB_PORT")+":"+SP.get("DB_FILE"),props);
			return(conn);
		}catch(Exception e){
			closeDBConnection(conn);
			ErrorJournalManager.write("mnadyukov.utilities.Utilities.openDBConnection: "+e);
			return(null);
		}
		
	}
	
	/**
	*<p>
	*������������� ���������� � ����� ������, ��������� ������� ��������� ���������.
	*</p>
	*@return �������� ���������� � ����� ������.<br>
	*null, ���� ��������� ������ ���������� � ����� ������.
	*/
	public static Connection openDBConnection(){
		return(openDBConnection(ErrorJournalManager.getSystemParameters()));
	}
	
	/**
	*<p>
	*��������� �������� Connection ���������� � ����� ������.
	*</p>
	*@param conn ���������� Connection � ����� ������.
	*@return true, ���� �������� ���������� ������ �������, ����� - false.
	*/
	public static boolean closeDBConnection(Connection conn){
		try{
			if(conn!=null)conn.close();
			return(true);
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.utilities.Utilities.closeDBConnection: "+e);
			return(false);
		}
	}
	
	/**
	*<p>
	*��������� �������� ���������� ���������� Socket.
	*</p>
	*@param s �������� ���������� ����������.
	*@return true, ���� �������� ��������� �������, ����� - false.
	*/
	public static boolean closeClientConnection(Socket s){
		try{
			if(s!=null)s.close();
			return(true);
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.utilities.Utilities.closeClientConnection: "+e);
			return(false);
		}
	}
	
	/**
	*<p>
	*������� ������, ���������� ��������� ���������.
	*</p>
	*��������� ��������� ����������� �� ���������� �����.
	*������ ������ ����� �������� ���� ��������� �������� � ������� ��� �������� (��� � �������� ��������� ��������).
	*���, ��� ���� � ������ ����� �������� (����� ������) ������������.
	*������ ������ ������������.
	*@param sysPrmFile ������ ���� � ����� ��������� ����������.
	*@return ���������� ������ Hashtable&lt;String,String&gt; ��������� ���������� ��� null, ���� ��������� ������ (����������).<br>
	*������ ��������� ���������� �������� ����� ��� (���,��������), ��� ��� - ��� ���������� ��������� (����), �������� - �������� ���������� ���������.
	*/
	public static Hashtable<String,String> getSystemParameters(String sysPrmFile){
		RandomAccessFile raf=null;
		try{
			File f=new File(sysPrmFile);
			if(!f.exists())return(null);
			raf=new RandomAccessFile(f,"r");
			String str;
			String[] tokens;
			Hashtable<String,String> res=new Hashtable();
			while(true){
				str=raf.readLine();
				if(str==null)break;
				if(str.isEmpty())continue;
				tokens=str.split(" ");
				if(tokens.length<2)throw new Exception("not enough parameters");
				res.put(tokens[0],tokens[1]);
			}
			return(res);
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Utilities.getSystemParameters: "+e);
			return(null);
		}finally{
			try{
				if(raf!=null)raf.close();
			}catch(Exception e){
				System.err.println("mnadyukov.utilities.Utilities.getSystemParameters.finally: "+e);
			}
		}
	}
	
	/**
	*<p>
	*���������� ��������� ������������� ���� � ������� ����.��.��.
	*</p>
	*@param msTime ������ ������� � ������������� � 1970.01.01 00:00:00.
	*@return ��������� ������������� ���� ��� ���������� ������� �������.<br>
	*null ���� ��������� ������ (����������).
	*/
	public static String getSQLDate(long msTime){
		try{
			Calendar C=Calendar.getInstance();
			C.setTimeInMillis(msTime);
			String res;
			int Y=C.get(Calendar.YEAR);
			int M=C.get(Calendar.MONTH)+1;
			int D=C.get(Calendar.DAY_OF_MONTH);
			res=Y+"."+(M<10?"0"+M:M)+"."+(D<10?"0"+D:D);
			return(res);
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Utilities.getSQLDate: "+e);
			return(null);
		}
	}

}
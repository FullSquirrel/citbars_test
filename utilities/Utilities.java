package mnadyukov.utilities;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;
import mnadyukov.server.ErrorJournalManager;

/**
*<p>
*Класс содержит различные вспомогательные функции.
*</p>
*@author Надюков Михаил
*/
public class Utilities{

	/**
	*<p>
	*Устанавливает соединение с базой данных.
	*</p>
	*Метод использует следующие системные параметры:<ul><li>
	*	USER - имя пользователя базы данных;</li><li>
	*	PASSWORD - пароль доступа к базе данных;</li><li>
	*	DB_ENCODING - кодировка текста в базе данных;</li><li>
	*	DB_HOST - ip адрес базы данных;</li><li>
	*	DB_PORT - номер порта базы данных;</li><li>
	*	DB_FILE - полное имя файла базы данных;</li></ul>
	*<br>
	*Параметры USER, PASSWORD, DB_ENCODING, DB_HOST, DB_PORT, DB_FILE должны быть описаны в файле системных параметров (см. {@link #getSystemParameters getSystemParameters}).
	*@param SP Таблица системных параметров.<br>
	*Представляет собой объект Hashtable&lt;String,String&gt;, где ключ - имя параметра, значение - значение параметра.
	*@return Объект Connection соединения с базой данных или null, если в методе произошла ошибка (исключение).
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
	*Устанавливает соединение с базой данных, используя текущие системные параметры.
	*</p>
	*@return Открытое соединение с базой данных.<br>
	*null, если произошла ошибка соединения с базой данных.
	*/
	public static Connection openDBConnection(){
		return(openDBConnection(ErrorJournalManager.getSystemParameters()));
	}
	
	/**
	*<p>
	*Закрывает открытое Connection соединение с базой данных.
	*</p>
	*@param conn Соединение Connection с базой данных.
	*@return true, если закрытие соединения прошло успешно, иначе - false.
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
	*Закрывает открытое клиентское соединение Socket.
	*</p>
	*@param s Открытое клиентское соединение.
	*@return true, если закрытие произошло успешно, иначе - false.
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
	*Создает объект, содержащий системные параметры.
	*</p>
	*Системные параметры считываются из указанного файла.
	*Каждая строка файла содержит один системный параметр в формате ИМЯ ЗНАЧЕНИЕ (ИМЯ и ЗНАЧЕНИЕ разделены пробелом).
	*Все, что идет в строке после ЗНАЧЕНИЕ (через пробел) игнорируется.
	*Пустые строки игнорируются.
	*@param sysPrmFile Полный путь к файлу системных параметров.
	*@return Возвращает объект Hashtable&lt;String,String&gt; системных параметров или null, если произошла ошибка (исключение).<br>
	*Объект системных параметров содержит набор пар (Имя,Значение), где Имя - имя системного параметра (ключ), Значение - значение системного параметра.
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
	*Возвращает строковое представление даты в формате ГГГГ.ММ.ДД.
	*</p>
	*@param msTime Момент времени в миллисекундах с 1970.01.01 00:00:00.
	*@return Текстовое представление даты для указанного момента времени.<br>
	*null если произошла ошибка (исключение).
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
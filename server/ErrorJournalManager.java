package mnadyukov.server;

import java.io.*;
import java.util.*;

/**
*<p>
*Управляет журналом ошибок.
*</p>
*В системе должен существовать только один объект ErrorJournalManager.
*<b>После создания объекта, первым должен быть вызван метод {@link #init}.</b>
*<br>
*Записи об ошибках делаются в файлах журнала ошибок.
*Расположение файлов определяется параметром ERROR_JOURNAL_PATH (значение параметра должно заканчиваться символом '/').
*Максимальный размер одного файла журнала определяется параметром ERROR_JOURNAL_MAX_FILE_SIZE (в Мб).
*При превышении размера файла журнала максимального размера, создается новый файл журнала, дальнейшая запись происходит в новый файл.
*Имя файла журнала имеет вид ИдентификаторСистемы_ГГГГММДДччммсс.err, где ИдентификаторСистемы определяется параметром SYSTEM_IDENTIFICATOR.
*При записи ошибки в журнале также данная ошибка выводится в System.err.
*<br>
*Параметры ERROR_JOURNAL_PATH, ERROR_JOURNAL_MAX_FILE_SIZE, SYSTEM_IDENTIFICATOR должны быть описаны в файле системных параметров.
*/
public class ErrorJournalManager{
	
	private static RandomAccessFile raf=null;
	private static long maxFileLen;
	private static Hashtable<String,String> SP;
	
	/**
	*<p>
	*Выполняет инициализацию класса  ErrorJournalManager.
	*</p>
	*<b>Должен вызываться первым, до использования методов класса</b>.
	*При возникновении ошибки в методе, запись в журнале ошибок не делается.
	*@param sp Таблица параметров системы.<br>
	*Представляет собой набор пар имя=значение параметров, содержащихся в файле системных параметров.
	*@return true, если инициализация прошла успешно, иначе - false.
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
	*Делает запись в журнале ошибок.
	*</p>
	*При возникновении ошибки в данном методе, запись в журнале ошибок не делается.
	*@param err Текст записи об ошибке.
	*@return true, если запись произведена, иначе - false.
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
	*Возвращает ссылку на таблицу системных параметров.
	*<p>
	*Представляет собой объект Hashtable&lt;String,String&gt;, где ключ - имя параметра, значение - значение параметра.
	*@return Ссылка на таблицу системных параметров.
	*/
	public static Hashtable<String,String> getSystemParameters(){
		return(SP);
	}
	
	/**
	*<p>
	*Создает новый файл журнала.
	*</p>
	*Формат имени файла см. в описании класса.
	*@return true, если файл создан успешно, иначе - false.
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
	*Возвращает текущий момент времени в формате ГГГГММДДччммсс.
	*</p>
	*@return Значение текущего момента времени в указанном формате.
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

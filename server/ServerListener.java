package mnadyukov.server;

import java.net.*;
import java.util.*;
import java.io.*;
import mnadyukov.utilities.*;

/**
*<p>
*Осуществляет прослушивание порта системы.
*</p>
*Номер порта указан в системном параметре SERVER_PORT в файле системных параметров.
*Клиенты должны отправлять сообщения на этот порт.
*@author Надюков Михаил
*/
public class ServerListener{
	
	private Hashtable<String,String> SP;
	
	/**
	*<p>
	*Создает объект ServerListener, инициализирует систему, запускает прослушивание порта системы.
	*</p>
	*@param sysPrmFile Полное имя файла системных параметров.
	*/
	public ServerListener(String sysPrmFile){
		SP=Utilities.getSystemParameters(sysPrmFile);
		if(SP==null){
			System.err.println("mnadyukov.server.ServerListener: error while reading system parameters file");
			return;
		}
		if(!ErrorJournalManager.init(SP)){
			System.err.println("mnadyukov.server.ServerListener: error while init ErrorJournalManager");
			return;
		}
	}
	
	/**
	*<p>
	*Прослушивает порт системы.
	*</p>
	*При получении клиентского соединения, создается объект {@link mnadyukov.server.ServerHandler ServerHandler}, который обрабатывает сооединение.
	*Данному объекту передается клиентское соединение.
	*Объект {@link mnadyukov.server.ServerHandler ServerHandler} запускается в отдельном потоке.
	*/
	public void start(){
		ServerSocket SS;
		try{
			SS=new ServerSocket(Integer.parseInt(SP.get("SERVER_PORT")));
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.ServerListener.start: wrong server port");
			return;
		}
		Socket cs;
		while(true){
			try{
				cs=SS.accept();
			}catch(Exception e){
				ErrorJournalManager.write("mnadyukov.server.ServerListener.start: error while accepting client connection");
				continue;
			}
			ServerHandler SH=new ServerHandler(cs);
			new Thread(SH).start();
		}
	}
	
	/**
	*<p>
	*Запускает серверную часть приложения.
	*</p>
	*@param args Аргументы командной строки, переданные в метод.
	*args[0] должен содержать полный путь к файлу системных параметров.
	*@throws java.lang.Exception Если произошла ошибка (исключение) времени исполнения.
	*/
	public static void main(String[] args)throws Exception{
		ServerListener sl=new ServerListener(args[0]);
		sl.start();
	}
	
}
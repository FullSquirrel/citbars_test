package mnadyukov.server;

import java.util.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.lang.reflect.*;
import mnadyukov.utilities.*;
import mnadyukov.server.services.*;

/**
*<p>
*Обрабатывает полученное от клиента сообщение.
*</p>
*Объект данного класса создается объектом ServerListener при каждом получении соединения с клиентом.
*Созданный объект выполняет следующие действия:<ul><li>
*	выделяет сообщение из пакета, полученного от клиента (при помощи объекта ServerCommunicator);</li><li>
*	преобразует сообщение в объект {@link mnadyukov.utilities.Structure Structure};</li><li>
*	создает объект-обработчик полученной команды (рефлексивно);</li><li>
*	вызывает метод {@link mnadyukov.server.services.Service#execute execute} созданного объекта и передает ему объект {@link mnadyukov.utilities.Structure Structure}.</li></ul>
*<br>
*Каждый объект ServerHandler работает в собственном потоке.<br>
*Все классы-обработчики должны находиться в пакете mnadyukov.server.services.
*@author Надюков Михаил
*/
public class ServerHandler implements Runnable{
	
	/**
	*Объект, содержащий системные параметры.
	*/
	private Hashtable<String,String> SP;
	
	/**
	*Соединение с клиентом.
	*/
	private Socket CS;
	
	/**
	*Объект, осуществляющий взаимодействие с клиентом (отправку / получение сообщений).
	*/
	private ServerCommunicator SC;
	
	/**
	*Соединение с базой данных.
	*/
	private Connection conn;
	
	/**
	*Создает объект ServerHandler.
	*@param cs Клиентское соединение.
	*/
	public ServerHandler(Socket cs){
		try{
			SP=ErrorJournalManager.getSystemParameters();
			CS=cs;
			conn=Utilities.openDBConnection(SP);
			SC=new ServerCommunicator(conn,null);
		}catch(Exception e){
			System.err.println("mnadyukov.server.ServerHandler.ServerHandler: "+e);
		}
	}
	
	/**
	*<p>
	*Метод, вызываемый при запуске данного объекта на выполнение.
	*</p>
	*Определяет действия, предписанные полученным сообщением и выполняет их.
	*/
	public void run(){
		try{
			String msg=SC.receive(CS);
			if(msg==null){
				ErrorJournalManager.write("mnadyukov.server.ServerHandler.run: error client data");
				return;
			}
			Structure str=Structure.izStroki(msg);
			if(str==null){
				ErrorJournalManager.write("mnadyukov.server.ServerHandler.run: error while structuring client data");
				return;
			}
			Class cls=Class.forName("mnadyukov.server.services."+str.getZnachenie());
			Constructor constr=cls.getConstructor(ServerCommunicator.class,Socket.class,Connection.class);
			Service srv=(Service)constr.newInstance(SC,CS,conn);
			if(!srv.execute(str)){
				ErrorJournalManager.write("mnadyukov.server.ServerHandler.run: error in service "+str.poluchitPoduzel(0).getZnachenie());
			}
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.ServerHandler.run: "+e);
		}finally{
			Utilities.closeDBConnection(conn);
			Utilities.closeClientConnection(CS);
		}
		
	}
	
}
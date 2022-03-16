package mnadyukov.client;

import java.lang.reflect.*;
import mnadyukov.utilities.*;
import mnadyukov.client.gui.*;

/**
*<p>
*Класс-обработчик команд, поступивших от сервера.
*</p>
*Данные от сервера поступают в строковом формате (см. описание класса {@link mnadyukov.utilities.Structure Structure}).
*Из поступивших данных определяется команда.
*Создается экземпляр объекта типа {@link mnadyukov.client.gui.ExecutorGUI ExecutorGUI}, соответствующий команде.
*Для созданного экземпляра вызывается метод {@link mnadyukov.client.gui.ExecutorGUI#execute execute}.<br>
*Создание экземпляра требуемого класса выполняется при помощи рефлексии.<br>
*Все классы, обеспечивающие выполнение команд, должны находиться в пакете mnadyukov.client.gui.
*@author Надюков Михаил
*/
public class ClientHandler{
	
	/**
	*Клиентское приложение, с которым связан данный объект.
	*/
	private Prilojenie P;
	
	/**
	*<p>
	*Создает объект ClientHandler, связывает его с клиентским приложением.
	*</p>
	*@param p Клиентское приложение, с которым связывается данный объект.
	*/
	public ClientHandler(Prilojenie p){
		P=p;
	}
	
	/**
	*<p>
	*Метод, обрабатывающий команды, поступившие от сервера.
	*</p>
	*@param data Данные, поступившие от сервера.
	*/
	public void handle(String data){
		try{
			Structure str=Structure.izStroki(data);
			Class cls=Class.forName("mnadyukov.client.gui."+str.getZnachenie());
			Constructor C=cls.getConstructor();
			ExecutorGUI exec=(ExecutorGUI)C.newInstance();
			exec.execute(P,str);
		}catch(Exception e){
			System.err.println("mnadyukov.client.ClientHandler.handle: "+e);
		}
	}

}
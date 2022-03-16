package mnadyukov.server.services;

import java.net.*;
import java.sql.*;
import mnadyukov.utilities.*;
import mnadyukov.server.*;

/**
*<p>
*Класс, от которого должны быть наследованы все классы-обработчики команд.
*</p>
*/
public abstract class Service{

	/**
	*Объект, осуществляющий взаимодействие с клиентом (отправку / получение сообщений).
	*/
	protected ServerCommunicator SC;
	
	/**
	*Соединение с клиентом.
	*/
	protected Socket CS;
	
	/**
	*Соединение с базой данных.
	*/
	protected Connection conn;

	/**
	*<p>
	*Общий конструктор объектов-обработчиков команд.
	*</p>
	*@param sc Объект, осуществляющий взаимодействие с клиентом (отправку / получение сообщений).
	*@param cs Соединение с клиентом.
	*@param conn Соединение с базой данных.
	*/
	public Service(ServerCommunicator sc, Socket cs, Connection conn){
		SC=sc;
		CS=cs;
		this.conn=conn;
	}
	
	/**
	*<p>
	*Выполняет команду, определяемую переданным методу объектом {@link mnadyukov.utilities.Structure Structure}.
	*</p>
	*@param str Структура, содержащая данные, необходимые для выполнения команды.
	*@return true, если команда была успешно выполнена.<br>
	*false, если произошла ошибка (исключение).
	*/
	public abstract boolean execute(Structure str);

}
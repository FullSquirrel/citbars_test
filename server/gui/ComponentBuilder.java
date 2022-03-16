package mnadyukov.server.gui;

import mnadyukov.utilities.*;
import java.util.*;

/**
*<p>
*Абстрактный класс, от которого должны быть наследованы все классы-построители элементов графического интерфейса пользователя.
*</p>
*Классы-построители предназначены для задания параметров соответствующего элемента GUI и преобразования его в объект {@link mnadyukov.utilities.Structure Structure}.
*Полученный объект {@link mnadyukov.utilities.Structure Structure} преобразуется элементом GUI верхнего уровня (обычно Stage) в строковое представление, которое будет отправлено клиенту.
*Клиент из полученного строкового представления строит элемент GUI. 
*/
public abstract class ComponentBuilder{
	
	/**
	*Элементы GUI, подчиненные данному элементу.
	*/
	protected ArrayList<ComponentBuilder> components;
	
	/**
	*<p>
	*Общий конструктор объектов ComponentBuilder.
	*</p>
	*/
	public ComponentBuilder(){
		components=new ArrayList();
	}
	
	/**
	*<p>
	*Преобразует текущий элемент в объект {@link mnadyukov.utilities.Structure Structure}.
	*</p>
	*@return Объект {@link mnadyukov.utilities.Structure Structure}
	*/
	public abstract Structure buildComponent();

}
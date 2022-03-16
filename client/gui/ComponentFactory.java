package mnadyukov.client.gui;

import java.lang.reflect.*;
import javafx.scene.*;
import mnadyukov.utilities.*;

/**
*<p>
*Класс-фабрика, предназначенный для построения JavaFX компонентов.
*</p>
*/
public class ComponentFactory{
	
	/**
	*<p>
	*Определяет тип элемента графического интерфейса пользователя и возвращает построенный элемент.
	*</p>
	*Для построения элемента рефлексивно создается объект соответствующего класса-построителя (объект типа {@link mnadyukov.client.gui.ComponentGUI ComponentGUI}).
	*@param str Структура, содержащая все данные, необходимые для построения элемента.
	*@return Построенный элемент графического интерфейса пользователя.<br>
	*null, если произошла ошибка (исключение).
	*/
	public static Parent createComponent(Structure str){
		try{
			String comp=str.getZnachenie();
			Class cls=Class.forName("mnadyukov.client.gui."+comp+"GUI");
			Constructor C=cls.getConstructor();
			ComponentGUI compGui=(ComponentGUI)C.newInstance();
			return(compGui.getComponent(str));
		}catch(Exception e){
			System.err.println("mnadyukov.client.gui.ComponentFactory.createComponent: "+e);
			return(null);
		}
	}

}
package mnadyukov.client.gui;

import javafx.scene.*;
import mnadyukov.utilities.*;

/**
*<p>
*»нтерфейс всех классов, выполн€ющих построение элементов графического интерфейса пользовател€.
*</p>
*/
public interface ComponentGUI{
	
	/**
	*<p>
	*ћетод, выполн€ющий построение элемента графического интерфейса пользовател€.
	*</p>
	*@param str —труктура, содержаща€ все данные, необходимые дл€ построени€ элемента.
	*@return Ёлемент графического интерфейса пользовател€.<br>
	*null, если произошла ошибка (исключение).
	*/
	public Parent getComponent(Structure str);

}
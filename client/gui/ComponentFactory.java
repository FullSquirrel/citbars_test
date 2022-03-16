package mnadyukov.client.gui;

import java.lang.reflect.*;
import javafx.scene.*;
import mnadyukov.utilities.*;

/**
*<p>
*�����-�������, ��������������� ��� ���������� JavaFX �����������.
*</p>
*/
public class ComponentFactory{
	
	/**
	*<p>
	*���������� ��� �������� ������������ ���������� ������������ � ���������� ����������� �������.
	*</p>
	*��� ���������� �������� ����������� ��������� ������ ���������������� ������-����������� (������ ���� {@link mnadyukov.client.gui.ComponentGUI ComponentGUI}).
	*@param str ���������, ���������� ��� ������, ����������� ��� ���������� ��������.
	*@return ����������� ������� ������������ ���������� ������������.<br>
	*null, ���� ��������� ������ (����������).
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
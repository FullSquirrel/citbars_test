package mnadyukov.server.gui;

import mnadyukov.utilities.*;
import java.util.*;

/**
*<p>
*����������� �����, �� �������� ������ ���� ����������� ��� ������-����������� ��������� ������������ ���������� ������������.
*</p>
*������-����������� ������������� ��� ������� ���������� ���������������� �������� GUI � �������������� ��� � ������ {@link mnadyukov.utilities.Structure Structure}.
*���������� ������ {@link mnadyukov.utilities.Structure Structure} ������������� ��������� GUI �������� ������ (������ Stage) � ��������� �������������, ������� ����� ���������� �������.
*������ �� ����������� ���������� ������������� ������ ������� GUI. 
*/
public abstract class ComponentBuilder{
	
	/**
	*�������� GUI, ����������� ������� ��������.
	*/
	protected ArrayList<ComponentBuilder> components;
	
	/**
	*<p>
	*����� ����������� �������� ComponentBuilder.
	*</p>
	*/
	public ComponentBuilder(){
		components=new ArrayList();
	}
	
	/**
	*<p>
	*����������� ������� ������� � ������ {@link mnadyukov.utilities.Structure Structure}.
	*</p>
	*@return ������ {@link mnadyukov.utilities.Structure Structure}
	*/
	public abstract Structure buildComponent();

}
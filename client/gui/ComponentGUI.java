package mnadyukov.client.gui;

import javafx.scene.*;
import mnadyukov.utilities.*;

/**
*<p>
*��������� ���� �������, ����������� ���������� ��������� ������������ ���������� ������������.
*</p>
*/
public interface ComponentGUI{
	
	/**
	*<p>
	*�����, ����������� ���������� �������� ������������ ���������� ������������.
	*</p>
	*@param str ���������, ���������� ��� ������, ����������� ��� ���������� ��������.
	*@return ������� ������������ ���������� ������������.<br>
	*null, ���� ��������� ������ (����������).
	*/
	public Parent getComponent(Structure str);

}
package mnadyukov.client;

import java.lang.reflect.*;
import mnadyukov.utilities.*;
import mnadyukov.client.gui.*;

/**
*<p>
*�����-���������� ������, ����������� �� �������.
*</p>
*������ �� ������� ��������� � ��������� ������� (��. �������� ������ {@link mnadyukov.utilities.Structure Structure}).
*�� ����������� ������ ������������ �������.
*��������� ��������� ������� ���� {@link mnadyukov.client.gui.ExecutorGUI ExecutorGUI}, ��������������� �������.
*��� ���������� ���������� ���������� ����� {@link mnadyukov.client.gui.ExecutorGUI#execute execute}.<br>
*�������� ���������� ���������� ������ ����������� ��� ������ ���������.<br>
*��� ������, �������������� ���������� ������, ������ ���������� � ������ mnadyukov.client.gui.
*@author ������� ������
*/
public class ClientHandler{
	
	/**
	*���������� ����������, � ������� ������ ������ ������.
	*/
	private Prilojenie P;
	
	/**
	*<p>
	*������� ������ ClientHandler, ��������� ��� � ���������� �����������.
	*</p>
	*@param p ���������� ����������, � ������� ����������� ������ ������.
	*/
	public ClientHandler(Prilojenie p){
		P=p;
	}
	
	/**
	*<p>
	*�����, �������������� �������, ����������� �� �������.
	*</p>
	*@param data ������, ����������� �� �������.
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
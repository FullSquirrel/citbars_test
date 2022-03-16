package mnadyukov.server.services;

import java.net.*;
import java.sql.*;
import mnadyukov.utilities.*;
import mnadyukov.server.*;

/**
*<p>
*�����, �� �������� ������ ���� ����������� ��� ������-����������� ������.
*</p>
*/
public abstract class Service{

	/**
	*������, �������������� �������������� � �������� (�������� / ��������� ���������).
	*/
	protected ServerCommunicator SC;
	
	/**
	*���������� � ��������.
	*/
	protected Socket CS;
	
	/**
	*���������� � ����� ������.
	*/
	protected Connection conn;

	/**
	*<p>
	*����� ����������� ��������-������������ ������.
	*</p>
	*@param sc ������, �������������� �������������� � �������� (�������� / ��������� ���������).
	*@param cs ���������� � ��������.
	*@param conn ���������� � ����� ������.
	*/
	public Service(ServerCommunicator sc, Socket cs, Connection conn){
		SC=sc;
		CS=cs;
		this.conn=conn;
	}
	
	/**
	*<p>
	*��������� �������, ������������ ���������� ������ �������� {@link mnadyukov.utilities.Structure Structure}.
	*</p>
	*@param str ���������, ���������� ������, ����������� ��� ���������� �������.
	*@return true, ���� ������� ���� ������� ���������.<br>
	*false, ���� ��������� ������ (����������).
	*/
	public abstract boolean execute(Structure str);

}
package mnadyukov.server;

import java.util.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.lang.reflect.*;
import mnadyukov.utilities.*;
import mnadyukov.server.services.*;

/**
*<p>
*������������ ���������� �� ������� ���������.
*</p>
*������ ������� ������ ��������� �������� ServerListener ��� ������ ��������� ���������� � ��������.
*��������� ������ ��������� ��������� ��������:<ul><li>
*	�������� ��������� �� ������, ����������� �� ������� (��� ������ ������� ServerCommunicator);</li><li>
*	����������� ��������� � ������ {@link mnadyukov.utilities.Structure Structure};</li><li>
*	������� ������-���������� ���������� ������� (�����������);</li><li>
*	�������� ����� {@link mnadyukov.server.services.Service#execute execute} ���������� ������� � �������� ��� ������ {@link mnadyukov.utilities.Structure Structure}.</li></ul>
*<br>
*������ ������ ServerHandler �������� � ����������� ������.<br>
*��� ������-����������� ������ ���������� � ������ mnadyukov.server.services.
*@author ������� ������
*/
public class ServerHandler implements Runnable{
	
	/**
	*������, ���������� ��������� ���������.
	*/
	private Hashtable<String,String> SP;
	
	/**
	*���������� � ��������.
	*/
	private Socket CS;
	
	/**
	*������, �������������� �������������� � �������� (�������� / ��������� ���������).
	*/
	private ServerCommunicator SC;
	
	/**
	*���������� � ����� ������.
	*/
	private Connection conn;
	
	/**
	*������� ������ ServerHandler.
	*@param cs ���������� ����������.
	*/
	public ServerHandler(Socket cs){
		try{
			SP=ErrorJournalManager.getSystemParameters();
			CS=cs;
			conn=Utilities.openDBConnection(SP);
			SC=new ServerCommunicator(conn,null);
		}catch(Exception e){
			System.err.println("mnadyukov.server.ServerHandler.ServerHandler: "+e);
		}
	}
	
	/**
	*<p>
	*�����, ���������� ��� ������� ������� ������� �� ����������.
	*</p>
	*���������� ��������, ������������ ���������� ���������� � ��������� ��.
	*/
	public void run(){
		try{
			String msg=SC.receive(CS);
			if(msg==null){
				ErrorJournalManager.write("mnadyukov.server.ServerHandler.run: error client data");
				return;
			}
			Structure str=Structure.izStroki(msg);
			if(str==null){
				ErrorJournalManager.write("mnadyukov.server.ServerHandler.run: error while structuring client data");
				return;
			}
			Class cls=Class.forName("mnadyukov.server.services."+str.getZnachenie());
			Constructor constr=cls.getConstructor(ServerCommunicator.class,Socket.class,Connection.class);
			Service srv=(Service)constr.newInstance(SC,CS,conn);
			if(!srv.execute(str)){
				ErrorJournalManager.write("mnadyukov.server.ServerHandler.run: error in service "+str.poluchitPoduzel(0).getZnachenie());
			}
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.ServerHandler.run: "+e);
		}finally{
			Utilities.closeDBConnection(conn);
			Utilities.closeClientConnection(CS);
		}
		
	}
	
}
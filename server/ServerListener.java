package mnadyukov.server;

import java.net.*;
import java.util.*;
import java.io.*;
import mnadyukov.utilities.*;

/**
*<p>
*������������ ������������� ����� �������.
*</p>
*����� ����� ������ � ��������� ��������� SERVER_PORT � ����� ��������� ����������.
*������� ������ ���������� ��������� �� ���� ����.
*@author ������� ������
*/
public class ServerListener{
	
	private Hashtable<String,String> SP;
	
	/**
	*<p>
	*������� ������ ServerListener, �������������� �������, ��������� ������������� ����� �������.
	*</p>
	*@param sysPrmFile ������ ��� ����� ��������� ����������.
	*/
	public ServerListener(String sysPrmFile){
		SP=Utilities.getSystemParameters(sysPrmFile);
		if(SP==null){
			System.err.println("mnadyukov.server.ServerListener: error while reading system parameters file");
			return;
		}
		if(!ErrorJournalManager.init(SP)){
			System.err.println("mnadyukov.server.ServerListener: error while init ErrorJournalManager");
			return;
		}
	}
	
	/**
	*<p>
	*������������ ���� �������.
	*</p>
	*��� ��������� ����������� ����������, ��������� ������ {@link mnadyukov.server.ServerHandler ServerHandler}, ������� ������������ �����������.
	*������� ������� ���������� ���������� ����������.
	*������ {@link mnadyukov.server.ServerHandler ServerHandler} ����������� � ��������� ������.
	*/
	public void start(){
		ServerSocket SS;
		try{
			SS=new ServerSocket(Integer.parseInt(SP.get("SERVER_PORT")));
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.ServerListener.start: wrong server port");
			return;
		}
		Socket cs;
		while(true){
			try{
				cs=SS.accept();
			}catch(Exception e){
				ErrorJournalManager.write("mnadyukov.server.ServerListener.start: error while accepting client connection");
				continue;
			}
			ServerHandler SH=new ServerHandler(cs);
			new Thread(SH).start();
		}
	}
	
	/**
	*<p>
	*��������� ��������� ����� ����������.
	*</p>
	*@param args ��������� ��������� ������, ���������� � �����.
	*args[0] ������ ��������� ������ ���� � ����� ��������� ����������.
	*@throws java.lang.Exception ���� ��������� ������ (����������) ������� ����������.
	*/
	public static void main(String[] args)throws Exception{
		ServerListener sl=new ServerListener(args[0]);
		sl.start();
	}
	
}
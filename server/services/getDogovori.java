package mnadyukov.server.services;

import java.net.*;
import java.sql.*;
import java.util.*;
import mnadyukov.utilities.*;
import mnadyukov.server.*;
import mnadyukov.server.dbase.*;
import mnadyukov.server.gui.*;

/**
*<p>
*��������� � ���������� ������������ ���� c �������� ���� ���������.
*</p>
*������ ������ ������� ��������:<ul><li>
*	����� ��������;</li><li>
*	���� ��������;</li><li>
*	������� ������������ �������� (� ���� �������� CheckBox).</li></ul>
*<br>
*��������� ��������:<ul><li>
*	���.</li></ul>
*<br>
*@author ������� ������
*/
public class getDogovori extends Service{
	
	/**
	*������� ������ getDogovori.
	*@param cm ������, ����� ������� �������������� ����� � ��������.
	*@param sc ���������� � ��������.
	*@param cn ���������� � ����� ������, ��������� �������.
	*������ ����� ��������� ���� ���������� � ����� ������, ������� �� ������ ������� ���.
	*���������� cn ������ ��������� �� ������.
	*/
	public getDogovori(ServerCommunicator cm, Socket sc, Connection cn){
		super(cm,sc,cn);
	}
	
	/**
	*��������� � ���������� ������������ ���� c �������� ���� ���������.
	*@param str �������� ��������� ��� ���������� �������.
	*@return true, ���� ������������ � �������� ���� ����������� �������.<br>
	*false, ���� ��������� ������ (����������).
	*/
	public boolean execute(Structure str){
		try{
			ArrayList<Object[]> res=new mnadyukov.server.dbase.getDogovori(conn).execute(null);
			if(res==null)throw new Exception("error while mnadyukov.server.dbase.getDogovori");
			long now=Calendar.getInstance().getTimeInMillis();
			Stage stg=null;
			if(res.size()==0){
				stg=new Stage("�������� �� �������",400,300);
			}else{
				String[][] data=new String[res.size()][3];
				String[] row;
				for(int i=0;i<res.size();i++){
					row=new String[3];
					row[0]=""+(long)res.get(i)[0];
					row[1]=Utilities.getSQLDate((long)res.get(i)[1]);
					row[2]=((now-(long)res.get(i)[2])<86400000*60)?"1":"0";//�������� �������� �� ������������
					data[i]=row;
				}
				Table tbl=new Table();
				tbl.addColumn("����� ��������",true,false);
				tbl.addColumn("���� ��������",true,false);
				tbl.addColumn("��������",true,true);
				tbl.setData(data);
				stg=new Stage("��������",800,600);
				AnchorPane ap=new AnchorPane(800,600);
				ScrollPane sp=new ScrollPane(800,600,true,true,ScrollPane.ALWAYS,ScrollPane.ALWAYS);
				sp.addComponent(tbl);
				ap.addComponent(sp,0.0,0.0,0.0,0.0);
				stg.addComponent(ap);
			}
			return(SC.send(stg.buildComponent().vStroku()));
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.services.getDogovori.execute: "+e);
			return(false);
		}
	}
	
}
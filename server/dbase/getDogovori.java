package mnadyukov.server.dbase;

import java.sql.*;
import java.util.*;
import mnadyukov.server.*;
import mnadyukov.utilities.*;

/**
*<p>
*���������� ������ ���� ���������.
*</p>
*������ ������������ ������:<ul><li>
*	����� ��������;</li><li>
*	���� �������� (long);</li><li>
*	���� ���������� �������� (long).</li></ul>
*<br>
*��������� �������:<ul><li>
*	���.</li></ul>
*@author ������� ������
*/
public class getDogovori extends SQLStatement{
	
	/**
	*������� ������ getDogovori.
	*@param cnn ���������� � ����� ������, ����� ������� ����� ����������� �������.
	*/
	public getDogovori(Connection cnn){
		super(cnn);
	}
	
	/**
	*���������� ������ ���� ���������.
	*@param params �������� ��������� �������.
	*@return ������ ��������� ������.<br>
	*null, ���� ��������� ������ (����������).
	*/
	public ArrayList<Object[]> execute(ArrayList<String> params){
		try{
			Result=new ArrayList();
			String	query=	"SELECT "+
								"d.Nomer, "+
								"d.DataDogovora, "+
								"d.DataObnovleniya "+
							"FROM "+
								"Dogovori d "+
							";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			while(rs.next()){
				Object[] row=new Object[3];
				row[0]=rs.getLong(1);
				row[1]=rs.getDate(2).getTime();
				row[2]=rs.getDate(3).getTime();
				Result.add(row);
			}
			return(Result);
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.dbase.getDogovori.execute: "+e);
			return(null);
		}
	}
	
}
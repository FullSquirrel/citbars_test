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
*Формирует и отправляет пользователю окно c таблицей всех договоров.
*</p>
*Каждая строка таблицы содержит:<ul><li>
*	номер договора;</li><li>
*	дату договора;</li><li>
*	признак актуальности договора (в виде элемента CheckBox).</li></ul>
*<br>
*Параметры процесса:<ul><li>
*	нет.</li></ul>
*<br>
*@author Надюков Михаил
*/
public class getDogovori extends Service{
	
	/**
	*Создает объект getDogovori.
	*@param cm Объект, через который осуществляется связь с клиентом.
	*@param sc Соединение с клиентом.
	*@param cn Соединение с базой данных, доступное объекту.
	*Объект может создавать свои соединения с базой данных, которые он обязан закрыть сам.
	*Соединение cn объект закрывать не должен.
	*/
	public getDogovori(ServerCommunicator cm, Socket sc, Connection cn){
		super(cm,sc,cn);
	}
	
	/**
	*Формирует и отправляет пользователю окно c таблицей всех договоров.
	*@param str Содержит параметры для выполнения команды.
	*@return true, если формирование и отправка окна произведена успешно.<br>
	*false, если произошла ошибка (исключение).
	*/
	public boolean execute(Structure str){
		try{
			ArrayList<Object[]> res=new mnadyukov.server.dbase.getDogovori(conn).execute(null);
			if(res==null)throw new Exception("error while mnadyukov.server.dbase.getDogovori");
			long now=Calendar.getInstance().getTimeInMillis();
			Stage stg=null;
			if(res.size()==0){
				stg=new Stage("Договоры не найдены",400,300);
			}else{
				String[][] data=new String[res.size()][3];
				String[] row;
				for(int i=0;i<res.size();i++){
					row=new String[3];
					row[0]=""+(long)res.get(i)[0];
					row[1]=Utilities.getSQLDate((long)res.get(i)[1]);
					row[2]=((now-(long)res.get(i)[2])<86400000*60)?"1":"0";//проверка договора на актуальность
					data[i]=row;
				}
				Table tbl=new Table();
				tbl.addColumn("Номер договора",true,false);
				tbl.addColumn("Дата договора",true,false);
				tbl.addColumn("Актуален",true,true);
				tbl.setData(data);
				stg=new Stage("Договоры",800,600);
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
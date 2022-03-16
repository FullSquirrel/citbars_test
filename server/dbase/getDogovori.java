package mnadyukov.server.dbase;

import java.sql.*;
import java.util.*;
import mnadyukov.server.*;
import mnadyukov.utilities.*;

/**
*<p>
*Возвращает данные всех договоров.
*</p>
*Формат возвращаемых данных:<ul><li>
*	номер договора;</li><li>
*	дата договора (long);</li><li>
*	дата обновления договора (long).</li></ul>
*<br>
*Параметры запроса:<ul><li>
*	нет.</li></ul>
*@author Надюков Михаил
*/
public class getDogovori extends SQLStatement{
	
	/**
	*Создает объект getDogovori.
	*@param cnn Соединение с базой данных, через которое могут выполняться запросы.
	*/
	public getDogovori(Connection cnn){
		super(cnn);
	}
	
	/**
	*Возвращает данные всех договоров.
	*@param params Содержит параметры запроса.
	*@return Массив найденных данных.<br>
	*null, если произошла ошибка (исключение).
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
package mnadyukov.server.gui;

import mnadyukov.utilities.*;
import mnadyukov.server.*;
import java.util.*;

/**
*<p>
*Класс-построитель элемента TableView.
*</p>
*/
public class Table extends ComponentBuilder{
	
	/**
	*<p>
	*Класс, представляющий свойства столбца таблицы.
	*</p>
	*/
	class ColumnProperties{
		
		private String title;
		private boolean isResizable;
		private boolean isCheckBoxed;
		
		/**
		*<p>
		*Создает объект ColumnProperties.
		*</p>
		*@param title Заголовок столбца.
		*@param isResizable Признак возможности изменения ширины столбца.
		*@param isCheckBoxed Признак того, что значение в ячейках столбца представлено элементом CheckBox.<br>
		*Если текстовое значение ячейки равно "1", то CheckBox будет отмечен как выбранный, иначе CheckBox будет отмечен как не выбранный.
		*/
		public ColumnProperties(String title, boolean isResizable, boolean isCheckBoxed){
			this.title=title;
			this.isResizable=isResizable;
			this.isCheckBoxed=isCheckBoxed;
		}
		
		/**
		*<p>
		*Возвращает значение title.
		*</p>
		*@return Значение title.
		*/
		public String getTitle(){
			return(title);
		}
		
		/**
		*<p>
		*Возвращает значение isResizable.
		*</p>
		*@return Значение isResizable.
		*/
		public boolean getResizable(){
			return(isResizable);
		}
		
		/**
		*<p>
		*Возвращает значение isCheckBoxed.
		*</p>
		*@return Значение isCheckBoxed.
		*/
		public boolean getCheckBoxed(){
			return(isCheckBoxed);
		}
	}
	
	private String[][] Data;
	private ArrayList<ColumnProperties> columns;
	
	/**
	*<p>
	*Создает объект Table.
	*</p>
	*/
	public Table(){
		super();
		columns=new ArrayList();
		Data=null;
	}
	
	public Structure buildComponent(){
		try{
			if(Data==null)return(null);
			if(columns.size()==0)return(null);
			Structure str=new Structure();
			str.setZnachenie("TableView");
			Structure props=new Structure();
			str.dobavitPoduzel(props);
			Structure cols=new Structure();
			str.dobavitPoduzel(cols);
			Structure col;
			for(int i=0;i<columns.size();i++){
				col=new Structure();
				col.setZnachenie(columns.get(i).getTitle());
				col.dobavitZnachenie(columns.get(i).getResizable()?"1":"0");
				col.dobavitZnachenie(columns.get(i).getCheckBoxed()?"1":"0");
				cols.dobavitPoduzel(col);
			}
			Structure data=new Structure();
			str.dobavitPoduzel(data);
			String dat="";
			String row;
			for(int i=0;i<Data.length;i++){
				row="";
				for(int j=0;j<Data[0].length;j++){
					row+="\u0003"+Data[i][j];
				}
				row=row.substring(1);
				dat+="\u0004"+row;
			}
			dat=dat.substring(1);
			data.dobavitZnachenie(dat);
			return(str);
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.gui.Table.buildComponent: "+e);
			return(null);
		}
	}
	
	/**
	*<p>
	*Добавляет в таблицу столбец.
	*</p>
	*@param title Заголовок столбца.
	*@param isResizable Признак возможности изменения ширины столбца.
	*@param isCheckBoxed Признак того, что значение в ячейках столбца представлено элементом CheckBox.<br>
	*Если текстовое значение ячейки равно "1", то CheckBox будет отмечен как выбранный, иначе CheckBox будет отмечен как не выбранный.
	*/
	public void addColumn(String title, boolean isResizable, boolean isCheckBoxed){
		ColumnProperties col=new ColumnProperties(title,isResizable,isCheckBoxed);
		columns.add(col);
	}
	
	/**
	*<p>
	*Присоединяет к таблице данные.
	*</p>
	*@param data Присоединяемые данные.
	*/
	public void setData(String[][] data){
		Data=data;
	}
	
}
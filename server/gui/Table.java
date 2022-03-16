package mnadyukov.server.gui;

import mnadyukov.utilities.*;
import mnadyukov.server.*;
import java.util.*;

/**
*<p>
*�����-����������� �������� TableView.
*</p>
*/
public class Table extends ComponentBuilder{
	
	/**
	*<p>
	*�����, �������������� �������� ������� �������.
	*</p>
	*/
	class ColumnProperties{
		
		private String title;
		private boolean isResizable;
		private boolean isCheckBoxed;
		
		/**
		*<p>
		*������� ������ ColumnProperties.
		*</p>
		*@param title ��������� �������.
		*@param isResizable ������� ����������� ��������� ������ �������.
		*@param isCheckBoxed ������� ����, ��� �������� � ������� ������� ������������ ��������� CheckBox.<br>
		*���� ��������� �������� ������ ����� "1", �� CheckBox ����� ������� ��� ���������, ����� CheckBox ����� ������� ��� �� ���������.
		*/
		public ColumnProperties(String title, boolean isResizable, boolean isCheckBoxed){
			this.title=title;
			this.isResizable=isResizable;
			this.isCheckBoxed=isCheckBoxed;
		}
		
		/**
		*<p>
		*���������� �������� title.
		*</p>
		*@return �������� title.
		*/
		public String getTitle(){
			return(title);
		}
		
		/**
		*<p>
		*���������� �������� isResizable.
		*</p>
		*@return �������� isResizable.
		*/
		public boolean getResizable(){
			return(isResizable);
		}
		
		/**
		*<p>
		*���������� �������� isCheckBoxed.
		*</p>
		*@return �������� isCheckBoxed.
		*/
		public boolean getCheckBoxed(){
			return(isCheckBoxed);
		}
	}
	
	private String[][] Data;
	private ArrayList<ColumnProperties> columns;
	
	/**
	*<p>
	*������� ������ Table.
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
	*��������� � ������� �������.
	*</p>
	*@param title ��������� �������.
	*@param isResizable ������� ����������� ��������� ������ �������.
	*@param isCheckBoxed ������� ����, ��� �������� � ������� ������� ������������ ��������� CheckBox.<br>
	*���� ��������� �������� ������ ����� "1", �� CheckBox ����� ������� ��� ���������, ����� CheckBox ����� ������� ��� �� ���������.
	*/
	public void addColumn(String title, boolean isResizable, boolean isCheckBoxed){
		ColumnProperties col=new ColumnProperties(title,isResizable,isCheckBoxed);
		columns.add(col);
	}
	
	/**
	*<p>
	*������������ � ������� ������.
	*</p>
	*@param data �������������� ������.
	*/
	public void setData(String[][] data){
		Data=data;
	}
	
}
package mnadyukov.client.gui;

import java.util.*;
import javafx.util.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import mnadyukov.utilities.*;

/**
*<p>
*Класс, предназначенный для построения JavaFX компонента TableView.
*</p>
*/
public class TableViewGUI implements ComponentGUI{
	
	private TableView<ArrayList<String>> comp;
	
	public TableViewGUI(){
		comp=null;
	}
	
	//Table{{}{col1Title{COL1}...}{DATA}}
	//COL:=title{isResizable{}isCheckBox{}}
	//DATA:=cell1_1\u0003cell1_2\u0003...\u0004cell2_1\u0003...{}
	public Parent getComponent(Structure str){
		try{
			Structure propStr=str.poluchitPoduzel(0);
			Structure colsStr=str.poluchitPoduzel(1);
			Structure dataStr=str.poluchitPoduzel(2);
			comp=new TableView();
			Structure col;
			String colTitle;
			boolean isResizable, isCheckBox;
			for(int i=0;;i++){
				col=colsStr.poluchitPoduzel(i);
				if(col==null)break;
				colTitle=col.getZnachenie();
				isResizable=col.poluchitPoduzel(0).getZnachenie().equals("1");
				isCheckBox=col.poluchitPoduzel(1).getZnachenie().equals("1");
				switch(i){
					case 0:{
						if(isCheckBox){
							TableColumn<ArrayList<String>,CheckBox> tCol=new TableColumn(colTitle);
							tCol.setCellValueFactory(cd->{
								CheckBox chb=new CheckBox();
								chb.setSelected(cd.getValue().get(0).equals("1"));
								return(new ReadOnlyObjectWrapper(chb));
							});
							tCol.setComparator(new Comparator<CheckBox>(){
								public int compare(CheckBox chb1, CheckBox chb2){
									if(!chb1.isSelected() && chb2.isSelected())return(-1);
									if(chb1.isSelected() && !chb2.isSelected())return(1);
									return(0);
								}
							});
							tCol.setResizable(isResizable);
							comp.getColumns().add(tCol);
						}else{
							TableColumn<ArrayList<String>,String> tCol=new TableColumn(colTitle);
							tCol.setCellValueFactory(cd->{return(new ReadOnlyObjectWrapper<String>(cd.getValue().get(0)));});
							tCol.setResizable(isResizable);
							comp.getColumns().add(tCol);
						}
						break;
					}
					case 1:{
						if(isCheckBox){
							TableColumn<ArrayList<String>,CheckBox> tCol=new TableColumn(colTitle);
							tCol.setCellValueFactory(cd->{
								CheckBox chb=new CheckBox();
								chb.setSelected(cd.getValue().get(1).equals("1"));
								return(new ReadOnlyObjectWrapper(chb));
							});
							tCol.setComparator(new Comparator<CheckBox>(){
								public int compare(CheckBox chb1, CheckBox chb2){
									if(!chb1.isSelected() && chb2.isSelected())return(-1);
									if(chb1.isSelected() && !chb2.isSelected())return(1);
									return(0);
								}
							});
							tCol.setResizable(isResizable);
							comp.getColumns().add(tCol);
						}else{
							TableColumn<ArrayList<String>,String> tCol=new TableColumn(colTitle);
							tCol.setCellValueFactory(cd->{return(new ReadOnlyObjectWrapper<String>(cd.getValue().get(1)));});
							tCol.setResizable(isResizable);
							comp.getColumns().add(tCol);
						}
						break;
					}
					case 2:{
						if(isCheckBox){
							TableColumn<ArrayList<String>,CheckBox> tCol=new TableColumn(colTitle);
							tCol.setCellValueFactory(cd->{
								CheckBox chb=new CheckBox();
								chb.setSelected(cd.getValue().get(2).equals("1"));
								return(new ReadOnlyObjectWrapper(chb));
							});
							tCol.setComparator(new Comparator<CheckBox>(){
								public int compare(CheckBox chb1, CheckBox chb2){
									if(!chb1.isSelected() && chb2.isSelected())return(-1);
									if(chb1.isSelected() && !chb2.isSelected())return(1);
									return(0);
								}
							});
							tCol.setResizable(isResizable);
							comp.getColumns().add(tCol);
						}else{
							TableColumn<ArrayList<String>,String> tCol=new TableColumn(colTitle);
							tCol.setCellValueFactory(cd->{return(new ReadOnlyObjectWrapper<String>(cd.getValue().get(2)));});
							tCol.setResizable(isResizable);
							comp.getColumns().add(tCol);
						}
						break;
					}
				}
			}
			ObservableList<ArrayList<String>> dat=FXCollections.<ArrayList<String>>observableArrayList();
			String[] datStr=dataStr.poluchitPoduzel(0).getZnachenie().split("\u0004");
			String[] row;
			ArrayList<String> row_list;
			for(int i=0;i<datStr.length;i++){
				row=datStr[i].split("\u0003");
				row_list=new ArrayList();
				for(int j=0;j<row.length;j++)row_list.add(row[j]);
				dat.add(row_list);
			}
			comp.setItems(dat);
			return(comp);
		}catch(Exception e){
			System.err.println("mnadyukov.client.gui.TableViewGUI.getComponent: "+e);
			return(null);
		}
	}

}
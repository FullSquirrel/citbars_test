package mnadyukov.server.gui;

import mnadyukov.utilities.*;
import mnadyukov.server.*;
import java.util.*;

/**
*<p>
*Класс-построитель элемента AnchorPane.
*</p>
*/
public class AnchorPane extends ComponentBuilder{

	private double pref_width;
	private double pref_height;
	private ArrayList<Structure> anchors;
	
	/**
	*<p>
	*Создает объект AnchorPane.
	*</p>
	*@param pref_w Значение свойства Region.prefWidth.
	*@param pref_h Значение свойства Region.prefHeight.
	*/
	public AnchorPane(double pref_w, double pref_h){
		super();
		pref_width=pref_w;
		pref_height=pref_h;
		anchors=new ArrayList();
	}
	
	public Structure buildComponent(){
		try{
			Structure str=new Structure();
			str.setZnachenie("AnchorPane");
			Structure props=new Structure();
			props.dobavitZnachenie(""+pref_width);
			props.dobavitZnachenie(""+pref_height);
			str.dobavitPoduzel(props);
			Structure p;
			for(int i=0;i<components.size();i++){
				p=components.get(i).buildComponent();
				if(p!=null){
					str.dobavitPoduzel(p);
					str.dobavitPoduzel(anchors.get(i));
				}
			}
			return(str);
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.gui.AnchorPane.buildComponent: "+e);
			return(null);
		}
	}
	
	/**
	*<p>
	*Добавляет вложенный элемент.
	*</p>
	*@param comp Добавляемый элемент.
	*@param tAnchor Значение ограничения AnchorPane.topAnchor.
	*@param rAnchor Значение ограничения AnchorPane.rightAnchor.
	*@param bAnchor Значение ограничения AnchorPane.bottomAnchor.
	*@param lAnchor Значение ограничения AnchorPane.leftAnchor.
	*/
	public void addComponent(ComponentBuilder comp, double tAnchor, double rAnchor, double bAnchor, double lAnchor){
		try{
			Structure anchrs=new Structure();
			anchrs.dobavitZnachenie(""+tAnchor);
			anchrs.dobavitZnachenie(""+rAnchor);
			anchrs.dobavitZnachenie(""+bAnchor);
			anchrs.dobavitZnachenie(""+lAnchor);
			anchors.add(anchrs);
			components.add(comp);
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.gui.AnchorPane.addComponent: "+e);
		}
	}
	
}
package mnadyukov.server.gui;

import mnadyukov.utilities.*;
import mnadyukov.server.*;
import java.util.*;
import javafx.scene.*;
import javafx.scene.control.*;

/**
*<p>
*Класс-построитель элемента ScrollPane.
*</p>
*/
public class ScrollPane extends ComponentBuilder{

	private double pref_width;
	private double pref_height;
	private boolean fit_to_width;
	private boolean fit_to_height;
	private int hbar_policy;
	private int vbar_policy;
	private ComponentBuilder content;
	
	/**
	*Аналог константы ScrollPane.ScrollBarPolicy.NEVER
	*/
	public static final int NEVER=1;
	
	/**
	*Аналог константы ScrollPane.ScrollBarPolicy.ALWAYS
	*/
	public static final int ALWAYS=2;
	
	/**
	*Аналог константы ScrollPane.ScrollBarPolicy.AS_NEEDED
	*/
	public static final int AS_NEEDED=3;
	
	/**
	*<p>
	*Создает объект ScrollPane.
	*</p>
	*@param pref_w Значение свойства Region.prefWidth.
	*@param pref_h Значение свойства Region.prefHeight.
	*@param fit_w Значение свойства ScrollPane.fitToWidth.
	*@param fit_h Значение свойства ScrollPane.fitToHeight.
	*@param h_bar Значение свойства ScrollPane.hbarPolicy.
	*@param v_bar Значение свойства ScrollPane.vbarPolicy.
	*/
	public ScrollPane(double pref_w, double pref_h, boolean fit_w, boolean fit_h, int h_bar, int v_bar){
		super();
		pref_width=pref_w;
		pref_height=pref_h;
		fit_to_width=fit_w;
		fit_to_height=fit_h;
		hbar_policy=h_bar;
		vbar_policy=v_bar;		
		content=null;
	}
	
	public Structure buildComponent(){
		try{
			Structure str=new Structure();
			str.setZnachenie("ScrollPane");
			Structure props=new Structure();
			props.dobavitZnachenie(""+pref_width);
			props.dobavitZnachenie(""+pref_height);
			props.dobavitZnachenie(fit_to_width?"1":"0");
			props.dobavitZnachenie(fit_to_height?"1":"0");
			props.dobavitZnachenie(""+hbar_policy);
			props.dobavitZnachenie(""+vbar_policy);
			str.dobavitPoduzel(props);
			Structure p=null;
			if(content!=null)p=content.buildComponent();
			if(p!=null)str.dobavitPoduzel(p);
			return(str);
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.gui.ScrollPane.buildComponent: "+e);
			return(null);
		}
	}
	
	/**
	*<p>
	*Добавляет вложенный элемент.
	*</p>
	*@param comp Добавляемый элемент.
	*/
	public void addComponent(ComponentBuilder comp){
		try{
			content=comp;
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.gui.ScrollPane.addComponent: "+e);
		}
	}
	
}
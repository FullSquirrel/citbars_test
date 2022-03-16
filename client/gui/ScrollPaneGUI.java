package mnadyukov.client.gui;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import mnadyukov.utilities.*;

/**
*<p>
*Класс, предназначенный для построения JavaFX компонента ScrollPane.
*</p>
*/
public class ScrollPaneGUI implements ComponentGUI{

	private ScrollPane pane;
	
	public static final int NEVER=1;
	public static final int ALWAYS=2;
	public static final int AS_NEEDED=3;
	
	public ScrollPaneGUI(){
		pane=null;
	}
	
	//ScrollPane{{pref_w{}pref_h{}fit_w{}fit_h{}h_bar{}v_bar{}}ROOT}
	public Parent getComponent(Structure str){
		try{
			Structure propStr=str.poluchitPoduzel(0);
			double pref_w=Double.parseDouble(propStr.poluchitPoduzel(0).getZnachenie());
			double pref_h=Double.parseDouble(propStr.poluchitPoduzel(1).getZnachenie());
			boolean fit_w=propStr.poluchitPoduzel(2).getZnachenie().equals("1");
			boolean fit_h=propStr.poluchitPoduzel(3).getZnachenie().equals("1");
			int h_bar=Integer.parseInt(propStr.poluchitPoduzel(4).getZnachenie());
			int v_bar=Integer.parseInt(propStr.poluchitPoduzel(5).getZnachenie());
			Structure rootStr=str.poluchitPoduzel(1);
			Parent p=ComponentFactory.createComponent(rootStr);
			if(p!=null){
				pane=new ScrollPane();
				pane.setContent(p);
				if(pref_w!=-1)pane.setPrefViewportWidth(pref_w);
				if(pref_h!=-1)pane.setPrefViewportHeight(pref_h);
				pane.setFitToWidth(fit_w);
				pane.setFitToHeight(fit_h);
				switch(h_bar){
					case NEVER:{
						pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
						break;
					}
					case ALWAYS:{
						pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
						break;
					}
					case AS_NEEDED:{
						pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
						break;
					}
				}
				switch(v_bar){
					case NEVER:{
						pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
						break;
					}
					case ALWAYS:{
						pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
						break;
					}
					case AS_NEEDED:{
						pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
						break;
					}
				}
			}
			return(pane);
		}catch(Exception e){
			System.err.println("mnadyukov.client.gui.ScrollPaneGUI.getComponent: "+e);
			return(null);
		}
	}

}
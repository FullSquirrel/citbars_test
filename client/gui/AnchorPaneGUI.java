package mnadyukov.client.gui;

import javafx.scene.*;
import javafx.scene.layout.*;
import mnadyukov.utilities.*;

/**
*<p>
*Класс, предназначенный для построения JavaFX компонента AnchorPane.
*</p>
*/
public class AnchorPaneGUI implements ComponentGUI{
	
	private AnchorPane pane;
	
	public AnchorPaneGUI(){
		pane=null;
	}
	
	//AnchorPane{{width{}height{}}par1{PAR1}{anchorTop{}anchorRight{}anchorBottom{}anchorLeft{}}...}
	public Parent getComponent(Structure str){
		try{		
			Structure paneStr=str.poluchitPoduzel(0);
			double width=Double.parseDouble(paneStr.poluchitPoduzel(0).getZnachenie());
			double height=Double.parseDouble(paneStr.poluchitPoduzel(1).getZnachenie());
			pane=new AnchorPane();
			if(width!=-1)pane.setPrefWidth(width);
			if(height!=-1)pane.setPrefHeight(height);
			Parent p;
			Structure parStr, propStr;
			for(int i=1;;i+=2){
				parStr=str.poluchitPoduzel(i);
				if(parStr==null)break;
				p=ComponentFactory.createComponent(parStr);
				pane.getChildren().add(p);
				propStr=str.poluchitPoduzel(i+1);
				if(propStr==null)break;
				double aTop=Double.parseDouble(propStr.poluchitPoduzel(0).getZnachenie());
				double rTop=Double.parseDouble(propStr.poluchitPoduzel(1).getZnachenie());
				double bTop=Double.parseDouble(propStr.poluchitPoduzel(2).getZnachenie());
				double lTop=Double.parseDouble(propStr.poluchitPoduzel(3).getZnachenie());
				if(aTop!=-1)pane.setTopAnchor(p,aTop);
				if(rTop!=-1)pane.setRightAnchor(p,rTop);
				if(bTop!=-1)pane.setBottomAnchor(p,bTop);
				if(lTop!=-1)pane.setLeftAnchor(p,lTop);
			}
			return(pane);
		}catch(Exception e){
			System.err.println("mnadyukov.client.gui.AnchorPaneGUI.addComponent: "+e);
			return(null);
		}
	}

}
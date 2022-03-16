package mnadyukov.client.gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import mnadyukov.client.*;
import mnadyukov.utilities.*;

/**
*<p>
*Класс, выполняющий команду сервера 'Stage'.
*</p>
*Команда 'Stage' представляет собой команду построения окна.<br>
*Класс является микросервисом.
*@author Надюков Михаил
*/
public class StageGUI implements ExecutorGUI{

	public StageGUI(){
		
	}

	//StageGUI{{title{}width{}height{}}root{{ROOT}par1{PAR1}{PAR1_ROOT}par2{PAR2}{PAR2_ROOT}...}}
	public void execute(Prilojenie p, Structure str){
		try{
			Structure stgParams=str.poluchitPoduzel(0);
			String stgTitle=stgParams.poluchitPoduzel(0).getZnachenie();
			String stgWidth=stgParams.poluchitPoduzel(1).getZnachenie();
			String stgHeight=stgParams.poluchitPoduzel(2).getZnachenie();
			Structure rootStr=str.poluchitPoduzel(1);
			Parent root=ComponentFactory.createComponent(rootStr);
			if(root==null)return;
			Scene scene=new Scene(root,Double.parseDouble(stgWidth),Double.parseDouble(stgHeight));
			Stage stg=new Stage();
			stg.setScene(scene);
			stg.setTitle(stgTitle);
			p.newWindow(stg);
		}catch(Exception e){
			System.err.println("mnadyukov.client.gui.StageGUI.execute: "+e);
		}
	}

}
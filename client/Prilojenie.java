package mnadyukov.client;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.control.cell.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.util.*;
import javafx.event.*;
import java.util.*;

/**
*<p>
*Класс представлят собой главное окно клиентской части приложения.
*</p>
*Главное окно содержит панель команд и главную панель.<br>
*Панель команд содержит команды, доступные пользователю.
*При нажатии на кнопку команды открывается отдельное окно, в которое выводится результат выполнения команды.
*В тестовой версии реализована только команда 'Документы', открывающая таблицу документов.<br>
*Главная панель представляет собой рабочий стол пользователя и содержит наиболее важную оперативную информацию.
*В тестовой версии главная панель не реализована.
*Для запуска клиентской части приложения должен быть запущен на выполнение данный класс.
*@author Надюков Михаил
*/
public class Prilojenie extends Application{
	
	/**
	*Объект, через который осуществляется обмен (отправка и прием) сообщениями с серверной частью приложения.
	*/
	private ClientCommunicator CC;
	
	public static void main(String[] args){
		Prilojenie P=new Prilojenie();
		Application.launch();
	}
	
	/**
	*Создает объект Prilojenie.
	*/
	public Prilojenie(){
		CC=new ClientCommunicator(this);
	}
	
	/**
	*<p>
	*Данный метод вызывается при запуске клиентской части приложения.
	*</p>
	*Метод формирует главное окно клиентской части приложения.
	*@param stg Основное окно клиентской части приложения.
	*/
	public void start(Stage stg){
		try{
			HBox root=new HBox();
			root.setPadding(new Insets(5));
			root.setSpacing(5);
			
			ScrollPane kom=new ScrollPane();
			kom.setBorder(new Border(new BorderStroke(Color.GREY,BorderStrokeStyle.SOLID,null,null)));
			kom.setMinWidth(100);
			kom.setMaxWidth(505);
			kom.setFitToWidth(true);
			kom.setFitToHeight(true);
			root.getChildren().add(kom);
			root.setHgrow(kom,Priority.ALWAYS);
			
			VBox kom_p=new VBox();
			kom_p.setSpacing(2);
			kom_p.setMinWidth(300);
			kom_p.setMaxWidth(500);
			kom_p.setFillWidth(true);
			kom.setContent(kom_p);
			
			Label kom_z=new Label("Команды");
			kom_z.setFont(Font.font("Sans",13));
			kom_z.setAlignment(Pos.CENTER);
			kom_z.setMaxWidth(Double.MAX_VALUE);
			kom_z.setBackground(new Background(new BackgroundFill(Color.SILVER,null,null)));
			kom_p.getChildren().add(kom_z);
			
			Button kom_dogovori=new Button("Договоры");
			kom_dogovori.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent e){
					CC.send("getDogovori\u0001\u0002");
				}
			});
			kom_dogovori.setMaxWidth(Double.MAX_VALUE);
			kom_p.getChildren().add(kom_dogovori);
			
			VBox pan=new VBox();
			root.getChildren().add(pan);
			root.setHgrow(pan,Priority.ALWAYS);
			pan.setSpacing(2);
			pan.setMinSize(300,300);
			pan.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
			pan.setFillWidth(true);
			pan.setBorder(new Border(new BorderStroke(Color.GREY,BorderStrokeStyle.SOLID,null,null)));
			
			Label pan_z=new Label("Главная панель");
			pan_z.setFont(Font.font("Sans",13));
			pan_z.setAlignment(Pos.CENTER);
			pan_z.setMaxWidth(Double.MAX_VALUE);
			pan_z.setBackground(new Background(new BackgroundFill(Color.SILVER,null,null)));
			pan.getChildren().add(pan_z);

			Scene scene=new Scene(root,1000,600);
			stg.setTitle("Тестовое приложение. Надюков М.А.");
			stg.setScene(scene);
			stg.show();
		}catch(Exception e){
			System.err.println("mnadyukov.client.Prilojenie.start: "+e);
		}		
	}
	
	/**
	*<p>
	*Открывает модально (с блокированием всего приложения) указанное окно.
	*</p>
	*@param wnd Открываемое окно.
	*/
	public void newWindow(Stage wnd){
		wnd.initModality(Modality.APPLICATION_MODAL);
		wnd.show();
	}
	
}
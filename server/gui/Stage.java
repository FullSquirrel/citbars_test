package mnadyukov.server.gui;

import mnadyukov.utilities.*;

/**
*<p>
*�����-����������� �������� Stage.
*</p>
*/
public class Stage extends ComponentBuilder{

	private double width;
	private double height;
	private String title;
	private ComponentBuilder root;
	
	/**
	*<p>
	*������� ������ Stage.
	*</p>
	*@param title ��������� ����.
	*@param w ������ ���� (� px).
	*@param h ������ ���� (� px).
	*/
	public Stage(String title, double w, double h){
		super();
		width=w;
		height=h;
		this.title=title;
		root=null;
	}
	
	public Structure buildComponent(){
		Structure str=new Structure();
		str.setZnachenie("StageGUI");
		Structure props=new Structure();
		props.dobavitZnachenie(title);
		props.dobavitZnachenie(""+width);
		props.dobavitZnachenie(""+height);
		str.dobavitPoduzel(props);
		if(root!=null){
			Structure rt=root.buildComponent();
			if(rt!=null)str.dobavitPoduzel(rt);
		}
		return(str);
	}
	
	/**
	*<p>
	*��������� �������� ������� ����.
	*</p>
	*@param root ����������� �������.
	*/
	public void addComponent(ComponentBuilder root){
		this.root=root;
	}
	
}
package mnadyukov.utilities;

import java.util.*;

/**
*<p>
*����� ������������ ����� ��������� ����, ��������� ������� ����� ���� ������������ � ���� ����������� ���������.
*</p>
*������ ���� ��������� ����� ��������� ��������, ������ �� ����-������ � ����� ������ �� ����-������� (���� ����� ����).
*����� �������, ��������� ��������� ����� ����� ����� ������������ ����������� ���������.
*����� ��� ���������� ����� ���������� ����� ��������� �����.
*����� �����-�������� ����������.
*<br>
*� ��������� ���� ������ ���� ���� �������� ������.
*���� ���� �� ����� ������ (������ �� ����-������ ����� null).
*������ �� ���� ���� ������������ ����� ������ �� ���������.
*<br>
*���� ������� ������ �� ����� ����� �������� (�������� ������ ����� ������ �� ����-�������).
*<br>
*����� ����� ������:<ul><li>
*	��������� ���������� �������� ����;</li><li>
*	��������� ���������� �������� ����;</li><li>
*	�������������� ������ ���������������� ������� � ���������;</li><li>
*	�������������� ��������� � ������ ���������������� �������;</li><li>
*	�������������� ��������� � ��������� �������������;</li><li>
*	���������� ������� � ������� ���� � ����� ������ �����-��������;</li><li>
*	������� ������� � ������� ���� � ��������� ����� � ������ �����-��������;</li><li>
*	�������� ���������� �������;</li><li>
*	��������� ���������� �������;</li><li>
*	���������� � ������� ���� ���������� �������� ��� ������� ������� ������.</li></ul>
*@author ������� ������
*/
public class Structure{

		private String znachenie;
		private ArrayList<Structure> potomki;
		private Structure predok;
	
	/**
	*<p>
	*������� ���� �������� ������.
	*</p>
	*�������� ���� ����� ������ ������.
	*��������� ���� �� ����� �����-��������.
	*/
	public Structure(){
		znachenie="";
		potomki=new ArrayList();
		predok=null;
	}
	
	/**
	*<p>
	*������������� ��������� �������� ����.
	*</p>
	*@param znach ��������� �������� ����.
	*/
	public void setZnachenie(String znach){
		znachenie=znach;
	}
	
	/**
	*<p>
	*���������� ��������� �������� ����.
	*</p>
	*@return ��������� �������� ����.
	*/
	public String getZnachenie(){
		return(znachenie);
	}
	
	/**
	*<p>
	*����������� ������ ������������� �������, ��������������� ��� �������� ����� �������� � ��������, � ������ Structure.
	*</p>
	*������ ������ (��� �������� ������ ������� '\u0001' � '\u0002' �������� �� ������� '{' � '}' ��������������):<br>
	*Znachenie{PODUZEL_1PODUZEL_2...} ���<ul><li>
	*	Znachenie - �������� ������� ����;</li><li>
	*	PODUZEL_N - ������, ����������� ������� N.</li></ul>
	*<br>
	*��������, ���� ���������, ��������� �� ���� �����.
	*�� ��������� ��������: '����1', '����2' � '����3'.
	*����1 - ���� �������� ������.
	*����2 � ����3 - ��� �������.
	*����2 � ����3 �� ����� ��������.
	*������, �������������� ����� ���������: '����1{����2{}����3{}}' (� �������� ������ ������ �������� '{' � '}' ������ ���� ������� '\u0001' � '\u0002' ��������������).
	*@param str ������, ����������� ���������.
	*@return ���������� ���������.<br>
	*null, ���� ��������� ������ (����������).
	*/
	public static Structure izStroki(String str){
		try{
			if(str==null)return(null);
			Structure rez=null;
			Structure tek_uzel=null;
			Structure uzel;
			int poz,poz1,poz2;
			String simvol;
			poz=0;
			while(true){
				if(poz==str.length())break;
				simvol=str.substring(poz,poz+1);
				if(simvol.equals("\u0001")){
					if(poz==0){
						uzel=new Structure();
						uzel.znachenie="";
						rez=uzel;
						tek_uzel=uzel;
					}else if(str.substring(poz-1,poz).equals("\u0001") || str.substring(poz-1,poz).equals("\u0002")){
						uzel=new Structure();
						uzel.znachenie="";
						tek_uzel.dobavitPoduzel(uzel);
						tek_uzel=uzel;
					}
					poz=poz+1;
				}else if(simvol.equals("\u0002")){
					poz=poz+1;
					tek_uzel=tek_uzel.predok;
				}else{
					poz1=str.indexOf("\u0001",poz);
					poz2=str.indexOf("\u0002",poz);
					if(poz1>poz2)throw new Exception("wrong Structure string");
					uzel=new Structure();
					uzel.znachenie=str.substring(poz,poz1);
					if(tek_uzel==null){
						rez=uzel;
					}else{
						tek_uzel.dobavitPoduzel(uzel);
					}
					tek_uzel=uzel;
					poz=poz1;
				}
			}
			return(rez);
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Structure.izStroki(String): "+e);
			return(null);
		}
	}
	
	/**
	*<p>
	*����������� ������ Structure � ������ ������������� �������, ��������������� ��� �������� ����� �������� � ��������.
	*</p>
	*�������� ������� ������ ��. � �������� ������ {@link #izStroki izStroki}.
	*@return ������ ���������������� �������, �������������� ������ ������ Structure.<br>
	*null, ���� ��������� ������ (����������).
	*/
	public String vStroku(){
		try{
			String rez=znachenie+"\u0001";
			for(int i=0;i<potomki.size();i++)rez+=potomki.get(i).vStroku();
			rez+="\u0002";
			return(rez);
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Structure.vStroku(): "+e);
			return(null);
		}
	}
	
	/**
	*<p>
	*���������� ��������� ������������� ������� Structure, ��������������� ��� ������ ���������.
	*</p>
	*@return ��������� ������������� ������� Structure.
	*/
	public String toString(){
		try{
			return(_toString(""));
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Structure.toString(): "+e);
			return(null);
		}
	}
	
	private String _toString(String prefiks){
		try{
			if(prefiks==null)prefiks="";
			String rez=prefiks+znachenie+"\n";
			for(int i=0;i<potomki.size();i++)rez+=potomki.get(i)._toString(prefiks+"=");
			return(rez);
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Structure.toString(): "+e);
			return(null);
		}
	}
	
	/**
	*<p>
	*��������� � �������� ���� ��������� �������.
	*</p>
	*���������� ������������ � ����� ������� ��������.
	*@param uzel ����������� �������.<br>
	*�� ������ ���� ����� null.
	*@return true, ���� ���������� ������� ����������� �������.<br>
	*false, ���� ��������� ������ (����������) ��� uzel ����� null.
	*/
	public boolean dobavitPoduzel(Structure uzel){
		try{
			if(uzel==null)return(false);
			potomki.add(uzel);
			uzel.predok=this;
			return(true);
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Structure.dobavitPoduzel(Structure): "+e);
			return(false);
		}
	}
	
	/**
	*<p>
	*��������� ��������� ������� � ��������� ������� ������� �������� �������� ����.
	*</p>
	*@param uzel ����������� �������.<br>
	*�� ������ ���� ����� null.
	*@param poziciya ������� ���������� ������� � ������ ��������.<br>
	*�������� poziciya ������ ���� ������ 0.
	*���� �������� poziciya ������ ���������� ��������, ����� ��������� ������� ����� ������� � ����� ������� ��������.
	*@return true, ���� ���������� ������� ����������� �������.<br>
	*false, ���� ��������� ������ (����������), poziciya ������ 0, uzel ����� null.
	*/
	public boolean vstavitPoduzel(Structure uzel,int poziciya){
		try{
			if(uzel==null || poziciya<0)return(false);
			if(poziciya>potomki.size()){
				potomki.add(uzel);
			}else{
				potomki.add(poziciya,uzel);
			}
			uzel.predok=this;
			return(true);
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Structure.vstavitPoduzel(Structure,int): "+e);
			return(false);
		}
	}
	
	/**
	*<p>
	*����������� ��������� �������� � ������� � ��������� ��� � �������� ����.
	*</p>
	*��������� ������� �� ����� ��������.
	*���������� ������������ � ����� ������� �������� �������� ����.
	*@param znach ����������� ��������.<br>
	*�� ������� ���� ����� null.
	*@return true, ���� ���������� �������� ����������� �������.<br>
	*false, ���� ��������� ������ (����������) ��� znach ����� null.
	*/
	public boolean dobavitZnachenie(String znach){
		try{
			if(znach==null)return(false);
			Structure s=new Structure();
			s.znachenie=znach;
			return(dobavitPoduzel(s));
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Structure.dobavitZnachenie: "+e);
			return(false);
		}
	}
	
	/**
	*<p>
	*������� ��������� ������� �� ������� �������� �������� ����.
	*</p>
	*@param poziciya ������� ���������� ������� � ������� �������� �������� ����.<br>
	*@return ��������� �������.<br>
	*false, ���� ��������� ������ (����������), ��� poziciya ������ 0 ��� ������ ���������� �������� ����� 1.
	*/
	public Structure udalitPoduzel(int poziciya){
		try{
			if(poziciya<0)return(null);
			if(poziciya<potomki.size())return(potomki.remove(poziciya));
			return(null);
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Structure.udalitPoduzel: "+e);
			return(null);
		}
	}
	
	/**
	*<p>
	*���������� ��������� ������� �������� ����.
	*</p>
	*@param poziciya ������� ������������� ������� � ������� �������� �������� ����.<br>
	*@return ��������� �������.<br>
	*false, ���� ��������� ������ (����������), ��� poziciya ������ 0 ��� ������ ���������� �������� ����� 1.
	*/
	public Structure poluchitPoduzel(int poziciya){
		try{
			if(poziciya<0)return(null);
			if(poziciya<potomki.size())return(potomki.get(poziciya));
			return(null);
		}catch(Exception e){
			System.err.println("mnadyukov.utilities.Structure.poluchitPoduzel(int): "+e);
			return(null);
		}
	}
	
}
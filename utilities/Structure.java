package mnadyukov.utilities;

import java.util.*;

/**
*<p>
*Класс представляет собой строковой узел, множество которых может быть организовано в виде древовидной структуры.
*</p>
*Каждый узел структуры имеет строковое значение, ссылку на узел-предок и набор ссылок на узлы-потомки (если такие есть).
*Таким образом, множество связанных между собой узлов представляет древовидную структуру.
*Далее под структурой будет пониматься набор связанных узлов.
*Набор узлов-потомков упорядочен.
*<br>
*У структуры есть только один узел верхнего уровня.
*Этот узел не имеет предка (ссылка на узел-предок равна null).
*Ссылка на этот узел представляет собой ссылку на структуру.
*<br>
*Узлы нижнего уровня не имеют узлов потомков (содержат пустой набор ссылок на узлы-потомки).
*<br>
*Класс имеет методы:<ul><li>
*	установки строкового значения узла;</li><li>
*	получения строкового значения узла;</li><li>
*	преобразования строки соответствующего формата в структуру;</li><li>
*	преобразования структуры в строку соответствующего формата;</li><li>
*	преобразования структуры в текстовое представление;</li><li>
*	добавления подузла к данному узлу в конец набора узлов-потомков;</li><li>
*	вставки подузла к данному узлу в указанное место в наборе узлов-потомков;</li><li>
*	удаление указанного подузла;</li><li>
*	получения указанного подузла;</li><li>
*	добавления к данному узлу строкового значения как подузла нижнего уровня.</li></ul>
*@author Надюков Михаил
*/
public class Structure{

		private String znachenie;
		private ArrayList<Structure> potomki;
		private Structure predok;
	
	/**
	*<p>
	*Создает узел верхнего уровня.
	*</p>
	*Значение узла равно пустой строке.
	*Созданный узел не имеет узлов-потомков.
	*/
	public Structure(){
		znachenie="";
		potomki=new ArrayList();
		predok=null;
	}
	
	/**
	*<p>
	*Устанавливает строковое значение узла.
	*</p>
	*@param znach Строковое значение узла.
	*/
	public void setZnachenie(String znach){
		znachenie=znach;
	}
	
	/**
	*<p>
	*Возвращает строковое значение узла.
	*</p>
	*@return Строковое значение узла.
	*/
	public String getZnachenie(){
		return(znachenie);
	}
	
	/**
	*<p>
	*Преобразует строку определенного формата, предназнеченную для передачи между клиентом и сервером, в объект Structure.
	*</p>
	*Формат строки (для удобства чтения символы '\u0001' и '\u0002' заменены на символы '{' и '}' соответственно):<br>
	*Znachenie{PODUZEL_1PODUZEL_2...} где<ul><li>
	*	Znachenie - значение данного узла;</li><li>
	*	PODUZEL_N - строка, описывающая подузел N.</li></ul>
	*<br>
	*Например, есть структура, состоящая из трех узлов.
	*Их строковые значения: 'Узел1', 'Узел2' и 'Узел3'.
	*Узел1 - узел верхнего уровня.
	*Узел2 и Узел3 - его подузлы.
	*Узел2 и Узел3 не имеют подузлов.
	*Строка, представляющая такую структуру: 'Узел1{Узел2{}Узел3{}}' (в реальной строке вместо символов '{' и '}' должны быть символы '\u0001' и '\u0002' соответственно).
	*@param str Строка, описывающая структуру.
	*@return Полученная структура.<br>
	*null, если произошла ошибка (исключение).
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
	*Преобразует объект Structure в строку определенного формата, предназнеченную для передачи между клиентом и сервером.
	*</p>
	*Описание формата строки см. в описании метода {@link #izStroki izStroki}.
	*@return Строка соответствующего формата, представляющая данный объект Structure.<br>
	*null, если произошла ошибка (исключение).
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
	*Возвращает строковое представление объекта Structure, предназначенное для чтения человеком.
	*</p>
	*@return Строковое представление объекта Structure.
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
	*Добавляет к текущему узлу указанный подузел.
	*</p>
	*Добавление производится в конец массива подузлов.
	*@param uzel Добавляемый подузел.<br>
	*Не должен быть равен null.
	*@return true, если добавление подузла произведено успешно.<br>
	*false, если произошла ошибка (исключение) или uzel равен null.
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
	*Вставляет указанный подузел в указанную позицию массива подузлов текущего узла.
	*</p>
	*@param uzel Добавляемый подузел.<br>
	*Не должен быть равен null.
	*@param poziciya Позиция добовления подузла в массив подузлов.<br>
	*Значение poziciya должно быть больше 0.
	*Если значение poziciya больше количества подузлов, тогда указанный подузел будет помещен в конец массива подузлов.
	*@return true, если добавление подузла произведено успешно.<br>
	*false, если произошла ошибка (исключение), poziciya меньше 0, uzel равен null.
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
	*Преобразует указанное значение в подузел и добавляет его к текущему узлу.
	*</p>
	*Созданный подузел не имеет подузлов.
	*Добавление производится в конец массива подузлов текущего узла.
	*@param znach Добавляемое значение.<br>
	*Не должено быть равно null.
	*@return true, если добавление значения произведено успешно.<br>
	*false, если произошла ошибка (исключение) или znach равно null.
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
	*Удаляет указанный подузел из массива подузлов текущего узла.
	*</p>
	*@param poziciya Позиция удаляемого подузла в массиве подузлов текущего узла.<br>
	*@return Удаленный подузел.<br>
	*false, если произошла ошибка (исключение), или poziciya меньше 0 или больше количества подузлов минус 1.
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
	*Возвращает указанный подузел текущего узла.
	*</p>
	*@param poziciya Позиция возвращаемого подузла в массиве подузлов текущего узла.<br>
	*@return Указанный подузел.<br>
	*false, если произошла ошибка (исключение), или poziciya меньше 0 или больше количества подузлов минус 1.
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
package mnadyukov.crypto;

import java.util.*;

/**
*<p>
*Общий интерфейс для всех классов, осуществляющих шифрование / расшифровывание данных.
*</p>
*@author Надюков Михаил
*/
public interface Cryptographer{
	
	/**
	*<p>
	*Метод, осуществляющий шифрование данных.
	*</p>
	*@param src Исходный массив байт.
	*@param props Объект, содержащий параметры шифрования.
	*@return Зашифрованный массив байт.
	*/
	public byte[] encrypting(byte[] src, Hashtable<String,String> props);
	
	/**
	*<p>
	*Метод, осуществляющий расшифровывание данных.
	*</p>
	*@param src Шифрованный массив байт.
	*@param props Объект, содержащий параметры расшифровывания.
	*@return Расшифрованный массив байт.
	*/
	public byte[] decrypting(byte[] src, Hashtable<String,String> props);

}
package mnadyukov.crypto;

import java.util.*;

/**
*<p>
*Класс, осуществляющий тривиальное шифрование / расшифровывание данных.
*</p>
*Фактически, класс не изменяет переданный ему на шифрование / расшифровывание массив байт.
*@author Надюков Михаил
*/
public class DefaultCryptographer implements Cryptographer{

	public byte[] encrypting(byte[] src, Hashtable<String,String> props){
		return(src);
	}

	public byte[] decrypting(byte[] src, Hashtable<String,String> props){
		return(src);
	}
	
}
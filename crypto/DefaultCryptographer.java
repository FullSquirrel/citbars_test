package mnadyukov.crypto;

import java.util.*;

/**
*<p>
*�����, �������������� ����������� ���������� / ��������������� ������.
*</p>
*����������, ����� �� �������� ���������� ��� �� ���������� / ��������������� ������ ����.
*@author ������� ������
*/
public class DefaultCryptographer implements Cryptographer{

	public byte[] encrypting(byte[] src, Hashtable<String,String> props){
		return(src);
	}

	public byte[] decrypting(byte[] src, Hashtable<String,String> props){
		return(src);
	}
	
}
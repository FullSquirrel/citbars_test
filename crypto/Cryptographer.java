package mnadyukov.crypto;

import java.util.*;

/**
*<p>
*����� ��������� ��� ���� �������, �������������� ���������� / ��������������� ������.
*</p>
*@author ������� ������
*/
public interface Cryptographer{
	
	/**
	*<p>
	*�����, �������������� ���������� ������.
	*</p>
	*@param src �������� ������ ����.
	*@param props ������, ���������� ��������� ����������.
	*@return ������������� ������ ����.
	*/
	public byte[] encrypting(byte[] src, Hashtable<String,String> props);
	
	/**
	*<p>
	*�����, �������������� ��������������� ������.
	*</p>
	*@param src ����������� ������ ����.
	*@param props ������, ���������� ��������� ���������������.
	*@return �������������� ������ ����.
	*/
	public byte[] decrypting(byte[] src, Hashtable<String,String> props);

}
package mnadyukov.client;

import java.util.*;
import java.io.*;
import java.net.*;
import mnadyukov.crypto.*;
import mnadyukov.utilities.*;

/**
*<p>
*�����, �������������� ����� ����������� ������� � ��������.
*</p>
*�������� � ����� ��������� �������������� �� ��������� http.
*� �������� ���������� ������ ��������� �� ������ localhost:3100.
*<br>
*��� �������� ��������� ������������ ��������� ��������:<ul><li>
*	��������� ��������� ������������� � ������ ����;</li><li>
*	������ ���� ���������;</li><li>
*	���������� ����� ���������� ������ ���� ������������� � ������ Base64;</li><li>
*	Base64 ������ ������������ �������;</li><li>
*	������ ��������� � ����� �������� ������ �� �������.</li></ul>
*<br>
*��� ��������� ������ �� ������� ������������ ��������� ��������:<ul><li>
*	���������� Base64 ������ ������������� � ������������� ������ ����;</li><li>
*	������������� ������ ���� ����������������;</li><li>
*	�������������� ������ ���� ������������� � ��������� ���������.</li></ul>
*<br>
*� �������� ���������� ���������� ��� ������� �� ������������ (������������ ����� {@link mnadyukov.crypto.DefaultCryptographer DefaultCryptographer} ��������� ����������� ��������������).
*@author ������� ������
*/
public class ClientCommunicator{
	
	/**
	*���������� � ��������.
	*/
	private Socket S;
	
	/**
	*������, ������������ ���������� / �������������.
	*/
	private Cryptographer CR;
	
	/**
	*������, ���������� ������ ��� ���������� / ������������� (� �������� ���������� ���������� �� ������������).
	*/
	private Hashtable<String,String> cr_props;
	
	/**
	*������-���������� ������, ����������� �� �������.
	*/
	private ClientHandler CH;
	
	/**
	*<p>
	*������� ������ ClientCommunicator.
	*</p>
	*@param p ���������� ����������, � ������� ����������� ������ ������.
	*/
	public ClientCommunicator(Prilojenie p){
		CR=new DefaultCryptographer();
		cr_props=null;
		S=null;
		CH=new ClientHandler(p);
	}
	
	/**
	*<p>
	*����� ������������ �������� ��������� �������.
	*</p>
	*@param msg ���������, ������������ �������.
	*/
	public void send(String msg){
		try{
			if(msg==null)return;
			byte[] b=msg.getBytes("Windows-1251");
			b=CR.encrypting(b,cr_props);
			String pckg=Base64.getEncoder().encodeToString(b);
			pckg=	"HTTP/1.1 200 OK\n"+
					"Content-type:text/plain; charset="+"Windows-1251"+"\n"+
					"Access-Control-Allow-Origin:*\n"+
					"Cache-Control:no-cache\n"+
					"Content-Length:"+pckg.length()+"\n\n"+
					pckg;
			S=new Socket("localhost",3100);
			OutputStream os=S.getOutputStream();
			os.write(pckg.getBytes("Windows-1251"));
			receive();
		}catch(Exception e){
			System.err.println("mnadyukov.client.ClientCommunicator.send: "+e);
			Utilities.closeClientConnection(S);
		}
	}
	
	/**
	*<p>
	*����� ������������ ����� ��������� �� �������.
	*</p>
	*���������� ��������� ���������� �������-����������� ��� ���������� ��������������� �������.
	*@param msg ���������, ������������ �������.
	*/
	private void receive(){
		try{
			if(S==null)return;
			if(S.isClosed())return;
			BufferedReader is=new BufferedReader(new InputStreamReader(S.getInputStream()));
			String line;
			while(true){
				line=is.readLine();
				if(line==null)return;
				if(line.equals(""))break;
				System.out.println(line);
			}
			line=is.readLine();
			if(line==null)return;
			byte[] b=Base64.getDecoder().decode(line);
			b=CR.decrypting(b,cr_props);
			line=new String(b,"Windows-1251");
			CH.handle(line);
		}catch(Exception e){
			System.err.println("mnadyukov.client.ClientCommunicator.receive: "+e);
		}finally{
			Utilities.closeClientConnection(S);
		}
	}

}
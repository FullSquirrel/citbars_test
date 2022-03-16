package mnadyukov.client;

import java.util.*;
import java.io.*;
import java.net.*;
import mnadyukov.crypto.*;
import mnadyukov.utilities.*;

/**
*<p>
*Класс, осуществляющий обмен сообщениями клиента с сервером.
*</p>
*Передача и прием сообщений осуществляется по протоколу http.
*В тестовом приложении сервер находится по адресу localhost:3100.
*<br>
*При отправке сообщения производятся следующие действия:<ul><li>
*	текстовое сообщение преобразуется в массив байт;</li><li>
*	массив байт шифруется;</li><li>
*	полученный после шифрования массив байт преобразуется в строку Base64;</li><li>
*	Base64 строка отправляется серверу;</li><li>
*	объект переходит в режим ожидания ответа от сервера.</li></ul>
*<br>
*При получении ответа от сервера производятся следующие действия:<ul><li>
*	полученная Base64 строка преобразуется в зашифрованный массив байт;</li><li>
*	зашифрованный массив байт расшифровывается;</li><li>
*	расшифрованный массив байт преобразуется в текстовое сообщение.</li></ul>
*<br>
*В тестовом приложении шифрование как таковое не производится (используемый класс {@link mnadyukov.crypto.DefaultCryptographer DefaultCryptographer} выполняет тривиальное преобразование).
*@author Надюков Михаил
*/
public class ClientCommunicator{
	
	/**
	*Соединение с сервером.
	*/
	private Socket S;
	
	/**
	*Объект, производящий шифрование / расшифрование.
	*/
	private Cryptographer CR;
	
	/**
	*Объект, содержащий данные для шифрования / расшифрования (в тестовом приложении фактически не используется).
	*/
	private Hashtable<String,String> cr_props;
	
	/**
	*Объект-обработчик команд, поступивших от сервера.
	*/
	private ClientHandler CH;
	
	/**
	*<p>
	*Создает объект ClientCommunicator.
	*</p>
	*@param p Клиентское приложение, с которым связывается данный объект.
	*/
	public ClientCommunicator(Prilojenie p){
		CR=new DefaultCryptographer();
		cr_props=null;
		S=null;
		CH=new ClientHandler(p);
	}
	
	/**
	*<p>
	*Метод осуществляет передачу сообщений серверу.
	*</p>
	*@param msg Сообщение, передаваемое серверу.
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
	*Метод осуществляет прием сообщений от сервера.
	*</p>
	*Полученное сообщение передается объекту-обработчику для выполнения соответствующей команды.
	*@param msg Сообщение, передаваемое серверу.
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
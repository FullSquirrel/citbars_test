package mnadyukov.server;

import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;
import mnadyukov.crypto.*;
import mnadyukov.utilities.*;

public class ServerCommunicator{

	private Hashtable<String,String> SP;
	private Cryptographer CR;
	private Connection conn;
	private Socket S;
	private Hashtable<String,String> cr_props;

	public ServerCommunicator(Connection conn, Hashtable<String,String> cr_props){
		this.conn=conn;
		this.cr_props=cr_props;
		CR=new DefaultCryptographer();
		SP=ErrorJournalManager.getSystemParameters();
	}
	
	public String receive(Socket s){
		try{
			S=s;
			InputStream is=S.getInputStream();
			int bNum=is.available();
			if(bNum==0)return(null);
			byte[] b=new byte[bNum];
			is.read(b);
			String msg=new String(b,SP.get("SERVER_ENCODING"));
			Scanner scn=new Scanner(msg);
			while(scn.hasNextLine()){
				if(scn.nextLine().length()==0)break;
			}
			msg=scn.hasNextLine()?scn.nextLine():null;
			if(msg==null)return(null);
			byte[] data=Base64.getDecoder().decode(msg);
			data=CR.decrypting(data,cr_props);
			if(data==null){
				ErrorJournalManager.write("mnadyukov.server.ServerCommunicator.receive: error while Cryptographer.decripting");
				return(null);
			}
			msg=new String(data,SP.get("SERVER_ENCODING"));
			return(msg);
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.ServerCommunicator.receive: "+e);
			return(null);
		}
	}
	
	public boolean send(String msg){
		try{
			if(msg==null)return(true);
			byte[] b=msg.getBytes(SP.get("SERVER_ENCODING"));
			b=CR.encrypting(b,cr_props);
			String pckg=Base64.getEncoder().encodeToString(b);
			pckg=	"HTTP/1.1 200 OK\n"+
					"Content-type:text/plain; charset="+SP.get("SERVER_ENCODING")+"\n"+
					"Access-Control-Allow-Origin:*\n"+
					"Cache-Control:no-cache\n"+
					"Content-Length:"+pckg.length()+"\n\n"+
					pckg;
			OutputStream os=S.getOutputStream();
			os.write(pckg.getBytes(SP.get("SERVER_ENCODING")));
			return(true);
		}catch(Exception e){
			ErrorJournalManager.write("mnadyukov.server.ServerCommunicator.send: "+e);
			return(false);
		}finally{
			Utilities.closeClientConnection(S);
		}
	}

}
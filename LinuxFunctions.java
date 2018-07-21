package GenericFunctionsLibrary;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
public class LinuxFunctions {

 

	  //public static void main(String[] arg){
	       static String Password;
	    
	       public void fnExecuteCommandOnLinuxServer(String hostname,String exactPassword,String commandstring)
	       {
	              setpassword(exactPassword);
	       try{  
	      JSch jsch=new JSch();    
	  
	      String host=null;  
	       
	        //host="username@ipaddress"; // enter username and ipaddress for machine you need to connect
	         //host="admin@172.16.3.212";
	      host=hostname;
	      String user=host.substring(0, host.indexOf('@'));  
	      host=host.substring(host.indexOf('@')+1);  
	  
	      Session session=jsch.getSession(user, host, 22);  
	      
	      // username and password will be given via UserInfo interface.  
	      UserInfo ui=new MyUserInfo();  
	      session.setUserInfo(ui);  
	      session.connect();  
	  
	      //String command=  "refreshRdxNSE"; // enter any command you need to execute  
	      String command=  commandstring; // enter any command you need to execute
	     Channel channel=session.openChannel("exec");  
	      ((ChannelExec)channel).setCommand(command);  
	  
	       
	      channel.setInputStream(null);  
	  
	      ((ChannelExec)channel).setErrStream(System.err);  
	  
	      InputStream in=channel.getInputStream();  
	  
	      channel.connect();  
	  
	      byte[] tmp=new byte[1024];  
	      while(true){  
	        while(in.available()>0){  
	          int i=in.read(tmp, 0, 1024);  
	          if(i<0)break;  
	          System.out.print(new String(tmp, 0, i));  
	        }  
	        if(channel.isClosed()){  
	          System.out.println("exit-status: "+channel.getExitStatus());  
	          break;  
	        }  
	        try{Thread.sleep(1000);}catch(Exception ee){}  
	      }  
	      channel.disconnect();  
	      session.disconnect();  
	    }  
	    catch(Exception e){  
	      System.out.println(e);  
	    }  
	  }  
	  
	  public static class MyUserInfo implements UserInfo{  
	    public String getPassword(){ return passwd; }  
	    public boolean promptYesNo(String str){  
	        str = "Yes";  
	        return true;}  
	    
	    String passwd;  
	  
	    public String getPassphrase(){ return null; }  
	    public boolean promptPassphrase(String message){ return true; }  
	    public boolean promptPassword(String message){  
	        passwd=Password; // enter the password for the machine you want to connect.  
	        return true;  
	    }  
	    public void showMessage(String message){  
	     
	    }  
	   
	  }
	  
	  public void setpassword(String pass)
	  {
	         Password=pass;
	  }
	  
}

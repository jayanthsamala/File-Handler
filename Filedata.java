package packclass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Filedata {
	
	public static String gettbName(String p){
		String tbname=null;
		try{
		FileReader filee=new FileReader(p);
		BufferedReader b=new BufferedReader(filee);
				String l=b.readLine();
				String[] splitted=l.split(";");
				tbname=splitted[0];//store table name
				b.close();
		}
		//try close
		catch(Exception e){
			System.out.println("invalid file data");
			System.exit(1);
		}//catch close
		return tbname;
	}
	
	
	public static List getcolName(String p){
		List m=new ArrayList();
		try{
			FileReader filee=new FileReader(p);
			BufferedReader b=new BufferedReader(filee);
					String l=b.readLine();
					String[] splitted=l.split(";");
					String s=splitted[3];
					String[] ar=s.split(",");
					for(int i=0;i<ar.length;i++){
						String[] q=ar[i].split("-");
						for(int j=0;j<q.length;j++){
						m.add(q[j]);//adding column names and data types into list
						}
					}
					//System.out.println(m);
				//	System.out.println(col);
				//	System.out.println(dtype);
					b.close();
			}
			//try close
			catch(Exception e){
				System.out.println("invalid file data");
				System.exit(1);
			}//catch close
		return m;
		
	}//method close
	public static void create( String tableName, List m, String path){
		String filepath=path;
		List col=new ArrayList();
		List dtype=new ArrayList();
		for(int k=0;k<(m.size()-1);k=k+2){
			String c=(String) m.get(k);
			col.add(c);
		}
		for(int q=1;q<m.size();q=q+2){
			String d=(String) m.get(q);
			if(d.equals("0")){
				dtype.add("int");
			}else if(d.equals("1")){
				dtype.add("varchar(20)");
			}
		}
	
		try{
		 String sql="show tables like "+"'"+tableName+"'";
		 System.out.println(sql);
	        Connection con;
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","Viratjay68");
			PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs=ps.executeQuery(sql);
	        int count=0;
	       while(rs.next())
	        {	
	        	if(tableName.equals(rs.getString(1))){
	        		System.out.println("updating table");
	        	insert(filepath, tableName, dtype);
	        		count=1;
	        		
	        	}
	        }
	        if(count==0){
	        	
	        	String s="";
        		for(int i=0;i<col.size();i++){
        			s=s+","+col.get(i)+ " "+dtype.get(i);
        		}
        		String p=s.replaceFirst(",", "");
        		String query="create table "+tableName+"("+p+");";
        		ps.executeUpdate(query);
        		System.out.println("created table");
        		insert(filepath, tableName, dtype);
	        }
		
	        
	        
		}catch(Exception e){
			System.out.println("in this exception");
			System.out.println(e);
		}
		
	}
	public static void insert(String p, String tablename, List dtype){
		
		try{
			
			String sql="";
			String newpath="C:/Users/JAY/Desktop/updated/";
			String line1="";
			String line2="";
			int size=dtype.size();
			String[] www=new String[size];
        Connection con;
		Class.forName("com.mysql.jdbc.Driver");  
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","Viratjay68");
		//PreparedStatement ps = con.prepareStatement(sql);
		Statement stmt = con.createStatement();
		FileReader filee=new FileReader(p);
		BufferedReader b2=new BufferedReader(filee);
		b2.readLine();
		while((line1 = b2.readLine())!= null)
		{	line2=line1.replace(";", " ");
		String[] qqq=line2.split(" ");
		
		for(int i=0;i<dtype.size();i++){
		if(dtype.get(i).equals("int")){
			www[i]=qqq[i];
		}else if(dtype.get(i).equals("varchar(20)")){
			www[i]="\'"+qqq[i]+"\'";
		}
		}//for close
		String line3="";
		String line4="";
		for(int i=0;i<size;i++){
		line3=line3+","+www[i];
		line4=line3.replaceFirst(",", "");
		
		}//for close
		 stmt.addBatch("insert into "+tablename+" values("+line4+")");
		//PreparedStatement ps = con.prepareStatement(sql);
		//ps.executeUpdate();
		
		}
		stmt.executeBatch();
		System.out.println("rows inserted succesfully");
		b2.close();
		File fil=new File(p);
		String fname=fil.getName();
		fil.renameTo(new File(newpath+fname));
		
		}catch(Exception e){
			System.out.println(e);
		}
		
		
		
		
		
		
		
		
		
		
		
	}//method close

}//class close

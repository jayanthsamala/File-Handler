package packclass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Iammain {

	public static void main(String[] args) {
		List columnName = new ArrayList();
			Filedata f=new Filedata();
			File fil=new File("C:\\Users\\JAY\\Desktop\\filedata");
			String[] filelist=fil.list();
			if(filelist.length==0){
				System.out.println("empty folder");
			}else{
			for(int i=0;i<filelist.length;i++)
			{
				String path="C:\\Users\\JAY\\Desktop\\filedata\\"+filelist[i];
			String tableName=f.gettbName(path);
			columnName=f.getcolName(path);
			f.create(tableName, columnName, path);
			}
	}

}
}

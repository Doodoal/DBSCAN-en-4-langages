import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class CsvHandler{

private PrintWriter writer;
private BufferedReader reader;
private ArrayList<String[]> importedData;

//Constructor for CsvHandler w/ reader & writer
public CsvHandler (String toRead,String newFile){
  try {
    importedData = new ArrayList<String[]>();

    //Reading the csv toRead
    reader = new BufferedReader(new FileReader(toRead));
    String line = "";
    while ((line = reader.readLine())!= null){
      String[] array = line.split(",");
      importedData.add(array);
    }
    reader.close();

    //Initializing a writer for newFile
    writer = new PrintWriter(new File(newFile));

  }catch (IOException e){
  throw new RuntimeException() ;
}
}

//Constructor for CsvHandler w/ writer only
public CsvHandler (String newFile){
  try {
    //Initializing a writer for newFile
    writer = new PrintWriter(new File(newFile));

  }catch (IOException e){
  System.out.println(e.getMessage());
}
}



public ArrayList<String[]> getData(){return this.importedData;}

public void exportArrayList(ArrayList<String[]> data){
  for (int i = 0; i < data.size(); i++){
    this.exportArray(data.get(i));
  }
}


public void exportArray(String[] array){
//write an array in csv format into the file

  String line = "";
  for (int i = 0 ; i< array.length -1; i++ ){
    line = line+ array[i]+ ",";
  }
  line = line + array [array.length -1]+ "\n";

  writer.write(line);
}

public void  closeWriter(){ writer.close();}

}

import java.util.ArrayList;
public class TaxiClusters{

  public static void main (String[] args){

    String fileName = args[0] ;
    int minPts = Integer.parseInt(args[1]);
    double eps = Double.parseDouble(args[2]);
    CsvHandler csvHandler;

    try {
      csvHandler = new CsvHandler(fileName, "Output.csv");
    } catch (RuntimeException e){
      csvHandler = new CsvHandler("yellow.csv", "Output.csv");
      System.out.println("WARNING: Input file not found.\nTaxiClusters will use yellow.csv (default file) \n\n ");

    }


    DataManager data = new DataManager(csvHandler.getData());





    //Complete DBscan (see Cluster constructor for more details )
    ArrayList<TripRecord> tripRecords = data.getRecords();



    for (TripRecord record : tripRecords){


      if (record.getLabel()== "undefined"){
        ArrayList<TripRecord> neighbors = record.getNeighbors(data.getRecords(),eps);

        if (neighbors.size()>= eps){
          Cluster cluster = new Cluster(record, tripRecords, eps, minPts );
          data.addCluster(cluster);
        }
      } else{
          record.setLabel("outlier");
        }


    }//

    System.out.println("Trip record count: "+ data.getRecords().size());
    System.out.println("Cluster count: "+ data.getClusters().size());
    System.out.println("\nOutput file : output.csv");









    csvHandler.exportArrayList(data.getClusterList());
    csvHandler.closeWriter();



  }
}

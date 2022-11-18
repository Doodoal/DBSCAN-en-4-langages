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





    //Complete DBscan

    //Labels signification:
    // -1 : Undefined
    // -2: Noise
    ArrayList<TripRecord> tripRecords = data.getRecords();
    int label = -1;



    for (TripRecord record : tripRecords){


      if (record.getLabel() != -1){continue;}

        ArrayList<TripRecord> neighbors = record.getNeighbors(data.getRecords(),eps);

      if (neighbors.size()< eps){
        record.setLabel(-2);
        continue;
      }

      label ++;
      record.setLabel(label);
      Cluster cluster = new Cluster(label, tripRecords);
      data.addCluster(cluster);

      for (TripRecord x : neighbors){
        if (x.getLabel() == -2) {x.setLabel(label);}
        if (x.getLabel() != -1){continue;}
        x.setLabel(label);
        ArrayList<TripRecord> xNeighbors = x.getNeighbors(data.getRecords(),eps);
        if (xNeighbors.size()>= eps){
            neighbors.addAll(xNeighbors);
          }
        }




    }//

    for (Cluster cluster: data.getClusters()){
      cluster.fill();
    }

    System.out.println("Trip record count: "+ data.getRecords().size());
    System.out.println("Cluster count: "+ data.getClusters().size());
    System.out.println("\nOutput file : output.csv");









    csvHandler.exportArrayList(data.getClusterList());
    csvHandler.closeWriter();



  }
}

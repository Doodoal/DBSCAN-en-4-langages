import java.util.ArrayList;
public class DataManager{

  private ArrayList<String[]> rawData;
  private ArrayList<GPScoord> startPoints;
  private ArrayList<TripRecord> records;
  private ArrayList <Cluster> clusters ;

  public DataManager(ArrayList<String[]> rawData){
    this.rawData = rawData;
    startPoints = new ArrayList<GPScoord>();
    records = new ArrayList<TripRecord>();
    clusters = new ArrayList<Cluster>();

      for (String[] array : rawData){
        try{
          this.convertData(array);
        }catch (Exception e){}
      }




  }

  public void convertData( String[] array ){
      double start_long = Double.parseDouble(array[8]);
      double start_lat = Double.parseDouble(array[9]) ;
      double end_long = Double.parseDouble(array[12]) ;
      double end_lat = Double.parseDouble(array[13])  ;
      String pickup_DateTime = array[4];
      float trip_Distance = Float.parseFloat(array[7]) ;

      GPScoord pickup_Location = new GPScoord(start_long, start_lat);
      GPScoord dropoff_Location = new GPScoord(end_long, end_lat);
      TripRecord record = new TripRecord(pickup_DateTime, pickup_Location , dropoff_Location, trip_Distance);

      startPoints.add(pickup_Location);
      records.add(record);

  }

  public ArrayList<String[]> getRawData(){return rawData;}
  public ArrayList<GPScoord> getStartPoints(){return startPoints;}
  public ArrayList<TripRecord> getRecords(){return records;}

  public void addCluster(Cluster cluster){clusters.add(cluster);}

  public ArrayList <Cluster> getClusters(){return clusters;}


  public ArrayList<String[]> getClusterList(){

   ArrayList<String[]> list = new ArrayList<String[]>();
   String[] titleColumn = {"Cluster","Size","Average_Long","Average_Lat"};
   list.add(titleColumn);

     for (int i =0; i> clusters.size(); i++){
         try {
           String[] array = new String[4];
           Cluster cluster = clusters.get(i);
           GPScoord average = cluster.getAverage();

           array[0]= Integer.toString(i+1);
           array[1] = Integer.toString(cluster.getSize());
           array[2] = Double.toString(average.getLongitude());
           array[3] = Double.toString(average.getLatitude());
           list.add(array);
         } catch (ArithmeticException e){ System.out.println(e);}
        }

        return list;

     }


}

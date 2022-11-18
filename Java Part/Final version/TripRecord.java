import java.util.ArrayList;
public class TripRecord{
  private String pickup_DateTime;
  private GPScoord pickup_Location;
  private GPScoord dropoff_Location;
  private float trip_Distance;
  private String label;


  public TripRecord(String pickup_DateTime ,GPScoord pickup_Location,
                    GPScoord dropoff_Location,float trip_Distance){
                    this.pickup_DateTime = pickup_DateTime;
                    this.pickup_Location = pickup_Location;
                    this.dropoff_Location = dropoff_Location;
                    this.trip_Distance = trip_Distance;
                    this.label = "undefined";
                  }

  public GPScoord getStart(){ return pickup_Location; }


  public ArrayList<TripRecord> getNeighbors(ArrayList<TripRecord> records,double range){

    ArrayList<TripRecord> neighbors = new ArrayList<TripRecord>();

    for (TripRecord record : records){

      if (this.pickup_Location.distance(record.getStart()) <= range ){
        neighbors.add(record);
      }
    }

    return neighbors;
  }

  public void setLabel(String label){this.label = label;}
  public String getLabel(){return this.label;}





}

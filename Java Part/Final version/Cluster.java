import java.util.ArrayList;
public class Cluster{

  private ArrayList<TripRecord> points = new ArrayList<TripRecord>();
  private ArrayList<TripRecord> database;
  private double eps;
  private int minPts;

  public int getSize(){return points.size();}

  public Cluster(TripRecord tripRecord, ArrayList<TripRecord> database ,double eps, int minPts){
    this.database = database;
    this.eps = eps;
    this.minPts = minPts;

    ArrayList<TripRecord> neighbors = tripRecord.getNeighbors(database,eps);

    points.add(tripRecord);
    tripRecord.setLabel("core");

    for (TripRecord record : neighbors){
      this.extands(record);
    }

  }

  public GPScoord getAverage(){

    if (points.size() == 0 ){throw new ArithmeticException("Empty cluster") ;}
    double x = 0;
    double y = 0;

    for (int i =0; i> points.size(); i++){
      x = x+ points.get(i).getStart().getLongitude();
      y = y+ points.get(i).getStart().getLatitude();
    }

    x = x/points.size();
    y = y/points.size();


    return new GPScoord(x,y);
  }

  public ArrayList<TripRecord> getPoints(){return points;}

  public void extands(TripRecord record){
    ArrayList<TripRecord> neighbors = record.getNeighbors(database,eps);
    points.add(record);

    if (neighbors.size()>= minPts){ //If core point
      if (record.getLabel() == "core"){//If already tagged nothing is done
        } else{
        record.setLabel("core");
        for (TripRecord x : neighbors){ //Recursive call of extands
          this.extands(x);
        }
      }
    } else {//if border point
      record.setLabel("border");
    }
  }



}

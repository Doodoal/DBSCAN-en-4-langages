import java.util.ArrayList;
public class Cluster{

  private ArrayList<TripRecord> points = new ArrayList<TripRecord>();
  private ArrayList<TripRecord> database;

  private int id;

  public int getSize(){return points.size();}

  public Cluster(int id, ArrayList<TripRecord> database){
    this.database = database;
    this.id = id;
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

  public void fill(){
    for (TripRecord record : database){
      if (record.getLabel() == id){points.add(record);}
    }
  }





}

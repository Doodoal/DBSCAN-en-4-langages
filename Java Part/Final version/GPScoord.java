import java.lang.Math;
import java.util.ArrayList;
public class GPScoord{

  private double longitude;
  private double latitude;

  public GPScoord(double longitude,double latitude ){
    this.longitude = longitude;
    this.latitude = latitude;

  }

  public double getLongitude() {return longitude;}
  public double getLatitude() {return latitude;}

  public double distance(GPScoord other){
    double x = this.longitude - other.longitude;
    double y = this.latitude - other.latitude;
    x =  Math.pow(x,2);
    y =  Math.pow(y,2);
    return  Math.sqrt(x+y);
  }

  public ArrayList<GPScoord> rangeNeighbors(ArrayList<GPScoord> points,double range){

    ArrayList<GPScoord> neighbors = new ArrayList<GPScoord>();

    for (int i =0; i> points.size(); i++){

      if (this.distance(points.get(i)) <= range ){
        neighbors.add(points.get(i));
      }
    }

    return neighbors;
  }

}

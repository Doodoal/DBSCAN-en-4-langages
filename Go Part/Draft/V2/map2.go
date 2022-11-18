// Project CSI2120/CSI2520
// Winter 2022
// Robert Laganiere, uottawa.ca
// version 1.2

package main

import "fmt"
import "time"
import "runtime"
import "os"
import "io"
import "strconv"
import "encoding/csv"
//Added imports

import "math"

type GPScoord struct {

    lat float64
	long float64
}

type LabelledGPScoord struct {
    GPScoord
	ID int     // point ID
	Label int  // cluster ID
}

const N int=4
const MinPts int=5
const eps float64= 0.0003
const filename string="yellow_tripdata_2009-01-15_9h_21h_clean.csv"

func main() {

    start := time.Now();

    gps, minPt, maxPt := readCSVFile(filename)
	fmt.Printf("Number of points: %d\n", len(gps))

	minPt = GPScoord{40.7, -74.}
	maxPt = GPScoord{40.8, -73.93}

	// geographical limits
	fmt.Printf("SW:(%f , %f)\n", minPt.lat, minPt.long)
	fmt.Printf("NE:(%f , %f) \n\n", maxPt.lat, maxPt.long)

	// Parallel DBSCAN STEP 1.
	incx := (maxPt.long-minPt.long)/float64(N)
	incy := (maxPt.lat-minPt.lat)/float64(N)

	var grid [N][N][]LabelledGPScoord  // a grid of GPScoord slices

	// Create the partition
	// triple loop! not very efficient, but easier to understand

	partitionSize:=0
    for j:=0; j<N; j++ {
        for i:=0; i<N; i++ {

		    for _, pt := range gps {

			    // is it inside the expanded grid cell
			    if (pt.long >= minPt.long+float64(i)*incx-eps) && (pt.long < minPt.long+float64(i+1)*incx+eps) && (pt.lat >= minPt.lat+float64(j)*incy-eps) && (pt.lat < minPt.lat+float64(j+1)*incy+eps) {

                    grid[i][j]= append(grid[i][j], pt) // add the point to this slide
					partitionSize++;
                }
			}
	    }
	}

	// ***
	// This is the non-concurrent procedural version
	// It should be replaced by a producer thread that produces jobs (partition to be clustered)
	// And by consumer threads that clusters partitions
    for j:=0; j<N; j++ {
        for i:=0; i<N; i++ {

		    DBscan(grid[i][j], MinPts, eps, i*10000000+j*1000000)
		}
	}

	// Parallel DBSCAN STEP 2.
	// Apply DBSCAN on each partition
	// ...


	// Parallel DBSCAN step 3.
	// merge clusters
	// *DO NOT PROGRAM THIS STEP

	end := time.Now();
    fmt.Printf("\nExecution time: %s of %d points\n", end.Sub(start), partitionSize)
    fmt.Printf("Number of CPUs: %d", runtime.NumCPU())
}

// Applies DBSCAN algorithm on LabelledGPScoord points
// LabelledGPScoord: the slice of LabelledGPScoord points
// MinPts, eps: parameters for the DBSCAN algorithm
// offset: label of first cluster (also used to identify the cluster)
// returns number of clusters found
func DBscan(coords []LabelledGPScoord, MinPts int, eps float64, offset int) (nclusters int) {



   /*Labels meaning:
   0 : Undefined
   -1 : Not enough neighbors
   */

   for _,point:= range coords{

     //Case 1: Point already processed
     if point.Label != 0{ continue;}

     //Obtaining a list of point's neighbors
     neighbors := rangeQuery( point,coords, eps)

     //Case 2: point does not possess enough meighbors to form a cluster
     if len(neighbors) < MinPts {
       point.Label = -1
       continue;
       }

    //Case 3: All requirements to create a cluster are met

    nclusters += 1
    label := nclusters + offset
    point.Label = label

    for _,neighbor:= range neighbors{

      if neighbor.Label == -1{neighbor.Label = label}
      if neighbor.Label != 0{continue;}
      neighbor.Label = Label

      newNeighbors := rangeQuery( neighbor,coords, eps)

      if len(newNeighbors) >= MinPts { }




     }












   }
   // *** end of fake code.

   // End of DBscan function
   // Printing the result (do not remove)
   fmt.Printf("Partition %10d : [%4d,%6d]\n", offset, nclusters, len(coords))

   return nclusters
}

//My own methods

//distance retourne la distance euclidienne entre les points x et y
func distance(x LabelledGPScoord, y LabelledGPScoord)(float64){
  return math.Sqrt (math.Pow (x.lat - y.lat , 2) +
                         math.Pow(x.long - y.long , 2))

}
/*Range Query retourne un []LabelledGPScoord contenant les eventuels voisins
du  LabelledGPScoord point qui sont listés dans le []LabelledGPScoord coords
*/

func rangeQuery(point LabelledGPScoord,coords []LabelledGPScoord,eps float64 )( []LabelledGPScoord){

  neighbors := make([]LabelledGPScoord,0)
  for _,coord:= range coords{

    if distance(point, coord) <= eps {
      neighbors= append(neighbors, coord)
    }

  }
  return neighbors
}



func seedSet(point LabelledGPScoord,coords []LabelledGPScoord,eps float64, MinPts int)


// reads a csv file of trip records and returns a slice of the LabelledGPScoord of the pickup locations
// and the minimum and maximum GPS coordinates
func readCSVFile(filename string) (coords []LabelledGPScoord, minPt GPScoord, maxPt GPScoord) {

    coords= make([]LabelledGPScoord, 0, 5000)

    // open csv file
    src, err := os.Open(filename)
	defer src.Close()
    if err != nil {
        panic("File not found...")
    }

	// read and skip first line
    r := csv.NewReader(src)
    record, err := r.Read()
    if err != nil {
        panic("Empty file...")
    }

    minPt.long = 1000000.
    minPt.lat = 1000000.
    maxPt.long = -1000000.
    maxPt.lat = -1000000.

	var n int=0

    for {
        // read line
        record, err = r.Read()

        // end of file?
        if err == io.EOF {
            break
        }

        if err != nil {
             panic("Invalid file format...")
        }

		// get lattitude
		lat, err := strconv.ParseFloat(record[9], 64)
        if err != nil {
             panic("Data format error (lat)...")
        }

        // is corner point?
		if lat>maxPt.lat {
		    maxPt.lat= lat
		}
		if lat<minPt.lat {
		    minPt.lat= lat
		}

		// get longitude
		long, err := strconv.ParseFloat(record[8], 64)
        if err != nil {
             panic("Data format error (long)...")
        }

        // is corner point?
		if long>maxPt.long {
		    maxPt.long= long
		}

		if long<minPt.long {
		    minPt.long= long
		}

        // add point to the slice
		n++
        pt:= GPScoord{lat,long}
        coords= append(coords, LabelledGPScoord{pt,n,0})
    }

    return coords, minPt,maxPt
}

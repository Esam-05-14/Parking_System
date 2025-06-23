package parking;
import vehicle.Size;
import parking.facility.Space;
public class ParkingLot{
    private final Space[][] floorPlan;
    public ParkingLot(int floorNumber,int spaceNumber)throws IllegalArgumentException{
        if(floorNumber < 1){
            throw new IllegalArgumentException();
        }
        floorPlan = new Space[floorNumber][spaceNumber];
        for(int i = 0 ; i < floorPlan.length ; i++){
            for(int j = 0 ; j < floorPlan[0].length ; j++){
                floorPlan[i][j] = new Space(i,j);
            }
        }
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = floorPlan.length - 1 ; i >= 0 ;i--){
            for(int j = 0;j<floorPlan[0].length ; j++){
                if(floorPlan[i][j].isTaken()){
                    if (floorPlan[i][j].getOccupyingCarSize()== Size.LARGE){
                        sb.append("L ");
                    }else{
                        sb.append("S ");
                    }
                }
                else{
                    sb.append("X ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
                        // if(j+1<floorPlan[0].length &&floorPlan[i][j+1].getOccupyingCarSize()==Size.LARGE ){
                        //     sb.append("L ");
                        // }else{
                        //     sb.append("X ");
                        // }

    public Space[][] getFloorPlan(){return floorPlan;}
}
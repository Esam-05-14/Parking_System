package parking.facility;
import java.util.ArrayList;
import java.util.List;
import vehicle.Size;
import vehicle.Car;
import parking.ParkingLot;
import parking.facility.Space;
public class Gate{
    private final ArrayList<Car> cars;
    private final ParkingLot parkingLot;

    public Gate(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
        this.cars = new ArrayList<>();
    }
    private Space findTakenSpaceByCar(Car c){
        for(Space[] floor : this.parkingLot.getFloorPlan()){
            for(Space s : floor){
                if(s.isTaken()){
                    if(s.getCarLicensePlate().equals(c.getLicensePlate())){
                        return s;
                    }
                }
            }
        }
        return null;
    }
    private Space findAvailableSpaceOnFloor(int floor , Car c){
        // for(Space[] floors : this.parkingLot.getFloorPlan()){
        
        //     for(Space s : floors){
        //         if(!s.isTaken() && s.getFloorNumber() == floor){
        //             return s;
        //         }
        //     }
            
        // }
        for(int j = 0 ; j < parkingLot.getFloorPlan()[0].length ; j++){
            if(!parkingLot.getFloorPlan()[floor][j].isTaken()){
                if(c.getSpotOccupation()==Size.LARGE){
                    if(j+1 <parkingLot.getFloorPlan()[0].length &&!parkingLot.getFloorPlan()[floor][j+1].isTaken()){
                        parkingLot.getFloorPlan()[floor][j+1].addOccupyingCar(c);
                        parkingLot.getFloorPlan()[floor][j].addOccupyingCar(c);

                        return parkingLot.getFloorPlan()[floor][j+1];
                    }
                }else{
                    parkingLot.getFloorPlan()[floor][j].addOccupyingCar(c);
                    return parkingLot.getFloorPlan()[floor][j];
                }
            }
        }
        return null;
    }
    public Space findAnyAvailableSpaceForCar(Car c){
        for(int i = 0 ; i < parkingLot.getFloorPlan().length ;i++){
            for(int j = 0;j<parkingLot.getFloorPlan()[0].length ; j++){
                if(!parkingLot.getFloorPlan()[i][j].isTaken()){
                    if(c.getSpotOccupation()==Size.LARGE &&j+1 <parkingLot.getFloorPlan()[0].length &&!parkingLot.getFloorPlan()[i][j+1].isTaken() ){
                        parkingLot.getFloorPlan()[i][j+1].addOccupyingCar(c);
                        parkingLot.getFloorPlan()[i][j].addOccupyingCar(c);
                        return parkingLot.getFloorPlan()[i][j+1];
                    }else if(c.getSpotOccupation()==Size.SMALL){
                        parkingLot.getFloorPlan()[i][j].addOccupyingCar(c);
                        return parkingLot.getFloorPlan()[i][j];
                    }
                }
            }
        }
        return null;
    }
    public Space findPreferredAvailableSpaceForCar(Car c){
        Space s = findAvailableSpaceOnFloor(c.getPreferredFloor(),c);
        if(s != null) return s;
        //think about using indexed for loop and ckech the first space if each floor if it has the same car's floor this process it
        for(int i = 0 ; i < parkingLot.getFloorPlan().length ;i++){
            for(int j = 0;j<parkingLot.getFloorPlan()[0].length ; j++){
                if(i== c.getPreferredFloor()&& !parkingLot.getFloorPlan()[i][j].isTaken()){
                    if(c.getSpotOccupation()==Size.LARGE &&j+1 <parkingLot.getFloorPlan()[0].length &&!parkingLot.getFloorPlan()[i][j+1].isTaken() ){
                        parkingLot.getFloorPlan()[i][j+1].addOccupyingCar(c);
                        parkingLot.getFloorPlan()[i][j].addOccupyingCar(c);
                        return parkingLot.getFloorPlan()[i][j+1];
                    }else if(c.getSpotOccupation()==Size.SMALL){
                        parkingLot.getFloorPlan()[i][j].addOccupyingCar(c);
                        return parkingLot.getFloorPlan()[i][j];
                    }
                }
            }
        }
        return null;
        // for(Space[] floor : this.parkingLot.getFloorPlan()){
        //     if(floor[0].getFloorNumber()==c.getPreferredFloor()){
        //         for(Space s : floor){
        //             if(!s.isTaken()){
        //                 return s;
        //             }      
        //         }
        //     }
        // }
        // return null;
    }
    public boolean registerCar(Car c){
        if(this.cars.contains(c))return false;
        // if(c.getPreferredFloor()>parkingLot.getFloorPlan().length){
        //     if(findAnyAvailableSpaceForCar(c)!= null){
        //         this.cars.add(c);
        //         return true;
        //     }
        // }
        // else {
        //     if(findPreferredAvailableSpaceForCar(c)!= null){
        //         this.cars.add(c);
        //         return true;
        //     }
        // }
        if(c.getPreferredFloor() < parkingLot.getFloorPlan().length && findPreferredAvailableSpaceForCar(c)!= null){
            this.cars.add(c);
            return true;
        }else{
            if(findAnyAvailableSpaceForCar(c)!= null){
                this.cars.add(c);
                return true;
            }else{
                return false;
            }
        }
    }
    public void registerCars(Car[] cars){        
        for(Car c : cars){
            if(this.cars.contains(c))continue;
            if(findAnyAvailableSpaceForCar(c)==null){
                System.err.println("No available space for car with number "+c.getLicensePlate());
            }else{
                this.cars.add(c);
            }
        }   
    }
    public void deRegisterCar(String ticketId){
        for (Car c : cars){
            if(c.getTicketId().equals(ticketId)){
                cars.remove(c);
                for(int i = 0 ; i < parkingLot.getFloorPlan().length ;i++){
                    for(int j = 0 ; j < parkingLot.getFloorPlan()[0].length ;j++)
                    if(parkingLot.getFloorPlan()[i][j] == findTakenSpaceByCar(c)){
                        parkingLot.getFloorPlan()[i][j].removeOccupyingCar();
                    }
                }
                return;
            }
        }
    }
}

//  Space s = null;
//             if((s = findAnyAvailableSpaceForCar(c))!=null){
//                 if (c.getOccupyingCarSize() == Size.LARGE){
//                     this.parkingLot.getFloorPlan()[s.getFloorNumber()][s.getSpaceNumber()] = "L"; 
//                 }else{
//                     this.parkingLot.getFloorPlan()[s.getFloorNumber()][s.getSpaceNumber()] = "S"; 
//                 }
//                 this.cars.add(c);
//             }
//             else{
//                 System.err.println("No available space for car with number "+c.getCarLicensePlate());
//             }
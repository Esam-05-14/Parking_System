package parking.facility;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import  org.junit.jupiter.api.Test;
import  org.junit.jupiter.params.ParameterizedTest;
import  org.junit.jupiter.params.provider.CsvSource;
import parking.facility.*;
import parking.ParkingLot;
import vehicle.*;
public class GateTest{
    @Test
    public void testFindAnyAvailableSpaceForCar(){
        ParkingLot p = new ParkingLot(3,3);
        Gate g = new Gate(p);
        Car c1 = new Car("123a",Size.LARGE,1);
        Car c2 = new Car("133g",Size.SMALL,1);
        Car c3 = new Car("133gds",Size.SMALL,0);
        Car[] cars = {c1,c2,c3};
        Space s = g.findAnyAvailableSpaceForCar(c1);
        assertEquals(p.getFloorPlan()[0][1],s);
    }
    @ParameterizedTest
    @CsvSource({"'122a',LARGE,1","'898j',SMALL,0"})
    public void testFindPreferredAvailableSpaceForCar(String plate,Size size, int preferredFloor){
        Car c = new Car(plate , size , preferredFloor);
        ParkingLot p = new ParkingLot(3,3);
        Gate g = new Gate(p);
        Space s = g.findPreferredAvailableSpaceForCar(c);
        if(size == Size.SMALL){
            assertEquals(s,p.getFloorPlan()[preferredFloor][0]);    
            
        }else{
            assertEquals(s,p.getFloorPlan()[preferredFloor][1]);

        }

    }
    @ParameterizedTest
    @CsvSource({"'122a',LARGE,1","'898j',LARGE,3","'ssg9',SMALL,0"})
    public void testRegisterCar(String plate , Size size , int preferredFloor){
        Car c = new Car(plate , size , preferredFloor);
        ParkingLot p = new ParkingLot(3,3);
        Gate g = new Gate(p);
        assertTrue(g.registerCar(c));
    }
    @ParameterizedTest
    @CsvSource({"'122a',LARGE,1"})
    public void testDeRegisterCar(String plate , Size size , int preferredFloor){
        ParkingLot p = new ParkingLot(3,3);
        Gate g = new Gate(p);
        Car c = new Car(plate , size , preferredFloor);
        c.setTicketId("11");

        g.registerCar(c);
        assertEquals("X X X \nL L X \nX X X \n",p.toString());
        g.deRegisterCar("11");
        assertEquals("X X X \nX X X \nX X X \n",p.toString());




    }
}
package parking;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import  org.junit.jupiter.api.Test;
import  org.junit.jupiter.params.ParameterizedTest;
import  org.junit.jupiter.params.provider.CsvSource;
import parking.facility.*;
import parking.ParkingLot;
import vehicle.*;
public class ParkingLotTest{
    @Test
    public void testConstructorWithInvalidValues(){
        try{
            ParkingLot p = new ParkingLot(0,1);
            fail();

        }catch(IllegalArgumentException e){
        }
    }
    @Test
    public void testTextualRepresentation(){
        ParkingLot p = new ParkingLot(3,3);
        Gate g = new Gate(p);
        Car c1 = new Car("123a",Size.LARGE,1);
        Car c2 = new Car("133g",Size.SMALL,1);
        Car c3 = new Car("133gds",Size.SMALL,0);
        Car[] cars = {c1,c2,c3};

        g.registerCars(cars);
        g.registerCar(c1);
        g.registerCar(c2);
        g.registerCar(c3);
        c1.setTicketId("11");
        c2.setTicketId("22");
        c3.setTicketId("33");
        assertEquals("X X X \nS X X \nL L S \n",p.toString());

    }
}
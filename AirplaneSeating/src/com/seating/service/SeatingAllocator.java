package com.seating.service;

import com.seating.dto.SeatsPassangersDto;
import com.seating.entities.Passenger;
import com.seating.entities.SeatLayout;
import com.seating.entities.SeatTypes;

import java.lang.reflect.Array;
import java.util.*;

public class SeatingAllocator {

    SeatTypes seatTypes = new SeatTypes();
    public Integer passengerId = 1;

    public void fillSeats(SeatLayout seatLayout, SeatsPassangersDto seatsPassangerDto){

        seatTypes.aisle = separateAsileColumns(seatsPassangerDto);

        seatTypes.center= separateCenterColumns(seatsPassangerDto);

        seatTypes.window= separateWindowColumns(seatsPassangerDto);

        allocateAsileSeats(0,seatLayout,seatsPassangerDto);
        allocateWindowSeats(0,seatLayout,seatsPassangerDto);
        allocateCenterSeats(0,seatLayout,seatsPassangerDto);
    }

     private void allocateAsileSeats(Integer currentRow, SeatLayout seatLayout,SeatsPassangersDto seatSizesDto){

        if(  passengerId > seatSizesDto.passangers) {
            return;
        }

        int continueCutOff =0;
        for(int seatLayoutCol =0;seatLayoutCol<4;seatLayoutCol++) {
            if(seatTypes.aisle.get(seatLayoutCol) != null  && seatSizesDto.sizes.get(seatLayoutCol)[0]-1 >= currentRow) {
                for (int index = 0; index < seatTypes.aisle.get(seatLayoutCol).size(); index++) {
                    seatLayout.seatingLayout.get(seatLayoutCol)[currentRow][seatTypes.aisle.get(seatLayoutCol).get(index)].id = passengerId++;
                }
            }
            else{
                if(seatSizesDto.sizes.get(seatLayoutCol)[0]-1 < currentRow)
                    continueCutOff++;

                if(continueCutOff == 4){
                    return;
                }
                continue;
            }

        }
         allocateAsileSeats(currentRow+1,seatLayout,seatSizesDto);
     }

    private void allocateWindowSeats(Integer currentRow, SeatLayout seatLayout,SeatsPassangersDto seatSizesDto){

        if(  passengerId > seatSizesDto.passangers) {
            return;
        }

        int continueCutOff=0;
        for(int seatLayoutCol =0, index=0;seatLayoutCol<4;seatLayoutCol+=3,index++) {
            if (seatSizesDto.sizes.get(seatLayoutCol)[0] - 1 >= currentRow) {
                seatLayout.seatingLayout.get(seatLayoutCol)[currentRow][seatTypes.window.get(index)].id = passengerId++;
            }
            else {
                if(seatSizesDto.sizes.get(seatLayoutCol)[0]-1 < currentRow)
                    continueCutOff++;

                if(continueCutOff == 2){
                    return;
                }
                continue;
            }
        }
        allocateWindowSeats(currentRow+1,seatLayout,seatSizesDto);
    }

    private void allocateCenterSeats(Integer currentRow, SeatLayout seatLayout,SeatsPassangersDto seatSizesDto){

        if(  passengerId > seatSizesDto.passangers) {
            return;
        }

        int continueCutOff = 0;
        for(int seatLayoutCol =0;seatLayoutCol<4;seatLayoutCol++) {

            if(seatTypes.center.get(seatLayoutCol) != null  && seatSizesDto.sizes.get(seatLayoutCol)[0]-1 >= currentRow) {
                for (int index = 0; index < seatTypes.center.get(seatLayoutCol).size(); index++) {
                    seatLayout.seatingLayout.get(seatLayoutCol)[currentRow][seatTypes.center.get(seatLayoutCol).get(index)].id = passengerId++;
                }
            }
            else{
                if(seatSizesDto.sizes.get(seatLayoutCol)[0]-1 < currentRow)
                    continueCutOff++;

                if(continueCutOff == 4){
                    return;
                }
                continue;
            }


        }
        allocateCenterSeats(currentRow+1,seatLayout,seatSizesDto);
    }

    private ArrayList<ArrayList<Integer>> separateAsileColumns(SeatsPassangersDto seatsPassangerDto){
        ArrayList<ArrayList<Integer>> toRet = new ArrayList<>();

        if(seatsPassangerDto.sizes.get(0)[1] > 1)
        {
            ArrayList<Integer> columns = new ArrayList<>();
            columns.add(seatsPassangerDto.sizes.get(0)[1]-1);
            toRet.add(columns);
        }
        else
        {
            toRet.add(null);
        }

        if(seatsPassangerDto.sizes.get(1)[1] > 1)
        {
            ArrayList<Integer> columns = new ArrayList<>();
            columns.add(0);
            columns.add(seatsPassangerDto.sizes.get(1)[1]-1);
            toRet.add(columns);
        }
        else
        {
            ArrayList<Integer> columns = new ArrayList<>();
            columns.add(0);
            toRet.add(columns);
        }

        if(seatsPassangerDto.sizes.get(2)[1] > 1)
        {
            ArrayList<Integer> columns = new ArrayList<>();
            columns.add(0);
            columns.add(seatsPassangerDto.sizes.get(2)[1]-1);
            toRet.add(columns);
        }
        else
        {
            ArrayList<Integer> columns = new ArrayList<>();
            columns.add(0);
            toRet.add(columns);
        }

        if(seatsPassangerDto.sizes.get(3)[1] > 1)
        {
            ArrayList<Integer> columns = new ArrayList<>();
            columns.add(0);
            toRet.add(columns);
        }
        else
        {
            toRet.add(null);
        }
        return toRet;
    }

    private ArrayList<ArrayList<Integer>> separateCenterColumns(SeatsPassangersDto seatsPassangerDto){

        ArrayList<ArrayList<Integer>> toRet = new ArrayList<>();

        for(int seatLayoutCol = 0; seatLayoutCol<4;seatLayoutCol++) {

            if (seatsPassangerDto.sizes.get(seatLayoutCol)[1] > 2) {

                ArrayList<Integer> columns = new ArrayList<>();
                for (int col = 1; col != seatsPassangerDto.sizes.get(seatLayoutCol)[1] - 1; col++)
                    columns.add(col);
                toRet.add(columns);
            } else {

                toRet.add(null);
            }
        }
        return toRet;

    }

    private ArrayList<Integer> separateWindowColumns(SeatsPassangersDto seatsPassangerDto){
        ArrayList<Integer> toRet = new ArrayList<>();

        toRet.add(0);
        toRet.add(seatsPassangerDto.sizes.get(3)[1]-1);

        return toRet;
    }
}

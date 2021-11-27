package com.seating.userinterface.impl;

import com.seating.dto.SeatsPassangersDto;
import com.seating.entities.Passenger;
import com.seating.entities.SeatLayout;
import com.seating.service.SeatingAllocator;
import com.seating.userinterface.UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UserInterfaceImpl implements UserInterface {

    SeatingAllocator seatingAllocator = new SeatingAllocator();

    @Override
    public void getUserInputs() throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        SeatsPassangersDto seatPassangersDto =  new SeatsPassangersDto();
        SeatLayout seatLayout = initializeSeats(reader,seatPassangersDto);

        String passengers= reader.readLine();
        seatPassangersDto.passangers = Integer.parseInt(passengers);

        seatingAllocator.fillSeats(seatLayout,seatPassangersDto);

        printSeats(seatLayout,seatPassangersDto);

    }

    private void printSeats(SeatLayout seatLayout,SeatsPassangersDto seatPassangersDto){
        for(int i=0;i<4;i++){
            for(int row=0;row <seatPassangersDto.sizes.get(i)[0];row++){
                for(int col = 0; col<seatPassangersDto.sizes.get(i)[1];col++){
                    System.out.print(seatLayout.seatingLayout.get(i)[row][col].id + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private SeatLayout initializeSeats(BufferedReader reader, SeatsPassangersDto seatingCapacityDto) throws IOException {
        SeatLayout seatLayout = new SeatLayout();

        seatingCapacityDto.sizes = new ArrayList<>();
        seatLayout.seatingLayout = new ArrayList<>();

        String layoutSize = reader.readLine();
        seatingCapacityDto.sizes.add(splitSeatSizes(layoutSize));
        seatLayout.seatingLayout.add(initializeLayout(seatingCapacityDto.sizes.get(0)));

        layoutSize = reader.readLine();
        seatingCapacityDto.sizes.add(splitSeatSizes(layoutSize));
        seatLayout.seatingLayout.add(initializeLayout(seatingCapacityDto.sizes.get(1)));

        layoutSize = reader.readLine();
        seatingCapacityDto.sizes.add(splitSeatSizes(layoutSize));
        seatLayout.seatingLayout.add(initializeLayout(seatingCapacityDto.sizes.get(2)));

        layoutSize = reader.readLine();
        seatingCapacityDto.sizes.add(splitSeatSizes(layoutSize));
        seatLayout.seatingLayout.add(initializeLayout(seatingCapacityDto.sizes.get(3)));

        return seatLayout;
    }

    private int[] splitSeatSizes(String rowColumn){
        String[] sizesStr=rowColumn.split(",");
        int[] sizes = new int[2];
        sizes[0]=Integer.parseInt(sizesStr[0]);
        sizes[1]=Integer.parseInt(sizesStr[1]);
        return sizes;
    }
    private Passenger[][] initializeLayout(int[] rowColumn) {

        Passenger[][] toRet = new Passenger[rowColumn[0]][rowColumn[1]];
        for (int row = 0; row < rowColumn[0]; row++) {
            for (int column = 0; column < rowColumn[1]; column++) {
                toRet[row][column] = new Passenger();
            }
        }
        return toRet;
    }
}

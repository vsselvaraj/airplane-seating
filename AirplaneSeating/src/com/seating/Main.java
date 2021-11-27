package com.seating;

import com.seating.userinterface.UserInterface;
import com.seating.userinterface.impl.UserInterfaceImpl;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        UserInterface uiImpl = new UserInterfaceImpl();
        uiImpl.getUserInputs();
    }
}

package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInputHandler {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //-------------------------------------------------
    public static String getString()
    {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error!");
            return null;
        }
    }
    //-------------------------------------------------
    public static int getInt()
    {
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.print("Invalid entry. Please enter a number:");
            return 0;
        }
    }
    //-------------------------------------------------
    public static Date getDateFromInput() throws IOException
    {
        Date date = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print("Enter the date in the format (yyyy): ");
                String inputDateStr = getString();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
                date = dateFormat.parse(inputDateStr);

                isValid = true;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter date in the format (yyyy).");
            }
        }
        return date;
    }
    //-------------------------------------------------
}

package apptSys.model;

import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class Logger {

    private static File log = new File("../utils/log.txt");
    private static User currUser = null;
    private static String errMessage = "Unable to create user logs. For security purposes, this application will close."
            + " Please run this application with Administrator writes.";

    private static void write (String print) {

        try {
            BufferedWriter logger = new BufferedWriter(new FileWriter(log));
            logger.append(print);
            logger.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, errMessage);
            alert.showAndWait();
            System.exit(-1);
        }
    }
    private static void write (ArrayList<String> printList) {
        try {
            BufferedWriter logger = new BufferedWriter(new FileWriter(log));
            for (String print : printList) {
                logger.append(print);
            }

            logger.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, errMessage);
            alert.showAndWait();
            System.exit(-1);
        }
    }

    public static void logSignin (User newUser) {
        currUser = newUser;
        StringBuilder entry = new StringBuilder();
        entry.append(LocalDateTime.now().atZone(ZoneId.of("GMT")).toString()).append(" - ");
        entry.append(currUser.getUserName()).append(" has logged in successfully.\n");

        write(entry.toString());
    }

    public static void logSignout () {
        StringBuilder entry = new StringBuilder();
        entry.append(LocalDateTime.now().atZone(ZoneId.of("GMT")).toString()).append(" - ");
        entry.append(currUser.getUserName()).append(" has been logged out.\n\n");

        write(entry.toString());
        currUser = null;
    }
}





















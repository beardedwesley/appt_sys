package apptSys.model;

import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Logger {

    private static Path path = Paths.get("src", "apptSys", "utils", "log.txt");
    private static File log = path.toFile();
    private static User currUser = null;
    private static String errMessage = "Unable to create user logs. For security purposes, this application will close."
            + " Please run this application with Administrator writes.";

    private static void write (String print) {

        BufferedWriter logger;
        boolean exists = false;
        try {
            exists = path.toRealPath().isAbsolute();
        } catch (IOException e) {}
        if (exists){
            try {
                logger = new BufferedWriter(new FileWriter(log, true));
                logger.write(print);
                logger.close();
                return;
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, errMessage);
                alert.showAndWait();
                System.exit(-1);
            }
        } else {
            try {
                logger = new BufferedWriter(new FileWriter(log));
                logger.write(print);
                logger.close();
                return;
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, errMessage);
                alert.showAndWait();
                System.exit(-1);
            }
        }
    }
    private static void write (ArrayList<String> printList) {
        try {
            BufferedWriter logger = new BufferedWriter(new FileWriter(log, path.toRealPath().isAbsolute()));
            for (String print : printList) {
                logger.write(print);
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
        entry.append(ZonedDateTime.now().toString()).append(" - ");
        entry.append(currUser.getUserName()).append(" has logged in successfully.\n");

        write(entry.toString());
    }

    public static void logSignout () {
        StringBuilder entry = new StringBuilder();
        entry.append(ZonedDateTime.now().toString()).append(" - ");
        entry.append(currUser.getUserName()).append(" has been logged out.\n\n");

        write(entry.toString());
        currUser = null;
    }
}





















package com.medical;

import java.awt.Desktop;
import java.net.URI;

public class MedicalBillingLauncher {

    public static void main(String[] args) {

        try {

            ProcessBuilder process = new ProcessBuilder(
                    "java",
                    "-jar",
                    "MedicalBillingSystem-0.0.1-SNAPSHOT.jar"
            );

            process.inheritIO();

            process.start();

            Thread.sleep(10000);

            if (Desktop.isDesktopSupported()) {

                Desktop.getDesktop().browse(
                        new URI("http://localhost:8081")
                );

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
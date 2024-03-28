package main.java.com.exemple.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KitC {

    public static void cCompiler()
    {
        try {
            Process compileProcess = new ProcessBuilder("gcc", "C:\\Users\\Utilisateur\\Documents\\InitR\\APP\\M1-InitRC\\RC\\rc\\src\\main\\resources\\test.c", "-o", "test").start();
            int compileExitCode = compileProcess.waitFor();
            if (compileExitCode == 0) {
                System.out.println("Compilation successful.");
                Process execProcess = new ProcessBuilder(".\\test").start();
                BufferedReader reader = new BufferedReader(new InputStreamReader (execProcess.getInputStream()));
                String line;
                System.out.println("Output of the C program:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                int execExitCode = execProcess.waitFor();
                System.out.println("C program exited with code: " + execExitCode);
            } else {
                System.out.println("Compilation failed.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}

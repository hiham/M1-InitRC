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

    public static void runPythonScriptInference(String scriptPath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("python","-W","ignore", scriptPath,"C:\\Users\\Utilisateur\\Documents\\InitR\\APP\\M1-InitRC\\approche_avec_apprentissage\\inference\\test");
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        System.out.println("Python script execution completed with exit code: " + exitCode);
    }

    public static void runPythonScriptLearning(String nomModel,String wLoss,String batch_size,String imgSize,String nbE) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("python","C:\\Users\\Utilisateur\\Documents\\InitR\\APP\\M1-InitRC\\approche_avec_apprentissage\\entrainement\\pTileMaker.py","74","256");
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Python script finished successfully.");
            //runPythonScriptTrain(nomModel, wLoss, batch_size, imgSize, nbE);
        } else {
            // Script exited with an error, handle accordingly
            System.err.println("Python script exited with error code: " + exitCode);
        }
    }

    public static void runPythonScriptTrain(String nomModel,String wLoss,String batch_size,String imgSize,String nbE) throws IOException, InterruptedException {
        //python main.py train --logname <nommodele> --wloss <wLoss> --batch_size <batch_size> --img_size <img> --nepochs <nbE>
        ProcessBuilder pb = new ProcessBuilder(
                "python",
                "C:\\Users\\Utilisateur\\Documents\\InitR\\APP\\M1-InitRC\\approche_avec_apprentissage\\entrainement\\main.py",
                "--logname",nomModel, "--wloss" ,wLoss, "--batch_size" ,batch_size, "--img_size", imgSize,"--nepochs", nbE);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        System.out.println("Python script execution completed with exit code: " + exitCode);
    }

}

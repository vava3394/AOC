import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import outils.number.MyNumber;

/**
 * author vportal
 * 
 */
public class App {
    public static void main(String[] args) {

        try {
            String path = getPath();
            String sourceFileCompilePath = path.substring(0, path.length() - 5).replace("/", ".");

            compileJavaFile(path);

            ClassLoader classLoader = App.class.getClassLoader();
            Class<?> dayClass = classLoader.loadClass(sourceFileCompilePath);

            Method mainMethod = dayClass.getMethod("main", String[].class);

            mainMethod.invoke(null, (Object) args);

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ExceptionInInitializerError | IllegalArgumentException
                | java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void compileJavaFile(String sourceFilePath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        int compilationResult = compiler.run(null, null, null, sourceFilePath);

        if (compilationResult == 0) {
            System.out.println("Compilation réussie");
        } else {
            System.out.println("Échec de la compilation");
        }
    }

    private static String getPath() {
        Scanner scanner = new Scanner(System.in);
        String path = "";
        while (!CheckFileExistence(path)) {
            String text;

            System.out.print("Select year : ");
            text = scanner.nextLine();
            while (null == text || !MyNumber.isNumber(text)) {
                System.out.print("Select year valid: ");
                text = scanner.nextLine();
            }
            int year = Integer.parseInt(text);

            System.out.print("Select Day : ");
            text = scanner.nextLine();

            while (null == text || !MyNumber.isNumber(text)) {
                System.out.print("Select day valid: ");
                text = scanner.nextLine();
            }
            int dayNumber = Integer.parseInt(text);

            String className = "Day" + dayNumber;

            String yearDirectory = "_" + year + "/";

            path = yearDirectory + className + "/" + className + ".java";
        }
        scanner.close();
        return path;

    }

    private static boolean CheckFileExistence(String cheminFichier) {
        File fichier = new File(cheminFichier);
        if (fichier.exists()) {
            return true;
        }
        return false;
    }
}

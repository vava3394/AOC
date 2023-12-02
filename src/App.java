import java.lang.reflect.Method;
import java.util.Scanner;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * author vportal
 * 
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Select year : ");
        int year = scanner.nextInt();

        System.out.print("Select Day : ");
        int dayNumber = scanner.nextInt();

        String className = "Day" + dayNumber;

        scanner.close();

        try {
            String yearDirectory = "_" + year + "/";
            String sourceFilePath = yearDirectory + className + "/" + className + ".java";
            String sourceFileCompilePath = (yearDirectory + className + "/" + className).replace("/", ".");

            compileJavaFile(sourceFilePath);

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
}

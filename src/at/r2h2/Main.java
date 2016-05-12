package at.r2h2;

import org.jhades.JHades;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;


public class Main {
    /* Search for a class in the classpath defined in the CLASSPASS environment variable
        CAVE CANEM: the classpath implied from the class loader of the classpathtool jar is included!
     */

    private static void addSoftwareLibrary(File file) throws Exception {
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{file.toURI().toURL()});
    }

    public static void main(String[] args) {

        for (String cp: System.getenv("CLASSPATH").split(":")) {
            try {
                addSoftwareLibrary(new File(cp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new JHades().printClassLoaderNames();
        //new JHades().dumpClassloaderInfo();
        System.out.println(">> contents of CLASSPATH environment variable");
        System.out.println(System.getenv("CLASSPATH").replace(':', '\n'));
        new JHades().printClasspath();
        //new JHades().findClassByName("at.wien.ma14.pvzd.validatexsd.cli.XSDValidatorCLI");     // fully qualified
        new JHades().findByRegex(args[0]);
        System.out.println("--- classpath tool (0.4)");
    }
}

package de.conxult.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MetaInfServices {

    static public List<Class> locateClasses(Class clazz) {
        List<Class> classes = new ArrayList<Class>();
        for (String line : locateLines(clazz)) {
            try {
                classes.add(Class.forName(line));
            } catch (ClassNotFoundException cnfEx) {
            }
        }

        return classes;
    }

    static public List<String> locateLines(Class clazz) {
        return locateLines(clazz.getName());
    }

    static public List<String> locateLines(String resourceName) {
        List<String> lines = new ArrayList<String>();
        try {
            for (Enumeration<URL> urls = MetaInfServices.class.getClassLoader().getResources("META-INF/services/" + resourceName); (urls.hasMoreElements());) {
                locateStream(lines, urls.nextElement().openStream());
            }
            for (Enumeration<URL> urls = MetaInfServices.class.getClassLoader().getResources("MetaInfServices/" + resourceName); (urls.hasMoreElements());) {
                locateStream(lines, urls.nextElement().openStream());
            }
            for (Enumeration<URL> urls = MetaInfServices.class.getClassLoader().getResources("MetaInfServices/" + resourceName + ".zip"); (urls.hasMoreElements());) {
                ZipInputStream zipInputStream = new ZipInputStream(urls.nextElement().openStream());
                for (ZipEntry zipEntry = zipInputStream.getNextEntry(); (zipEntry != null); zipEntry = zipInputStream.getNextEntry()) {
                    locateStream(lines, zipInputStream);
                }
            }
        } catch (IOException ioEx) {
        }
        return lines;
    }

    static private void locateStream(List<String> lines, InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        for (String line = reader.readLine(); (line != null); line = reader.readLine()) {
            line = line.trim();
            if (!line.isEmpty() && !line.startsWith("#") && !lines.contains(line)) {
                lines.add(line);
            }
        }
    }
}

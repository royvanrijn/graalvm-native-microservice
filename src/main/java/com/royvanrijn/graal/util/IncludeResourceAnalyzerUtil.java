package com.royvanrijn.graal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * Small utility I wrote to analyze the -H:IncludeResources option in GraalVM and test the used regex.
 */
public class IncludeResourceAnalyzerUtil {

    public static void main(String[] args) {
        new IncludeResourceAnalyzerUtil().test(".*.properties|.*META-INF/persistence.xml|.*.xsd");
    }

    public void test(String regExp) {

        if (regExp.length() == 0) {
            return;
        }

        Pattern pattern = Pattern.compile(regExp);

        final Set<File> todo = new HashSet<>();
        // Checkstyle: stop
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader instanceof URLClassLoader) {
            for (URL url : ((URLClassLoader) contextClassLoader).getURLs()) {
                try {
                    final File file = new File(url.toURI());
                    todo.add(file);
                } catch (URISyntaxException | IllegalArgumentException e) {
                    throw new RuntimeException("Unable to handle imagecp element '" + url.toExternalForm() + "'. Make sure that all imagecp entries are either directories or valid jar files.");
                }
            }
        }
        // Checkstyle: resume
        for (File element : todo) {
            try {
                if (element.isDirectory()) {
                    scanDirectory(element, "", pattern);
                } else {
                    scanJar(element, pattern);
                }
            } catch (IOException ex) {
                throw new RuntimeException("Unable to handle classpath element '" + element + "'. Make sure that all classpath entries are either directories or valid jar files.");
            }
        }
    }

    private void scanDirectory(File f, String relativePath, Pattern... patterns) throws IOException {
        if (f.isDirectory()) {
            for (File ch : f.listFiles()) {
                scanDirectory(ch, relativePath + "/" + ch.getName(), patterns);
            }
        } else {
            if (matches(patterns, relativePath)) {
                try (FileInputStream is = new FileInputStream(f)) {
                    System.out.println("Adding FILE: " + relativePath.substring(1) + "\t"+ is);
                }
            }
        }
    }

    private static void scanJar(File element, Pattern... patterns) throws IOException {
        JarFile jf = new JarFile(element);
        Enumeration<JarEntry> en = jf.entries();
        while (en.hasMoreElements()) {
            JarEntry e = en.nextElement();
            if (e.getName().endsWith("/")) {
                continue;
            }
            if (matches(patterns, e.getName())) {
                try (InputStream is = jf.getInputStream(e)) {
                    System.out.println("Adding JAR: " + e.getName() + "\t"+ is);
                }
            }
        }
    }

    private static boolean matches(Pattern[] patterns, String relativePath) {
        for (Pattern p : patterns) {
            if (p.matcher(relativePath).matches()) {
                return true;
            }
        }
        return false;
    }
}

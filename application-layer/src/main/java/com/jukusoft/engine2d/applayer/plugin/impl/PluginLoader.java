package com.jukusoft.engine2d.applayer.plugin.impl;

import com.jukusoft.engine2d.plugin.Pluggable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class PluginLoader {

    //see also: https://www.java-blog-buch.de/d-plugin-entwicklung-in-java/2/

    public static List<Pluggable> loadPlugins(File pluginDir) throws IOException {
        File[] plugJars = pluginDir.listFiles(new JARFileFilter());
        ClassLoader cl = new URLClassLoader(PluginLoader.fileArrayToURLArray(plugJars));
        List<Class<Pluggable>> plugClasses = PluginLoader.extractClassesFromJARs(plugJars, cl);
        return PluginLoader.createPluggableObjects(plugClasses);
    }

    private static URL[] fileArrayToURLArray(File[] files) throws MalformedURLException {

        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; i++) {
            urls[i] = files[i].toURI().toURL();
        }
        return urls;
    }

    private static List<Class<Pluggable>> extractClassesFromJARs(File[] jars, ClassLoader cl) throws IOException {

        List<Class<Pluggable>> classes = new ArrayList<Class<Pluggable>>();
        for (File jar : jars) {
            classes.addAll(PluginLoader.extractClassesFromJAR(jar, cl));
        }
        return classes;
    }

    @SuppressWarnings("unchecked")
    private static List<Class<Pluggable>> extractClassesFromJAR(File jar, ClassLoader cl) throws IOException {

        List<Class<Pluggable>> classes = new ArrayList<>();

        JarInputStream jaris = new JarInputStream(new FileInputStream(jar));
        JarEntry ent = null;
        while ((ent = jaris.getNextJarEntry()) != null) {
            if (ent.getName().toLowerCase().endsWith(".class")) {
                try {
                    Class<?> cls = cl.loadClass(ent.getName().substring(0, ent.getName().length() - 6).replace('/', '.'));
                    if (PluginLoader.isPluggableClass(cls)) {
                        classes.add((Class<Pluggable>) cls);
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Can't load Class " + ent.getName());
                    e.printStackTrace();
                }
            }
        }
        jaris.close();
        return classes;
    }

    private static boolean isPluggableClass(Class<?> cls) {
        for (Class<?> i : cls.getInterfaces()) {
            if (i.equals(Pluggable.class)) {
                return true;
            }
        }

        return false;
    }

    private static List<Pluggable> createPluggableObjects(List<Class<Pluggable>> pluggables) {
        List<Pluggable> plugs = new ArrayList<>(pluggables.size());

        for (Class<Pluggable> plug : pluggables) {
            try {
                plugs.add(plug.newInstance());
            } catch (InstantiationException e) {
                System.err.println("Can't instantiate plugin: " + plug.getName());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.err.println("IllegalAccess for plugin: " + plug.getName());
                e.printStackTrace();
            }
        }

        return plugs;
    }

}

package com.jass.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static java.lang.Thread.currentThread;

public class FilePathUtils {

    private static final String RESOURCE_ROOT_FOLDER = "src/test/resources";
    private static final String DOWNLOADS_FOLDER= "Downloads";
    private static final String CURRENT_USER_HOME_DIRECTORY = System.getProperty("user.home");

    private enum Location {
        RESOURCES,
        DOWNLOADS
    }

    public static String getFullPath(String relativePath) {
        return getFullPath(Location.RESOURCES, relativePath);
    }

    public static String getDownloadsFullPath(String relativePath) {
        return getFullPath(Location.DOWNLOADS, relativePath);
    }

    public static File getResourcesFile(String relativePath) {
        String path = RESOURCE_ROOT_FOLDER + "/" + relativePath;
        return new File(path);
    }

    public static File getDownloadsFile(String relativePath) {
        String path = CURRENT_USER_HOME_DIRECTORY + "/" + DOWNLOADS_FOLDER + "/" + relativePath;
        return new File(path);
    }

    public static String getAbsolutePath(String relativePath) {
        if (relativePath.isEmpty()) return "";
        URL resUrl = FilePathUtils.class.getClassLoader().getResource(relativePath);

        String path = null;

        if (resUrl == null)
            throw new IllegalArgumentException("No any resources by following path: " + relativePath);

        try {
            path = Paths.get(resUrl.toURI()).toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String getFilePath(String dirPath, String fileName) {
        URL resource = currentThread().getContextClassLoader().getResource(dirPath + fileName);
        try {
            if (resource == null) {
                throw new RuntimeException("Resource " + fileName +
                        " could not be found or the invoker doesn't have adequate privileges to get the resource");
            }
            return Paths.get(resource.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        new AssertionError("Resource was not found!");
        return null;
    }

    public static String getFilePath(String fileName) {
        URL resource = currentThread().getContextClassLoader().getResource(fileName);
        try {
            if (resource == null) {
                throw new RuntimeException("Resource " + fileName +
                        " could not be found or the invoker doesn't have adequate privileges to get the resource");
            }
            return Paths.get(resource.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        new AssertionError("Resource was not found!");
        return null;
    }

    private static String getFullPath(Location location, String relativePath) {
        if (relativePath.isEmpty()) {
            return "";
        } else {
            try {
                switch (location) {
                    case RESOURCES: return getResourcesFile(relativePath).getCanonicalPath();
                    case DOWNLOADS: return getDownloadsFile(relativePath).getCanonicalPath();
                    default: throw new IllegalArgumentException("Unknown location: " + location);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
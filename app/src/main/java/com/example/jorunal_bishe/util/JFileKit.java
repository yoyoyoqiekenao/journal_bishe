package com.example.jorunal_bishe.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;

import com.example.jorunal_bishe.exceptions.BaseException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class JFileKit {
    /**
     * Detection of SD is readable
     *
     * @return
     */
    public static boolean sdcardIsReadyForRead() {
        String state = Environment.getExternalStorageState();
        return state != null ? state.equals(Environment.MEDIA_MOUNTED_READ_ONLY) : false;
    }

    /**
     * Detection of SD can write
     *
     * @return
     */
    public static boolean sdcardIsReadyForWrite() {
        String state = Environment.getExternalStorageState();
        return state != null ? state.equals(Environment.MEDIA_MOUNTED) : false;
    }

    /**
     * Access to the path of the SDCard
     *
     * @return
     */
    public static String getSDCardPath() {
        if (!sdcardIsReadyForWrite()) {
            throw new BaseException("SDCard Is not a read and write mode");
        }
        return Environment.getExternalStorageDirectory()
                .getAbsolutePath();
    }

    /**
     * Access to the path of the hard disk cache
     *
     * @author blue
     */
    public static String getDiskCacheDir(Context context) {
        if (sdcardIsReadyForWrite()
                || !Environment.isExternalStorageRemovable()) {
            File file = context.getExternalCacheDir();
            return file != null ? file.getAbsolutePath()
                    : context.getCacheDir().getAbsolutePath();
        } else {
            return context.getCacheDir().getAbsolutePath();
        }
    }

    /**
     * Get the path address of the hard disk cache based on the incoming
     * uniqueName
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (sdcardIsReadyForWrite()
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * Create a directory or file in SDCard
     * <p/>
     * If you have a file with the same name, delete the file and then create
     * the same name. The folder does not do any operations.
     *
     * @param path Folder relative path
     * @throws BaseException
     */
    public static String createFileOnSDCard(String path) {
        if (!sdcardIsReadyForWrite()) {
            throw new BaseException("SD card not write");
        }

        if (!path.startsWith(File.separator)) {
            path = File.separator + path;
        }
        File file = new File(getSDCardPath() + path);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
        if (!createFolder(file)) {
            throw new BaseException("file/folder " + path + " create fail");
        }
        return getSDCardPath() + path;
    }

    /**
     * Delete SDCard files or folders
     *
     * @param path file path
     */
    public static void deleteFileOnSDCard(String path) {
        if (!sdcardIsReadyForWrite()) {
            throw new BaseException("SDCard not write");
        }

        if (!path.startsWith(File.separator)) {
            path = getSDCardPath() + File.separator + path;
        }
        File file = new File(path);
        if (!deleteFile(file)) {
            throw new BaseException("delete file " + path + " fail");
        }
    }

    /**
     * get data/data/package/files folder
     * <p/>
     * The directory is automatically deleted when you uninstall the program
     *
     * @param context
     * @return
     */
    public static String getDataFolderPath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    public static String getDatabaseDir(Context context) {
        return (context.getFilesDir().getPath() + "/databases");
    }

    /**
     * in data/data/package/files create file or folder
     * <p/>
     * If you have a file with the same name, delete the file and then create
     * the same name. The folder does not do any operations.
     * <p/>
     * The directory is automatically deleted when you uninstall the program
     *
     * @param path Folder absolute path
     * @throws BaseException
     */
    public static String createFileOnDataFolder(Context contenxt, String path) {
        if (!path.startsWith(File.separator)) {
            path = getDataFolderPath(contenxt) + File.separator + path;
        }

        File file = new File(path);
        deleteFile(file);

        if (!createFolder(file)) {
            throw new BaseException("file/folder " + path + " create fail");
        }
        return path;
    }

    /**
     * delete data/data/package/files file or folder
     * <p/>
     * The directory is automatically deleted when you uninstall the program
     *
     * @param path file path
     */
    public static void deleteFileOnDataFolder(String path) {
        if (!path.startsWith(File.separator)) {
            path = File.separator + path;
        }

        File file = new File(getSDCardPath() + path);
        if (!deleteFile(file)) {
            throw new BaseException("delete file " + path + " fail");
        }
    }

    /**
     * Recursively delete all files in the specified folder (including the
     * folder)
     *
     * @param file
     * @author andrew
     */
    public static boolean deleteAll(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isFile() || file.list().length == 0) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteAll(f);// delete file
            }
            return file.delete();
        }
    }

    public static boolean deleteFile(File file) {
        return file.isFile() && file.exists() ? file.delete() : false;
    }

    /**
     * Deletes all files in the specified folder, but does not delete the
     * directory.
     *
     * @param path
     */
    public static void deleteAll(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f : files) {
            deleteAll(f);
            f.delete();
        }
    }

    /**
     * 检查磁盘空间是否大于10mb
     *
     * @return true 大于
     */
    public static boolean isDiskAvailable() {
        long size = getDiskAvailableSize();
        return size > 10 * 1024 * 1024; // > 10bm
    }

    /**
     * 获取磁盘可用空间
     *
     * @return byte 单位 kb
     */
    public static long getDiskAvailableSize() {
        if (!sdcardIsReadyForWrite())
            return 0;
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
        // (availableBlocks * blockSize)/1024 KIB 单位
        // (availableBlocks * blockSize)/1024 /1024 MIB单位
    }

    public static double getFileOrFolderSize(String filePath) {
        return getFileOrFolderSize(filePath, JDataKit.Size_Type.SIZE_TYPE_KB);
    }

    /**
     * Gets the size of the specified unit of the specified file
     *
     * @param filePath file path
     * @param sizeType get size type 1 is byte、2 is KB、3 is MB、4 is GB
     * @return size
     */
    public static double getFileOrFolderSize(String filePath, JDataKit.Size_Type sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getDirSize(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JDataKit.FormatFileSize(blockSize, sizeType);
    }

    /**
     * Calling this method automatically calculates the size of the specified
     * file or the specified folder
     *
     * @param filePath file path
     * @return Calculated with B, KB, MB, GB of the string
     */
    public static String getAutoFileOrFolderSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getDirSize(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JDataKit.FormatFileSize(blockSize);
    }

    /**
     * Gets the specified file size
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeQuietly(fis);
            }
        }
        return size;
    }

    /**
     * 得到文件或目录的大小
     *
     * @param file
     * @return
     */
    public static long getFileOrFolderSize(File file) {
        if (!file.exists())
            return 0;
        if (!file.isDirectory())
            return file.length();

        long length = 0;
        File[] list = file.listFiles();
        // 文件夹被删除时, 子文件正在被写入, 文件属性异常返回null.
        if (list != null) {
            for (File item : list) {
                length += getFileOrFolderSize(item);
            }
        }
        return length;
    }

    /**
     * 将一个InputStream字节流写入到SD卡中
     */
    public static File write2SDFromInput(String dir, String name, InputStream input) {
        File file = null;
        OutputStream output = null;   //创建一个写入字节流对象
        try {
            createFolder(getSDCardPath(), dir);    //根据传入的路径创建目录
            file = createFile(getSDCardPath() + File.separator + dir, name); //根据传入的文件名创建
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];   //每次读取4K
            int num;      //需要根据读取的字节大小写入文件
            while ((num = (input.read(buffer))) != -1) {
                output.write(buffer, 0, num);
            }
            output.flush();  //清空缓存
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(output);
        }
        return file;
    }

    /**
     * 把传入的字符流写入到SD卡中
     *
     * @param dir
     * @param name
     * @param input
     * @return
     */
    public static File write2SDFromWrite(String dir, String name, BufferedReader input) {
        File file = null;
        FileWriter output = null;   //创建一个写入字符流对象
        BufferedWriter bufw = null;
        try {
            createFolder(getSDCardPath(), dir);    //根据传入的路径创建目录
            file = createFile(getSDCardPath() + File.separator + dir, name); //根据传入的文件名创建
            output = new FileWriter(file);
            bufw = new BufferedWriter(output);
            String line;
            while ((line = (input.readLine())) != null) {
                System.out.println("line = " + line);
                bufw.write(line);
                bufw.newLine();
            }
            bufw.flush();  //清空缓存
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(bufw);
        }
        return file;
    }

    public static File createFile(String path, String name) throws IOException {
        File file = new File(path + name);
        file.createNewFile();
        return file;
    }

    public static boolean createFile(String path) throws IOException {
        File file = new File(path);
        return file.exists() ? true : file.createNewFile();
    }

    public static boolean createFile(File file) throws IOException {
        return file.exists() ? true : file.createNewFile();
    }

    /**
     * 判断指定路径文件是否存在
     *
     * @param path
     * @param name
     * @return
     */
    public static boolean isFileExist(String path, String name) {
        File file = new File(path + name);
        return isFileExist(file);
    }

    /**
     * 判断文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExist(File file) {
        return file.exists();
    }

    /**
     * 创建指定路径文件夹
     *
     * @param path
     * @param dir
     * @return
     */
    public static boolean createFolder(String path, String dir) {
        return createFolder(path + File.separator + dir);
    }

    /**
     * 创建指定路径文件夹
     *
     * @param path
     * @return
     */
    public static boolean createFolder(String path) {
        File file = new File(path);
        return createFolder(file);
    }

    /**
     * 创建文件夹
     *
     * @param file
     * @return
     */
    public static boolean createFolder(File file) {
        return file.exists() ? true : file.mkdirs();
    }

    /**
     * Get the specified folder
     *
     * @param dir
     * @return
     * @throws Exception
     */
    private static long getDirSize(File dir) throws Exception {
        if (dir == null || !dir.isDirectory())
            return getFileSize(dir);
        long size = 0;
        File files[] = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                size += getDirSize(file);  //递归调用继续统计
            } else {
                size += getFileSize(file);
            }
        }
        return size;
    }

    /**
     * 复制文件到指定文件
     *
     * @param fromPath 源文件
     * @param toPath   复制到的文件
     * @return true 成功，false 失败
     */
    public static boolean copy(String fromPath, String toPath) {
        boolean result = false;
        File from = new File(fromPath);
        if (!from.exists()) {
            return false;
        }

        File toFile = new File(toPath);
        deleteAll(toFile);
        File toDir = toFile.getParentFile();
        if (createFolder(toDir)) {
            FileInputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(from);
                out = new FileOutputStream(toFile);
                copy(in, out);
                result = true;
            } catch (IOException ex) {
                ex.printStackTrace();
                result = false;
            } finally {
                closeQuietly(in);
                closeQuietly(out);
            }
        }
        return result;
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        if (!(in instanceof BufferedInputStream)) {
            in = new BufferedInputStream(in);
        }
        if (!(out instanceof BufferedOutputStream)) {
            out = new BufferedOutputStream(out);
        }
        int len;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getJsonString(Context context, String path) {
        StringBuffer sb = new StringBuffer();
        AssetManager am = context.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(am.open(path)));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString().trim();
    }
}

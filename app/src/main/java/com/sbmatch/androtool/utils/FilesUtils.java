package com.sbmatch.androtool.utils;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import android.content.Context;
import android.os.Build;

import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class FilesUtils {

    private static final String TAG = "FilesUtils";
    /**
     * 创建文件
     * @param filePath 文件路径
     * @return 创建成功返回true，否则返回false
     */
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return false; // 文件已存在，创建失败
        }
        try {
            return file.createNewFile(); // 创建文件
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Set<String> list(File filePath){
        if (filePath == null) return new HashSet<>();
        return list(filePath.getAbsolutePath());
    }
    public static Set<String> list(String filePath){
        ZipEntry entry;
        Set<String> fileList = new HashSet<>();
        try (ZipInputStream zipInputStream = new ZipInputStream(open(filePath))){

            while ((entry = zipInputStream.getNextEntry()) != null) {
                fileList.add(entry.getName());
            }
        }catch (IOException e){
            Log.e(TAG, e.getMessage() != null ? e.getMessage() : Log.getStackTraceString(e));
        }
        return fileList;
    }

    public static InputStream open(String filePath){
        try {
            return new FileInputStream(filePath);
        }catch (FileNotFoundException e){
            return Build.VERSION.SDK_INT >= 33 ? InputStream.nullInputStream() : null;
        }
    }

    public static File getZipInnerWhitTempFile(String originZipFile, String targetFile){
        return getZipInnerWhitTempFile(open(originZipFile), targetFile, true);
    }

    public static InputStream getAndroidManifestInputStream(String filePath) {
        return getAndroidManifestInputStream(open(filePath));
    }

    public static InputStream getAndroidManifestInputStream(InputStream apkInputStream) {
        return baseGetZipFileWithInputStream(apkInputStream, "AndroidManifest.xml");
    }
    public static InputStream baseGetZipFileWithInputStream(InputStream apkInputStream, String fileName) {
        ZipEntry entry;
        try (ZipInputStream zipInputStream = new ZipInputStream(apkInputStream)){

            while ((entry = zipInputStream.getNextEntry()) != null) {

                if (entry.getName().equals(fileName)) {
                    // 创建一个ByteArrayInputStream来保存数据
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte[] data = new byte[1024];
                    int nRead;
                    while ((nRead = zipInputStream.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    return new ByteArrayInputStream(buffer.toByteArray());
                }
            }
        }catch (IOException e){
            Log.e(TAG, e.getMessage() != null ? e.getMessage() : Log.getStackTraceString(e));
        }
        return null;
    }

    public static File getAndroidManifestFile(String filePath) {
        return getZipInnerWhitTempFile(open(filePath), "AndroidManifest.xml", true);
    }

    public static File getZipInnerWhitTempFile(InputStream apkInputStream, String fileName, boolean deleteOnExit){
        for (String entryName : getZipInnerFiles(apkInputStream).keySet()){
            if (entryName.endsWith("/"+fileName) || entryName.equals(fileName)) {
                try {
                    File tempFile = File.createTempFile("temp_"+fileName.split("\\.")[0], "."+fileName.split("\\.")[1]);
                    if (deleteOnExit) tempFile.deleteOnExit(); // 程序退出时自动删除

                    try (FileOutputStream out = new FileOutputStream(tempFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = getZipInnerFiles(apkInputStream).get(entryName).read(buffer)) > 0) {
                            out.write(buffer, 0, length);
                        }
                    }
                    return tempFile;
                }catch (Throwable e){
                    Log.e("FilesUtils", e.getMessage());
                }
            }
        }
        return null;
    }

    public static Map<String, ZipInputStream> getZipInnerFiles(InputStream apkInputStream) {
        ZipEntry entry;
        Map<String, ZipInputStream> zipEntryList = new HashMap<>();
        try (ZipInputStream zipInputStream = new ZipInputStream(apkInputStream)) {
            while ((entry = zipInputStream.getNextEntry()) != null) {
               zipEntryList.put(entry.getName(), zipInputStream);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage() != null ? e.getMessage() : Log.getStackTraceString(e));
        }
        return zipEntryList;
    }

    /**
     * 获取外部存储目录下指定文件的绝对路径
     *
     * @param fileName 文件名（包括扩展名）
     * @return 文件的绝对路径，如果外部存储不可用则返回 null
     */
    public static String getExternalFilePath(String fileName) {
        // 获取外部存储目录
        File externalDir = android.app.AppGlobals.getInitialApplication().getExternalFilesDir(null);

        // 检查外部存储状态
        if (externalDir == null) return null;

        // 构建文件路径并返回绝对路径
        return new File(externalDir, fileName).getAbsolutePath();
    }


    /**
     * 转换为File对象
     * @param path 输入路径（支持File对象或String类型）
     * @return 对应的File对象，转换失败返回null
     */
    private static File toFile(Object path) {
        if (path instanceof File) {
            return (File) path;
        }
        if (path instanceof String) {
            return new File((String) path);
        }
        return null;
    }


    /**
     * 复制raw资源到目标位置
     *
     * @param resId 资源ID
     * @param dest 目标位置（File对象或路径字符串）
     * @return 是否成功
     */

    public static boolean copyRawResource(int resId, File dest, String fileName) {

        try (InputStream is = android.app.AppGlobals.getInitialApplication().getResources().openRawResource(resId)) {
            return copyFromStream(is, dest, fileName);
        } catch (Exception e) {
            System.err.println("复制raw资源失败: " + resId);
            return false;
        }
    }

    /**
     * 复制文件到目标位置
     * @param src 源文件（File对象或路径字符串）
     * @param dest 目标位置（File对象或路径字符串，如果是目录则自动使用原文件名）
     * @return 是否成功
     */
    public static boolean copyFile(Object src, Object dest) {
        File srcFile = toFile(src);
        File destFile = toFile(dest);

        // 参数检查
        if (srcFile == null || destFile == null ||
                !srcFile.exists() || !srcFile.isFile()) {
            return false;
        }

        // 处理目标为目录的情况
        if (destFile.isDirectory()) {
            destFile = new File(destFile, srcFile.getName());
        }

        // 创建父目录
        File parent = destFile.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            System.err.println("无法创建目录: " + parent.getAbsolutePath());
            return false;
        }

        // 执行复制
        try {
            Files.copy(srcFile.toPath(), destFile.toPath(), REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.err.println("复制文件失败: " + srcFile + " -> " + destFile);
            return false;
        }
    }


    /**
     * 将输入流复制到指定目录下的文件中
     * @param inputStream 输入流(调用方负责关闭)
     * @param targetDir 目标目录
     * @param fileName 文件名
     * @return 是否复制成功
     */

    public static boolean copyFromStream(InputStream inputStream, File targetDir, String fileName) {
        // 参数校验
        if (inputStream == null || targetDir == null || fileName == null || fileName.isEmpty()) {
            System.err.println("无效参数: " +
                    (inputStream == null ? "输入流为空" : "") +
                    (targetDir == null ? "目标目录为空" : "") +
                    (fileName == null || fileName.isEmpty() ? "文件名为空" : ""));
            return false;
        }

        try {
            // 创建目录(如果不存在)
            if (!Files.exists(targetDir.toPath())) {
                try {
                    Files.createDirectories(targetDir.toPath());
                } catch (IOException e) {
                    System.err.printf("无法创建目录 %s: %s%n", targetDir, e.getMessage());
                    return false;
                }
            }

            // 解析目标文件路径
            Path targetFile = targetDir.toPath().resolve(fileName).normalize();

            // 确保目标在指定目录内
            if (!targetFile.startsWith(targetDir.toPath().normalize())) {
                System.err.println("安全违规: 尝试写入目录外位置: " + targetFile);
                return false;
            }

            // 执行复制(使用NIO API)
            Files.copy(inputStream, targetFile, REPLACE_EXISTING);
            return true;

        } catch (IOException e) {
            System.err.printf("复制文件到 %s/%s 失败: %s%n",
                    targetDir, fileName, e.getMessage());
            return false;
        }
    }

    /**
     * 重载方法，支持字符串路径参数
     */
    public static boolean copyFromStream(InputStream inputStream, String targetDir, String fileName) {
        if (targetDir == null) {
            return false;
        }
        return copyFromStream(inputStream, new File(targetDir), fileName);
    }

    /**
     * 创建文件夹
     * @param dirPath 文件夹路径
     * @return 创建成功返回true，否则返回false
     */
    public static boolean createDir(String dirPath) {
        File dir = new File(dirPath);
        if (dir.exists()) {
            return false; // 文件夹已存在，创建失败
        }
        return dir.mkdirs(); // 创建文件夹
    }

    /**
     * 删除文件或文件夹
     * @param path 文件或文件夹路径
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String path) {
        File file = new File(path);
        return delete(file, false);
    }

    public static boolean delete(File file, boolean onlyChildFile) {
        if (file == null || !file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    delete(f, false);
                }
            }
        }

        if (file.isDirectory() && onlyChildFile) {
            return true;
        }

        return file.delete();
    }

    /**
     * 移动文件或文件夹
     * @param srcPath 源文件或文件夹路径
     * @param destPath 目标文件或文件夹路径
     * @return 移动成功返回true，否则返回false
     */
    public static boolean move(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (!srcFile.exists()) {
            return false; // 源文件或文件夹不存在，移动失败
        }

       return move(srcFile, destFile);

    }

    public static boolean move(File srcPath, String destPath){
        return move(srcPath, new File(destPath));
    }

    public static boolean move(File srcFile, File destFile){
        try {
            Files.move(srcFile.toPath(), destFile.toPath(), REPLACE_EXISTING); // 移动文件或文件夹
            return true;
        }catch (IOException e){
            return false;
        }
    }

    /**
     * 判断文件是否存在
     * @param filePath 文件路径
     * @return 存在返回true，否则返回false
     */
    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /**
     * 判断文件夹是否存在
     * @param dirPath 文件夹路径
     * @return 存在返回true，否则返回false
     */
    public static boolean isDirExists(String dirPath) {
        File dir = new File(dirPath);
        return dir.exists() && dir.isDirectory();
    }

    /**
     * 向文件写入内容
     * @param filePath 文件路径
     * @param content 写入的内容
     * @return 写入成功返回true，否则返回false
     */
    public static boolean writeToFile(String filePath, String content, boolean isAppend) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, isAppend))) {
            writer.write(content);
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从文件中读取内容
     * @param filePath 文件路径
     * @return 读取到的内容，如果读取失败返回null
     */
    public static String readFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null; // 文件不存在或不是文件，读取失败
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * 读取指定文件的第一行内容
     * @param filePath 文件路径
     * @return 文件的第一行内容，如果读取失败则返回null
     */

    public static String readFirstLine(String filePath) {
        File file = new File(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }


    public static boolean copyFileFromAssets(Context appCtx, String srcPath, String dstPath) {
        if (srcPath.isEmpty() || dstPath.isEmpty()) {
            return false;
        }
        try (InputStream is = new BufferedInputStream(appCtx.getAssets().open(srcPath));
             OutputStream os = new BufferedOutputStream(new FileOutputStream(dstPath))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean copyFileFromAssets(Context appCtx, String srcPath, String dstPath, boolean setReadOnly){
        boolean r = copyFileFromAssets(appCtx, srcPath, dstPath);
        if (setReadOnly){
            File dstFile = new File(dstPath);
            return dstFile.setReadOnly();  // 设置为只读
        }
        return r;
    }


    public static void copyDirectoryFromAssetsASync(Context appCtx, String srcDir, String dstDir, IRemoteCallback callback) {
        if (srcDir.isEmpty() || dstDir.isEmpty()) {
            return;
        }

        Bundle bundle = new Bundle();

        ExecutorUtils.submit(() -> {
            copyDirectoryFromAssetsSync(appCtx, srcDir, dstDir);
            bundle.putBoolean("status", true);
            try {
                callback.sendResult(bundle);
            } catch (RemoteException e) {
                LogUtils.e(e.getMessage());
            }
        });
    }


    public static void copyDirectoryFromAssetsSync(Context appCtx, String assetPath, String destDir) {
        copyDirectoryFromAssetsSync(appCtx, assetPath, destDir, true);
    }

    public static void copyDirectoryFromAssetsSync(Context appCtx, String assetPath, String destDir, boolean skipExisting) {
        try (InputStream is = appCtx.getAssets().open(assetPath);
             BufferedInputStream bis = new BufferedInputStream(is);
             ZipInputStream zis = new ZipInputStream(bis)) {
            byte[] buf = new byte[8192];
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File out = new File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    if (!skipExisting || !out.exists()) out.mkdirs();
                } else {
                    if (skipExisting && out.exists()) continue;
                    out.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(out);
                         BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                        int n;
                        while ((n = zis.read(buf)) != -1) bos.write(buf, 0, n);
                    }
                }
            }
        }catch (Throwable e){
            LogUtils.e(Log.getStackTraceString(e));
        }
    }

    private static void copyZipEntryToFile(ZipFile apkZip, ZipEntry entry, File dstFile) throws IOException {
        File parent = dstFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (InputStream is = apkZip.getInputStream(entry);
             OutputStream os = new BufferedOutputStream(new FileOutputStream(dstFile))) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }
        }
    }

    private static void extractZipFromEntry(ZipFile apkZip, ZipEntry zipEntry, String dstDir) throws IOException {
        File dstDirFile = new File(dstDir);
        if (!dstDirFile.exists()) {
            dstDirFile.mkdirs();
        }
        try (ZipInputStream zis = new ZipInputStream(apkZip.getInputStream(zipEntry))) {
            ZipEntry inner;
            while ((inner = zis.getNextEntry()) != null) {
                File dstFile = new File(dstDir, inner.getName());
                if (inner.isDirectory()) {
                    dstFile.mkdirs();
                } else {
                    File parent = dstFile.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    try (OutputStream os = new BufferedOutputStream(new FileOutputStream(dstFile))) {
                        byte[] buffer = new byte[8192];
                        int length;
                        while ((length = zis.read(buffer)) != -1) {
                            os.write(buffer, 0, length);
                        }
                    }
                }
            }
        }
    }

    /**
     * 递归查找目录下指定后缀的文件
     * @param dirPath 目录路径
     * @param suffix  文件后缀(如 ".jsc" 或 "jsc")
     * @return 匹配的文件列表
     */
    public static List<File> getFilesBySuffix(String dirPath, String suffix) {
        List<File> result = new ArrayList<>();
        if (dirPath == null || suffix == null) return result;
        String ext = suffix.startsWith(".") ? suffix : "." + suffix;
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) return result;
        File[] files = dir.listFiles();
        if (files == null) return result;
        for (File file : files) {
            if (file.isDirectory()) {
                result.addAll(getFilesBySuffix(file.getAbsolutePath(), ext));
            } else if (file.getName().toLowerCase().endsWith(ext.toLowerCase())) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * 获取目录下所有文件（按最后修改时间降序排列）
     * @param directoryPath 目录路径
     * @return 排序后的文件列表（可能为空列表）
     */
    public static List<File> getSortedFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        // 检查目录是否存在
        if (!directory.exists() || !directory.isDirectory()) {
            return Collections.emptyList();
        }

        // 获取目录中的所有文件（过滤掉子目录）
        File[] files = directory.listFiles(File::isFile);

        // 如果目录为空，返回空列表
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }

        // 按最后修改时间降序排序
        Arrays.sort(files, (file1, file2) -> Long.compare(file2.lastModified(), file1.lastModified()));

        // 转换为List返回
        return Arrays.asList(files);
    }

    /**
     * 获取目录下最新的单个文件（原方法保留）
     */
    public static File getLatestFileInDirectory(String directoryPath) {
        List<File> files = getSortedFilesInDirectory(directoryPath);
        return files.isEmpty() ? null : files.get(0);
    }

    /**
     * 获取目录下符合前缀条件的最新文件（复用排序逻辑）
     * @param directoryPath 目录路径
     * @param prefix 文件名前缀
     * @return 最新的文件（无匹配时返回null）
     */
    public static File getLatestFileInDirectoryWithPrefix(String directoryPath, String prefix) {
        // 1. 获取目录下所有已排序的文件
        List<File> files = getSortedFilesInDirectory(directoryPath);

        // 2. 过滤出符合前缀的文件
        List<File> matchedFiles = files.stream()
                .filter(file -> file.getName().startsWith(prefix))
                .toList();

        // 3. 返回第一个（即最新的）文件
        return matchedFiles.isEmpty() ? null : matchedFiles.get(0);
    }

}

package com.sbmatch.androtool.utils;

import android.os.Build;
import android.util.Log;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

// import rikka.shizuku.Shizuku;
// import rikka.shizuku.ShizukuRemoteProcess;

/**
 * ShellUtils
 *
 * @author <a href="https://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
 */
public class ShellUtils {

    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
    //static TerminalSession session;
    static final String TAG = "ShellUtils";

    private ShellUtils() {
        throw new AssertionError();
    }

    /**
     * check whether has root permission
     */
    public static boolean checkRootPermission() {
        return execCommand("echo root", true, 5000, false).result == 0;
    }

    public static CommandResult execCommand(String command) {
        return execCommand(new String[]{command}, false, -1, true);
    }

    public static CommandResult execCommand(String command, long timeoutInMills) {
        return execCommand(new String[]{command}, false, timeoutInMills, true);
    }

    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[]{command}, isRoot, -1, true);
    }

    /**
     * execute shell command, default return result msg
     *
     * @param command        command
     * @param isRoot         whether need to run with root
     * @param timeoutInMills timeout in millSeconds, -1 means no timeout, if bigger than 0, you must make sure Android Version is not smaller than Android O
     * @return
     * @see ShellUtils#execCommand(String[], boolean, long, boolean)
     */
    public static CommandResult execCommand(String command, boolean isRoot, long timeoutInMills) {
        return execCommand(new String[]{command}, isRoot, timeoutInMills, true);
    }

    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands == null ? null : commands.toArray(new String[]{}), isRoot, -1, true);
    }

    /**
     * execute shell commands, default return result msg
     *
     * @param commands       command list
     * @param isRoot         whether need to run with root
     * @param timeoutInMills timeout in millSeconds, -1 means no timeout, if bigger than 0, you must make sure Android Version is not smaller than Android O
     * @return
     * @see ShellUtils#execCommand(String[], boolean, long, boolean)
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot, long timeoutInMills) {
        return execCommand(commands == null ? null : commands.toArray(new String[]{}), isRoot, timeoutInMills, true);
    }

    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, -1, true);
    }

    /**
     * execute shell commands, default return result msg
     *
     * @param commands       command array
     * @param isRoot         whether need to run with root
     * @param timeoutInMills timeout in millSeconds, -1 means no timeout, if bigger than 0, you must make sure Android Version is not smaller than Android O
     * @return
     * @see ShellUtils#execCommand(String[], boolean, long, boolean)
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot, long timeoutInMills) {
        return execCommand(commands, isRoot, timeoutInMills, true);
    }

    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, -1, isNeedResultMsg);
    }

    /**
     * execute shell command
     *
     * @param command         command
     * @param isRoot          whether need to run with root
     * @param timeoutInMills  timeout in millSeconds, -1 means no timeout, if bigger than 0, you must make sure Android Version is not smaller than Android O
     * @param isNeedResultMsg whether need result msg
     * @return
     * @see ShellUtils#execCommand(String[], boolean, long, boolean)
     */
    public static CommandResult execCommand(String command, boolean isRoot, long timeoutInMills, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, timeoutInMills, isNeedResultMsg);
    }

    /**
     * execute shell commands
     *
     * @param commands        command list
     * @param isRoot          whether need to run with root
     * @param timeoutInMills  timeout in millSeconds, -1 means no timeout, if bigger than 0, you must make sure Android Version is not smaller than Android O
     * @param isNeedResultMsg whether need result msg
     * @return
     * @see ShellUtils#execCommand(String[], boolean, long, boolean)
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot, long timeoutInMills, boolean isNeedResultMsg) {
        return execCommand(commands == null ? null : commands.toArray(new String[]{}), isRoot, timeoutInMills, isNeedResultMsg);
    }

    /**
     * execute shell commands
     *
     * @param commands        command array
     * @param isRoot          whether need to run with root
     * @param timeoutInMills  timeout in millSeconds, -1 means no timeout, if bigger than 0, you must make sure Android Version is not smaller than Android O
     * @param isNeedResultMsg whether need result msg
     * @return <ul>
     * <li>if isNeedResultMsg is false, {@link CommandResult#successMsg} is null and
     * {@link CommandResult#errorMsg} is null.</li>
     * <li>if {@link CommandResult#result} is -1, there maybe some excepiton.</li>
     * </ul>
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot, long timeoutInMills, boolean isNeedResultMsg) {
        try {
            Process process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            return baseExecCommandWithProcess(process, commands, timeoutInMills, isNeedResultMsg);
        } catch (Exception e) {
            return new CommandResult(-1, null, e.getMessage());
        }
    }


    public static CommandResult baseExecCommandWithProcess(Process process, String[] commands, long timeoutInMills, boolean isNeedResultMsg) {
        int result = -1;
        if (process == null) return new CommandResult(result, null, "Process is null");
        if (commands == null || commands.length == 0) return new CommandResult(result, null, null);

        StringBuilder successMsg = isNeedResultMsg ? new StringBuilder() : null;
        StringBuilder errorMsg = isNeedResultMsg ? new StringBuilder() : null;

        try (DataOutputStream os = new DataOutputStream(process.getOutputStream());
             BufferedReader successReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {

            for (String command : commands) {
                if (command == null) continue;
                os.write(command.getBytes(StandardCharsets.UTF_8));
                os.writeBytes(COMMAND_LINE_END);
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();
            os.close(); // 写入完成后立即关闭

            Thread outThread = null;
            Thread errThread = null;
            if (isNeedResultMsg) {
                outThread = new Thread(() -> readStream(successReader, successMsg));
                errThread = new Thread(() -> readStream(errorReader, errorMsg));
                outThread.start();
                errThread.start();
            }


            if (timeoutInMills > 0) {
                boolean finished = process.waitFor(timeoutInMills, TimeUnit.MILLISECONDS);
                if (!finished) {
                    process.destroy(); // 强制结束
                    return new CommandResult(-1, null, "Execute command timeout");
                }
            }
            result = process.waitFor();

            // 等待读取线程完成
            if (outThread != null) outThread.join(500);
            if (errThread != null) errThread.join(500);

        } catch (Exception e) {
            LogUtils.e(Log.getStackTraceString(e));
            if (errorMsg != null) errorMsg.append(e.getMessage());
        } finally {
            process.destroy();
        }

        return new CommandResult(
                result,
                successMsg == null ? null : successMsg.toString(),
                errorMsg == null ? null : errorMsg.toString()
        );
    }

    // 辅助读取方法
    private static void readStream(BufferedReader reader, StringBuilder sb) {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(COMMAND_LINE_END);
            }
        } catch (IOException e) {
            LogUtils.e(e.getMessage());
        }
    }

    /*
    public static void execCommand(String command, TermuxTerminalSessionClientBase clientBase){

        String filesDirPath = ContextUtil.getApplicationContext().getFilesDir().getAbsolutePath();

        String binDir = Build.VERSION.SDK_INT >= 28 ? "/system/bin" : filesDirPath+"/bin";
        String homeDir = filesDirPath + "/home";

        File homeFile = new File(homeDir);
        if (!homeFile.exists()) {
            homeFile.mkdirs();
        }

        if (session == null) {

            session = new TerminalSession(
                    binDir+"/sh",
                    homeDir,            // 工作目录
                    new String[]{},     // 启动参数
                    new String[]{
                            "TERM=xterm-256color",
                            "HOME=" + homeDir,
                            "PATH=" + filesDirPath+"/bin" + ":/system/bin:/system/xbin"
                    }, // 环境变量
                    null,
                    clientBase
            );
        }

        session.updateSize(80, 24, 10, 20);

        String cmd = command+"\n";

        session.write(cmd.getBytes(StandardCharsets.UTF_8), 0, cmd.getBytes(StandardCharsets.UTF_8).length);

    }

    */
    // public static CommandResult execCommandWithShizuku(String[] commands, long timeoutInMills, boolean isNeedResultMsg){
        // if (!UserServiceFactory.getFactory(UserServiceFactory.FactoryMode.Shizuku_Mode).checkSelfPermission()){
            // return new CommandResult(0);
        // }
        // ShizukuRemoteProcess remoteProcess = ReflectUtils.callStaticObjectMethod(Shizuku.class, "newProcess", new Class<?>[] {
                // String[].class,
                // String[].class,
                // String.class
        // },commands, new String[]{}, null);
        // return baseExecCommandWithProcess(remoteProcess, commands, timeoutInMills, isNeedResultMsg);
    // }

    // public static CommandResult execCommandWithShizuku(String[] commands) {
        // return execCommandWithShizuku(commands, -1, true);
    // }


    /**
     * result of command
     * <ul>
     * <li>{@link CommandResult#result} means result of command, 0 means normal, else means error, same to excute in
     * linux shell</li>
     * <li>{@link CommandResult#successMsg} means success message of command result</li>
     * <li>{@link CommandResult#errorMsg} means error message of command result</li>
     * </ul>
     *
     * @author <a href="https://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
     */
    public static class CommandResult {

        /**
         * result of command
         **/
        public int result;
        /**
         * success message of command result
         **/
        public String successMsg;
        /**
         * error message of command result
         **/
        public String errorMsg;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }

        @Override
        public String toString() {
            return "result is: " + result +
                    "\nsuccessMsg is: '" + successMsg + "'" +
                    "\nerrorMsg is: '" + errorMsg + "'";
        }
    }
}

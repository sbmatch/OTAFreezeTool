package miui.process;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;

import java.util.List;

public class ProcessConfig implements Parcelable {

    private static final int INVALID_ADJ = -10000;
    private static final int INVALID_TASK_ID = -1;
    private static final int INVALID_UID = -1;
    private static final int INVALID_USER_ID = -10000;
    public static final int KILL_LEVEL_FORCE_STOP = 104;
    public static final int KILL_LEVEL_KILL = 103;
    public static final int KILL_LEVEL_KILL_BACKGROUND = 102;
    public static final int KILL_LEVEL_TRIM_MEMORY = 101;
    public static final int KILL_LEVEL_UNKNOWN = 100;
    public static final int POLICY_AUTO_IDLE_KILL = 13;
    public static final int POLICY_AUTO_LOCK_OFF_CLEAN = 15;
    public static final int POLICY_AUTO_LOCK_OFF_CLEAN_BY_PRIORITY = 17;
    public static final int POLICY_AUTO_POWER_KILL = 11;
    public static final int POLICY_AUTO_SLEEP_CLEAN = 14;
    public static final int POLICY_AUTO_SYSTEM_ABNORMAL_CLEAN = 16;
    public static final int POLICY_AUTO_THERMAL_KILL = 12;
    public static final int POLICY_AUTO_THERMAL_KILL_ALL_LEVEL_1 = 19;
    public static final int POLICY_AUTO_THERMAL_KILL_ALL_LEVEL_2 = 20;
    public static final int POLICY_DISPLAY_SIZE_CHANGED_KILL = 18;
    public static final int POLICY_FORCE_CLEAN = 2;
    public static final int POLICY_GAME_CLEAN = 4;
    public static final int POLICY_GARBAGE_CLEAN = 6;
    public static final int POLICY_LOCK_SCREEN_CLEAN = 3;
    public static final int POLICY_ONE_KEY_CLEAN = 1;
    public static final int POLICY_OPTIMIZATION_CLEAN = 5;
    public static final int POLICY_SCREEN_OFF_CPU_CHECK_KILL = 22;
    public static final int POLICY_SWIPE_UP_CLEAN = 7;
    public static final int POLICY_SYSTEM_CLEAN = 21;
    public static final int POLICY_USER_DEFINED = 10;
    private String mKillingPackage;
    private ArrayMap<Integer, List<String>> mKillingPackageMaps;
    private int mPolicy;
    private int mPriority;
    private String mReason;
    private boolean mRemoveTaskNeeded;
    private List<Integer> mRemovingTaskIdList;
    private int mTaskId;
    private int mUid;
    private int mUserId;
    private List<String> mWhiteList;
    private List<Integer> mWhiteListTaskId;
    private long mkillingClockTime;


    public ProcessConfig(int policy) {
        this.mUserId = -10000;
        this.mTaskId = -1;
        this.mUid = -1;
        this.mPriority = -10000;
        this.mPolicy = policy;
    }

    public ProcessConfig(int policy, String killingPackage, int uid) {
        this(policy);
        this.mKillingPackage = killingPackage;
        this.mUid = uid;
    }

    public ProcessConfig(int policy, String killingPackage, int userId, int taskId) {
        this(policy);
        this.mKillingPackage = killingPackage;
        this.mUserId = userId;
        this.mTaskId = taskId;
    }

    public ProcessConfig(int policy, int userId, ArrayMap<Integer, List<String>> killingPackageMaps) {
        this(policy);
        this.mUserId = userId;
        this.mKillingPackageMaps = killingPackageMaps;
    }

    public ProcessConfig(int policy, int userId, ArrayMap<Integer, List<String>> killingPackageMaps, String reason) {
        this(policy, userId, killingPackageMaps);
        this.mReason = reason;
    }

    public static final Creator<ProcessConfig> CREATOR = new Creator<ProcessConfig>() {
        @Override
        public ProcessConfig createFromParcel(Parcel in) {
            return new ProcessConfig(in);
        }

        @Override
        public ProcessConfig[] newArray(int size) {
            return new ProcessConfig[size];
        }
    };

    public int getPolicy() {
        return this.mPolicy;
    }

    public List<String> getWhiteList() {
        return this.mWhiteList;
    }

    public List<Integer> getWhiteListTaskId() {
        return this.mWhiteListTaskId;
    }

    public String getKillingPackage() {
        return this.mKillingPackage;
    }

    public String getReason() {
        return this.mReason;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public int getUid() {
        return this.mUid;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public boolean isRemoveTaskNeeded() {
        return this.mRemoveTaskNeeded;
    }

    public void setRemovingTaskIdList(List<Integer> taskIdList) {
        this.mRemovingTaskIdList = taskIdList;
    }

    public List<Integer> getRemovingTaskIdList() {
        return this.mRemovingTaskIdList;
    }

    public ArrayMap<Integer, List<String>> getKillingPackageMaps() {
        return this.mKillingPackageMaps;
    }


    public void setWhiteList(List<String> whiteList) {
        this.mWhiteList = whiteList;
    }

    public void setWhiteListTaskId(List<Integer> whiteListTaskId) {
        this.mWhiteListTaskId = whiteListTaskId;
    }

    public void setKillingPackage(String killingPackage) {
        this.mKillingPackage = killingPackage;
    }

    public void setKillingPackageMaps(ArrayMap<Integer, List<String>> killingPackageMaps) {
        this.mKillingPackageMaps = killingPackageMaps;
    }

    public void setReason(String reason) {
        this.mReason = reason;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public void setUid(int uid) {
        this.mUid = uid;
    }

    public void setTaskId(int taskId) {
        this.mTaskId = taskId;
    }

    public void setPriority(int priority) {
        this.mPriority = priority;
    }

    public void setRemoveTaskNeeded(boolean removeTaskNeeded) {
        this.mRemoveTaskNeeded = removeTaskNeeded;
    }

    public void setKillingClockTime(long killingClockTime) {
        this.mkillingClockTime = killingClockTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        throw new RuntimeException("Stub!");
    }

    public void readFromParcel(Parcel source) {
        throw new RuntimeException("Stub!");
    }

    private ProcessConfig(Parcel source) {
        throw new RuntimeException("Stub!");
    }

    public boolean isUserIdInvalid() {
        return this.mUserId == -10000;
    }

    public boolean isTaskIdInvalid() {
        return this.mTaskId == -1;
    }

    public boolean isUidInvalid() {
        return this.mUid <= -1;
    }

    public boolean isPriorityInvalid() {
        return this.mPriority == -10000;
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }
}

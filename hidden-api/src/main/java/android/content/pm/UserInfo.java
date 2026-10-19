/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Per-user information.
 *
 * <p>There are 3 base properties of users: {@link #FLAG_SYSTEM}, {@link #FLAG_FULL}, and
 * {@link #FLAG_PROFILE}. Every user must have one of the following combination of these
 * flags:
 * <ul>
 *    <li>FLAG_SYSTEM (user {@link UserHandle#USER_SYSTEM} on a headless-user-0 device)</li>
 *    <li>FLAG_SYSTEM and FLAG_FULL (user {@link UserHandle#USER_SYSTEM} on a regular device)</li>
 *    <li>FLAG_FULL (non-profile secondary user)</li>
 *    <li>FLAG_PROFILE (profile users)</li>
 * </ul>
 * Users can have also have additional flags (such as FLAG_GUEST) as appropriate.
 *
 * @hide
 */

public class UserInfo implements Parcelable {

    /**
     * *************************** NOTE ***************************
     * These flag values CAN NOT CHANGE because they are written
     * directly to storage.
     */

    /**
     * Primary user. In practice, this is just synonymous with {@link #FLAG_SYSTEM}.
     *
     * <p>On many devices, this will also be the first human user.
     * However, in {@link UserManager#isHeadlessSystemUserMode() headless system user mode}, this
     * should be regarded as unsupported since the system user may not be a human.
     *
     * @deprecated For checking for user 0, use {@link #FLAG_SYSTEM}.
     *             For checking for the designated "main human user", use {@link #FLAG_MAIN}.
     */

    @Deprecated
    public static final int FLAG_PRIMARY = 0x00000001;

    /**
     * User with administrative privileges. Such a user can create and
     * delete users.
     */
    public static final int FLAG_ADMIN   = 0x00000002;

    /**
     * Indicates a guest user that may be transient.
     * @deprecated Use {@link UserManager#USER_TYPE_FULL_GUEST} instead.
     */
    @Deprecated
    public static final int FLAG_GUEST   = 0x00000004;

    /**
     * Indicates the user has restrictions in privileges, in addition to those for normal users.
     * Exact meaning TBD. For instance, maybe they can't install apps or administer WiFi access pts.
     * @deprecated Use {@link UserManager#USER_TYPE_FULL_RESTRICTED} instead.
     */
    @Deprecated
    public static final int FLAG_RESTRICTED = 0x00000008;

    /**
     * Indicates that this user has gone through its first-time initialization.
     */
    public static final int FLAG_INITIALIZED = 0x00000010;

    /**
     * Indicates that this user is a profile of another user, for example holding a users
     * corporate data.
     * @deprecated Use {@link UserManager#USER_TYPE_PROFILE_MANAGED} instead.
     */
    @Deprecated
    public static final int FLAG_MANAGED_PROFILE = 0x00000020;

    /**
     * Indicates that this user is disabled.
     *
     * <p> This is currently used to indicate that a Managed Profile, when created via
     * DevicePolicyManager, has not yet been provisioned; once the DPC provisions it, a DPM call
     * will manually set it to enabled.
     *
     * <p>Users that are slated for deletion are also generally set to disabled.
     *
     * <p>Note: If an ephemeral user is disabled, it shouldn't be later re-enabled. Ephemeral users
     * are disabled as their removal is in progress to indicate that they shouldn't be re-entered.
     */
    public static final int FLAG_DISABLED = 0x00000040;

    public static final int FLAG_QUIET_MODE = 0x00000080;

    /**
     * Indicates that this user is ephemeral. I.e. the user will be removed after leaving
     * the foreground.
     */
    public static final int FLAG_EPHEMERAL = 0x00000100;

    /**
     * User is for demo purposes only and can be removed at any time.
     * @deprecated Use {@link UserManager#USER_TYPE_FULL_DEMO} instead.
     */
    @Deprecated
    public static final int FLAG_DEMO = 0x00000200;

    /**
     * Indicates that this user is a non-profile human user.
     *
     * <p>When creating a new (non-system) user, this flag will always be forced true unless the
     * user is a {@link #FLAG_PROFILE}. If user {@link UserHandle#USER_SYSTEM} is also a
     * human user, it must also be flagged as FULL.
     */
    public static final int FLAG_FULL = 0x00000400;

    /**
     * Indicates that this user is {@link UserHandle#USER_SYSTEM}. Not applicable to created users.
     */
    public static final int FLAG_SYSTEM = 0x00000800;

    /**
     * Indicates that this user is a profile human user, such as a managed profile.
     * Mutually exclusive with {@link #FLAG_FULL}.
     */
    public static final int FLAG_PROFILE = 0x00001000;

    /**
     * Indicates that this user is created in ephemeral mode via
     * {@link IUserManager} create user.
     *
     * When a user is created with {@link #FLAG_EPHEMERAL}, {@link #FLAG_EPHEMERAL_ON_CREATE}
     * is set internally within the user manager.
     *
     * When {@link #FLAG_EPHEMERAL_ON_CREATE} is set {@link IUserManager.setUserEphemeral}
     * has no effect because a user that was created ephemeral can never be made non-ephemeral.
     *
     * {@link #FLAG_EPHEMERAL_ON_CREATE} should NOT be set by client's of user manager
     *
     * @hide
     */
    public static final int FLAG_EPHEMERAL_ON_CREATE = 0x00002000;

    /**
     * Indicates that this user is the designated main user on the device. This user may have access
     * to certain features which are limited to at most one user.
     *
     * <p>Currently, this will be the first user to go through setup on the device, but in future
     * releases this status may be transferable or may even not be given to any users.
     *
     * <p>This is not necessarily the system user. For example, it will not be the system user on
     * devices for which {@link UserManager#isHeadlessSystemUserMode()} returns true.
     *
     * <p>NB: Features should ideally not limit functionality to the main user. Ideally, they
     * should either work for all users or for all admin users. If a feature should only work for
     * select users, its determination of which user should be done intelligently or be
     * customizable. Not all devices support a main user, and the idea of singling out one user as
     * special is contrary to overall multiuser goals.
     */
    public static final int FLAG_MAIN = 0x00004000;

    /**
     * Indicates that this user was created for the purposes of testing.
     *
     * <p>These users are subject to removal during tests and should not be used on actual devices
     * used by humans.
     *
     * @hide
     */
    public static final int FLAG_FOR_TESTING = 0x00008000;

    /**
     * @hide
     */

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserInfoFlag {
    }

    public static int NO_PROFILE_GROUP_ID ;

    public int id;

    public int serialNumber;

    public String name;

    public String iconPath;

    public @UserInfoFlag int flags;

    public long creationTime;

    public long lastLoggedInTime;
    public String lastLoggedInFingerprint;

    /**
     * Type of user, such as {@link UserManager#USER_TYPE_PROFILE_MANAGED}, corresponding to
     * {@link com.android.server.pm.UserTypeDetails#getName()}.
     */
    public String userType;

    /**
     * If this user is a parent user, it would be its own user id.
     * If this user is a child user, it would be its parent user id.
     * Otherwise, it would be {@link #NO_PROFILE_GROUP_ID}.
     */
    public int profileGroupId;
    public int restrictedProfileParentId;

    /**
     * Index for distinguishing different profiles with the same parent and user type for the
     * purpose of badging.
     * It is used for determining which badge color/label to use (if applicable) from
     * the options available for a particular user type.
     */
    public int profileBadge;

    /** User is only partially created. */
    public boolean partial;
    public boolean guestToRemove;

    /**
     * This is used to optimize the creation of a user, i.e. OEMs might choose to pre-create a
     * number of users at the first boot, so the actual creation later is faster.
     *
     * <p>A {@code preCreated} user is not a real user yet, so it should not show up on regular
     * user operations (other than user creation per se).
     *
     * <p>Once the pre-created is used to create a "real" user later on, {@code preCreated} is set
     * to {@code false}.
     *
     * <p><b>NOTE: Pre-created users are deprecated. This field remains to be able to recognize
     * pre-created users in older versions, but will eventually be removed.
     */
    public boolean preCreated;

    /**
     * When {@code true}, it indicates this user was created by converting a {@link #preCreated}
     * user.
     *
     * <p><b>NOTE: </b>only used for debugging purposes, it's not set when marshalled to a parcel.
     *
     * <p><b>NOTE: Pre-created users are deprecated. This field remains to be able to recognize
     * pre-created users in older versions, but will eventually ve removed.
     */
    public boolean convertedFromPreCreated;

    /**
     * Creates a UserInfo whose user type is determined automatically by the flags according to
     * {@link #getDefaultUserType}; can only be used for user types handled there.
     */

    public UserInfo(int id, String name, int flags) {
        this(id, name, null, flags);
    }

    /**
     * Creates a UserInfo whose user type is determined automatically by the flags according to
     * {@link #getDefaultUserType}; can only be used for user types handled there.
     */

    public UserInfo(int id, String name, String iconPath, int flags) {
        this(id, name, iconPath, flags, getDefaultUserType(flags));
    }

    public UserInfo(int id, String name, String iconPath, int flags, String userType) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get the user type (such as {@link UserManager#USER_TYPE_PROFILE_MANAGED}) that corresponds to
     * the given {@link UserInfoFlag}s.

     * <p>The userInfoFlag can contain GUEST, RESTRICTED, MANAGED_PROFILE, DEMO, or else be
     * interpreted as a regular "secondary" user. It cannot contain more than one of these.
     * It can contain other UserInfoFlag properties (like EPHEMERAL), which will be ignored here.
     *
     * @throws IllegalArgumentException if userInfoFlag is more than one type of user or if it
     *                                  is a SYSTEM user.
     *
     * @hide
     */
    public static String getDefaultUserType(@UserInfoFlag int userInfoFlag) {
        throw new RuntimeException("Stub!");
    }

    /**
     * For checking for user 0, compare {@link #id} to {@link UserHandle#USER_SYSTEM}.
     *             For checking for the designated "main human user", use {@link #isMain()}.
     */

    public boolean isPrimary() {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     */
    public boolean isPrimaryUnlogged() {
        throw new RuntimeException("Stub!");
    }

    public boolean isAdmin() {
        throw new RuntimeException("Stub!");
    }
    public boolean isGuest() {
        throw new RuntimeException("Stub!");
    }

    public boolean isRestricted() {
        throw new RuntimeException("Stub!");
    }

    public boolean isProfile() {
        throw new RuntimeException("Stub!");
    }

    public boolean isManagedProfile() {
        throw new RuntimeException("Stub!");
    }

    public boolean isCloneProfile() {
        throw new RuntimeException("Stub!");
    }

    public boolean isCommunalProfile() {
        throw new RuntimeException("Stub!");
    }


    public boolean isPrivateProfile() {
        throw new RuntimeException("Stub!");
    }

    public boolean isSupervisingProfile() {
        throw new RuntimeException("Stub!");
    }

    /** See {@link #FLAG_DISABLED}*/

    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }

    public boolean isQuietModeEnabled() {
        throw new RuntimeException("Stub!");
    }

    public boolean isEphemeral() {
        throw new RuntimeException("Stub!");
    }

    /** @hide */

    public boolean isForTesting() {
        throw new RuntimeException("Stub!");
    }

    public boolean isInitialized() {
        throw new RuntimeException("Stub!");
    }

    public boolean isDemo() {
        throw new RuntimeException("Stub!");
    }

    /** Returns whether the user is a "non-profile human user", as defined by {@link #FLAG_FULL}. */
    public boolean isFull() {
        throw new RuntimeException("Stub!");
    }

    /**
     * @see #FLAG_MAIN
     */
    public boolean isMain() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Same as {@link #isMain()}, but doesn't log the call (when logging multi-user violations is
     * enabled).
     *
     * <p>Should be called by methods that don't need to log (for example, because they're internal
     * to user infra) or already logged (to avoid duplicate entries).
     *
     * @hide
     */
    public boolean isMainUnlogged() {
        throw new RuntimeException("Stub!");
    }

    /**
     * @return true if this user can be switched to.
     **/

    public boolean supportsSwitchTo() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns whether the given user type, with the given base set of flags, can be switched to.
     * @hide
     */
    public static boolean supportsSwitchTo(String userType, @UserInfoFlag int flags) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns whether the userType is of type {@link UserManager#USER_TYPE_SYSTEM_HEADLESS} and
     * {@link com.android.internal.R.bool#config_canSwitchToHeadlessSystemUser} is true.
     */

    private static boolean isSwitchableHeadlessSystemUser(String userType) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns whether this is a human user that should be able to be switched to via the UI.
     *
     * <p>This method checks if {@link #supportsSwitchTo()} is true AND the user is a full user.
     * It specifically excludes the headless system user, as switching to that user
     * is typically a framework-level operation and not available via the usual switcher UIs.
     *
     * @hide
     */

    public boolean isUiSwitchableHumanUser() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns whether the user can have profiles, in general (on the basis of its UserInfo data).
     * See {@link #canHaveProfile(String)} for more fine-grained determination.
     */
    public boolean canHaveProfile() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns if the user can have a profile of the given type (on the basis of its UserInfo data).
     * @hide
     */
    public boolean canHaveProfile(String userType) {
        throw new RuntimeException("Stub!");
    }

    // TODO(b/142482943): Get rid of this (after removing it from all tests) if feasible.
    /**
     * This is dangerous since it doesn't set the mandatory fields. Use a different
     * constructor instead.
     */

    public UserInfo() {
    }

    public UserInfo(UserInfo orig) {
        throw new RuntimeException("Stub!");
    }


    public UserHandle getUserHandle() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public String toString() {
        // NOTE:  do not change this string, it's used by 'pm list users', which in turn is
        // used and parsed by TestDevice. In other words, if you change it, you'd have to change
        // TestDevice, TestDeviceTest, and possibly others....
        return "UserInfo{" + id + ":" + name + ":" + Integer.toHexString(flags) + "}";
    }

    public String toFullString() {
        return "UserInfo[id=" + id
                + ", name=" + name
                + ", type=" + userType
                + ", flags=" + flagsToString(flags)
                + (preCreated ? " (pre-created)" : "")
                + (convertedFromPreCreated ? " (converted)" : "")
                + (partial ? " (partial)" : "")
                + "]";
    }

    /** @hide */
    public static String flagsToString(int flags) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    private UserInfo(Parcel source) {
        throw new RuntimeException("Stub!");
    }
}

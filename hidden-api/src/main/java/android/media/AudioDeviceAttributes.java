/*
 * Copyright (C) 2019 The Android Open Source Project
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

package android.media;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the attributes of an audio device: its type (speaker, headset...), address
 * (if known) and role (input, output).
 * <p>Unlike {@link AudioDeviceInfo}, the device
 * doesn't need to be connected to be uniquely identified, it can
 * for instance represent a specific A2DP headset even after a
 * disconnection, whereas the corresponding <code>AudioDeviceInfo</code>
 * would then be invalid.
 * <p>While creating / obtaining an instance is not protected by a
 * permission, APIs using one rely on MODIFY_AUDIO_ROUTING.
 */

public final class AudioDeviceAttributes implements Parcelable {

    /**
     * A role identifying input devices, such as microphones.
     */
    public static int ROLE_INPUT;
    /**
     * A role identifying output devices, such as speakers or headphones.
     */
    public static int ROLE_OUTPUT;
    
    @Retention(RetentionPolicy.SOURCE)
    public @interface Role {}

    /**
     * The audio device type, as defined in {@link AudioDeviceInfo}
     */
    private final int mType;
    /**
     * The unique address of the device. Some devices don't have addresses, only an empty string.
     */
    private String mAddress;
    /**
     * The non-unique name of the device. Some devices don't have names, only an empty string.
     * Should not be used as a unique identifier for a device.
     */
    private final String mName;
    /**
     * Is input or output device
     */
    private final @Role int mRole;
    /**
     * The internal audio device type
     */
    private final int mNativeType;
    /**
     * List of AudioProfiles supported by the device
     */
    private final List<AudioProfile> mAudioProfiles;
    /**
     * List of AudioDescriptors supported by the device
     */
    private final List<AudioDescriptor> mAudioDescriptors;

    /**
     * @hide
     * Constructor from a valid {@link AudioDeviceInfo}
     * @param deviceInfo the connected audio device from which to obtain the device-identifying
     *                   type and address.
     */
    
    public AudioDeviceAttributes(AudioDeviceInfo deviceInfo) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     * Constructor from role, device type and address
     * @param role indicates input or output role
     * @param type the device type, as defined in {@link AudioDeviceInfo}
     * @param address the address of the device, or an empty string for devices without one
     */
    
    public AudioDeviceAttributes(@Role int role, int type,
                                 String address) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     * Constructor with specification of all attributes
     * @param role indicates input or output role
     * @param type the device type, as defined in {@link AudioDeviceInfo}
     * @param address the address of the device, or an empty string for devices without one
     * @param name the name of the device, or an empty string for devices without one
     * @param profiles the list of AudioProfiles supported by the device
     * @param descriptors the list of AudioDescriptors supported by the device
     */
    
    public AudioDeviceAttributes(@Role int role, int type,
                                 String address, String name, List<AudioProfile> profiles,
                                 List<AudioDescriptor> descriptors) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     * Constructor with specification of all attributes
     * @param role indicates input or output role
     * @param nativeType the native device type
     * @param address the address of the device, or an empty string for devices without one
     * @param name the name of the device, or an empty string for devices without one
     * @param profiles the list of AudioProfiles supported by the device
     * @param descriptors the list of AudioDescriptors supported by the device
     */
    public AudioDeviceAttributes(@Role int role, String address,
                                 int nativeType, String name,
                                 List<AudioProfile> profiles,
                                 List<AudioDescriptor> descriptors) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Constructor with specification of all attributes
     * @param role indicates input or output role
     * @param type the device type, as defined in {@link AudioDeviceInfo}
     * @param nativeType the native device type
     * @param address the address of the device, or an empty string for devices without one
     * @param name the name of the device, or an empty string for devices without one
     * @param profiles the list of AudioProfiles supported by the device
     * @param descriptors the list of AudioDescriptors supported by the device
     */
    private AudioDeviceAttributes(@Role int role, int type,
                                  int nativeType, String address,
                                  String name, List<AudioProfile> profiles,
                                  List<AudioDescriptor> descriptors) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     * Constructor called from AudioSystem JNI when creating an AudioDeviceAttributes from a native
     * AudioDeviceTypeAddr instance.
     * @param nativeType the internal device type, as defined in {@link AudioSystem}
     * @param address the address of the device, or an empty string for devices without one
     */
    public AudioDeviceAttributes(int nativeType, String address) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     * Constructor called from BtHelper to connect or disconnect a Bluetooth device.
     * @param nativeType the internal device type, as defined in {@link AudioSystem}
     * @param address the address of the device, or an empty string for devices without one
     * @param name the name of the device, or an empty string for devices without one
     */
    public AudioDeviceAttributes(int nativeType, String address, String name) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     * Copy Constructor.
     * @param ada the copied AudioDeviceAttributes
     */
    public AudioDeviceAttributes(AudioDeviceAttributes ada) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     * Returns the role of a device
     * @return the role
     */
    
    public @Role int getRole() {
        return mRole;
    }

    /**
     * @hide
     * Returns the audio device type of a device
     * @return the type, as defined in {@link AudioDeviceInfo}
     */
    
    public int getType() {
        return mType;
    }

    /**
     * @hide
     * Returns the address of the audio device, or an empty string for devices without one
     * @return the device address
     */
    
    public String getAddress() {
        return mAddress;
    }

    /**
     * @hide
     * Sets the device address. Only used by audio service.
     */
    public void setAddress(String address) {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     * Returns the name of the audio device, or an empty string for devices without one
     * @return the device name
     */
    
    public String getName() {
        return mName;
    }

    /**
     * @hide
     * Returns the internal device type of a device
     * @return the internal device type
     */
    public int getInternalType() {
        return mNativeType;
    }

    /**
     * @hide
     * Returns the list of AudioProfiles supported by the device
     * @return the list of AudioProfiles
     */
    
    public List<AudioProfile> getAudioProfiles() {
        return mAudioProfiles;
    }

    /**
     * @hide
     * Returns the list of AudioDescriptors supported by the device
     * @return the list of AudioDescriptors
     */
    public List<AudioDescriptor> getAudioDescriptors() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public boolean equals(Object o) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Returns true if the role, type and address are equal. Called to compare with an
     * AudioDeviceAttributes that was created from a native AudioDeviceTypeAddr instance.
     * @param o object to compare with
     * @return whether role, type and address are equal
     */
    public boolean equalTypeAddress(Object o) {
        throw new RuntimeException("Stub!");
    }

    public static String roleToString(@Role int role) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }

    /**
     * @hide
     * Creates a copy of an AudioDeviceAttributes that only stores the device type, role and
     * address.
     *
     * <p>It's recommended to use this method when comparing instances with equals (e.g.: when
     * using them as keys to maps) to recognize a given device based on type and address,
     * ignoring any other fields such as audio descriptors and profiles.
     * @return a new AudioDeviceAttributes from type and address of the original one
     */
    public AudioDeviceAttributes createFromTypeAndAddress() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }

    private AudioDeviceAttributes(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    public static final Parcelable.Creator<AudioDeviceAttributes> CREATOR =
            new Parcelable.Creator<AudioDeviceAttributes>() {
                /**
                 * Rebuilds an AudioDeviceAttributes previously stored with writeToParcel().
                 * @param p Parcel object to read the AudioDeviceAttributes from
                 * @return a new AudioDeviceAttributes created from the data in the parcel
                 */
                public AudioDeviceAttributes createFromParcel(Parcel p) {
                    return new AudioDeviceAttributes(p);
                }

                public AudioDeviceAttributes[] newArray(int size) {
                    return new AudioDeviceAttributes[size];
                }
            };
}

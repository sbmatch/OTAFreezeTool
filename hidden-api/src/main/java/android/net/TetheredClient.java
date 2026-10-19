/*
 * Copyright (C) 2020 The Android Open Source Project
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

package android.net;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Information on a tethered downstream client.
 *
 */

public final class TetheredClient implements Parcelable {
    private final MacAddress mMacAddress;
    private final List<AddressInfo> mAddresses;
    // TODO: use an @IntDef here
    private final int mTetheringType;

    public TetheredClient(MacAddress macAddress,
                          Collection<AddressInfo> addresses, int tetheringType) {
        throw new RuntimeException("Stub!");
    }

    private TetheredClient(Parcel in) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get the MAC address used to identify the client.
     */
   
    public MacAddress getMacAddress() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get information on the list of addresses that are associated with the client.
     */
    public List<AddressInfo> getAddresses() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Get the type of tethering used by the client.
     * @return one of the {@code TetheringManager#TETHERING_*} constants.
     */
    public int getTetheringType() {
        throw new RuntimeException("Stub!");
    }

    /**
     * Return a new {@link TetheredClient} that has all the attributes of this instance, plus the
     * {@link AddressInfo} of the provided {@link TetheredClient}.
     *
     * <p>Duplicate addresses are removed.
     * 
     */
    public TetheredClient addAddresses(TetheredClient other) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMacAddress, mAddresses, mTetheringType);
    }

    @Override
    public boolean equals(Object obj) {
        throw new RuntimeException("Stub!");
    }

    /**
     * Information on an lease assigned to a tethered client.
     */
    public static final class AddressInfo implements Parcelable {
        private final LinkAddress mAddress;
        private final String mHostname;

        
        public AddressInfo(LinkAddress address, String hostname) {
            throw new RuntimeException("Stub!");
        }

        private AddressInfo(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public void writeToParcel(@androidx.annotation.NonNull Parcel dest, int flags) {
            throw new RuntimeException("Stub!");
        }

        /**
         * Get the link address (including prefix length and lifetime) used by the client.
         *
         * This may be an IPv4 or IPv6 address.
         */
        
        public LinkAddress getAddress() {
            throw new RuntimeException("Stub!");
        }

        /**
         * Get the hostname that was advertised by the client when obtaining its address, if any.
         */
       
        public String getHostname() {
            throw new RuntimeException("Stub!");
        }

        /**
         * Get the expiration time of the address assigned to the client.
         * 
         */
        public long getExpirationTime() {
            throw new RuntimeException("Stub!");
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public int hashCode() {
            throw new RuntimeException("Stub!");
        }

        @Override
        public boolean equals(Object obj) {
            throw new RuntimeException("Stub!");
        }

        public static final Creator<AddressInfo> CREATOR = new Creator<AddressInfo>() {
            
            @Override
            public AddressInfo createFromParcel(Parcel in) {
                throw new RuntimeException("Stub!");
            }
            
            @Override
            public AddressInfo[] newArray(int size) {
                throw new RuntimeException("Stub!");
            }
        };
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    public static final Creator<TetheredClient> CREATOR = new Creator<TetheredClient>() {
        
        @Override
        public TetheredClient createFromParcel(Parcel in) {
            throw new RuntimeException("Stub!");
        }

      
        @Override
        public TetheredClient[] newArray(int size) {
            throw new RuntimeException("Stub!");
        }
    };

  
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}

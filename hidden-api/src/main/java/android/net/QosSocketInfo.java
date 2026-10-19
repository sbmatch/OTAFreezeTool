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
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Used in conjunction with
 * {@link ConnectivityManager#registerQosCallback}
 * in order to receive Qos Sessions related to the local address and port of a bound {@link Socket}
 * and/or remote address and port of a connected {@link Socket}.
 *
 * @hide
 */

public final class QosSocketInfo implements Parcelable {

    private final Network mNetwork;

    
    private final ParcelFileDescriptor mParcelFileDescriptor;

    
    private final InetSocketAddress mLocalSocketAddress;

    private final InetSocketAddress mRemoteSocketAddress;

    private final int mSocketType;

    /**
     * The {@link Network} the socket is on.
     *
     * @return the registered {@link Network}
     */
    
    public Network getNetwork() {
        return mNetwork;
    }

    /**
     * The parcel file descriptor wrapped around the socket's file descriptor.
     *
     * @return the parcel file descriptor of the socket
     * @hide
     */
    
    public ParcelFileDescriptor getParcelFileDescriptor() {
        return mParcelFileDescriptor;
    }

    /**
     * The local address of the socket passed into {@link QosSocketInfo(Network, Socket)}.
     * The value does not reflect any changes that occur to the socket after it is first set
     * in the constructor.
     *
     * @return the local address of the socket
     */
    
    public InetSocketAddress getLocalSocketAddress() {
        return mLocalSocketAddress;
    }

    /**
     * The remote address of the socket passed into {@link QosSocketInfo(Network, Socket)}.
     * The value does not reflect any changes that occur to the socket after it is first set
     * in the constructor.
     *
     * @return the remote address of the socket if socket is connected, null otherwise
     */

    public InetSocketAddress getRemoteSocketAddress() {
        return mRemoteSocketAddress;
    }

    /**
     * The socket type of the socket passed in when this QosSocketInfo object was constructed.
     *
     * @return the socket type of the socket.
     * @hide
     */
    public int getSocketType()  {
        return mSocketType;
    }

    /**
     * Creates a {@link QosSocketInfo} given a {@link Network} and bound {@link Socket}.  The
     * {@link Socket} must remain bound in order to receive {@link QosSession}s.
     *
     * @param network the network
     * @param socket the bound {@link Socket}
     */
    public QosSocketInfo( final Network network,  final Socket socket)
            throws IOException {
        throw new RuntimeException("Stub!");
    }

    /**
     * Creates a {@link QosSocketInfo} given a {@link Network} and bound {@link DatagramSocket}. The
     * {@link DatagramSocket} must remain bound in order to receive {@link QosSession}s.
     *
     * @param network the network
     * @param socket the bound {@link DatagramSocket}
     */
    public QosSocketInfo( final Network network,  final DatagramSocket socket)
            throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* Parcelable methods */
    private QosSocketInfo(final Parcel in) {
        throw new RuntimeException("Stub!");
    }

    private InetSocketAddress readSocketAddress(final Parcel in) {
        throw new RuntimeException("Stub!");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( final Parcel dest, final int flags) {
        writeToParcelInternal(dest, flags, /*includeFd=*/ true);
    }

    /**
     * Used when sending QosSocketInfo to telephony, which does not need access to the socket FD.
     * @hide
     */
    public void writeToParcelWithoutFd( final Parcel dest, final int flags) {
        writeToParcelInternal(dest, flags, /*includeFd=*/ false);
    }

    private void writeToParcelInternal(
             final Parcel dest, final int flags, boolean includeFd) {
        throw new RuntimeException("Stub!");
    }

    
    public static final Parcelable.Creator<QosSocketInfo> CREATOR =
            new Parcelable.Creator<QosSocketInfo>() {
                
                @Override
                public QosSocketInfo createFromParcel(final Parcel in) {
                    return new QosSocketInfo(in);
                }

                
                @Override
                public QosSocketInfo[] newArray(final int size) {
                    return new QosSocketInfo[size];
                }
            };
}

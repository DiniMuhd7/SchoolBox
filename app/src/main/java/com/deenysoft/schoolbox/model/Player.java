/*
 * Copyright 2015 Google Inc.
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

package com.deenysoft.schoolbox.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stores values to identify the subject that is currently attempting to solve quizzes.
 */
public class Player implements Parcelable {

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
    private final String mFirstName;
    private final Avatar mAvatar;

    public Player(String firstName, Avatar avatar) {
        mFirstName = firstName;
        mAvatar = avatar;
    }

    protected Player(Parcel in) {
        mFirstName = in.readString();
        mAvatar = Avatar.values()[in.readInt()];
    }

    public String getFirstName() {
        return mFirstName;
    }


    public Avatar getAvatar() {
        return mAvatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFirstName);
        dest.writeInt(mAvatar.ordinal());
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player) o;

        if (mAvatar != player.mAvatar) {
            return false;
        }
        if (!mFirstName.equals(player.mFirstName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mFirstName.hashCode();
        result = 31 * result + mAvatar.hashCode();
        return result;
    }
}

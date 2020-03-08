/*
* Copyright (C) 2019 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.omnirom.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceManager;

import org.omnirom.device.DeviceSettings;

 public class HWKeySwitch implements OnPreferenceChangeListener {

 private static final String FILE = "/proc/touchpanel/capacitive_keys_disable";

 public static final String SETTINGS_KEY = DeviceSettings.KEY_SETTINGS_PREFIX + DeviceSettings.HW_KEY_SWITCH;

 private Context mContext;

 public HWKeySwitch(Context context) {
        mContext = context;
    }

 public static String getFile() {
        if (Utils.fileWritable(FILE)) {
            return FILE;
        }
        return null;
    }


public static boolean isSupported() {
        return Utils.fileWritable(getFile());
    }

public static boolean isCurrentlyEnabled(Context context) {
        return Utils.getFileValueAsBoolean(getFile(), false);
    }

@Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Boolean enabled = (Boolean) newValue;
        Settings.System.putInt(mContext.getContentResolver(), SETTINGS_KEY, enabled ? 1 : 0);
        Utils.writeValue(getFile(), enabled ? "1" : "0");
        Settings.System.putInt(mContext.getContentResolver(), Settings.System.OMNI_BUTTON_BACKLIGHT_ENABLE , enabled ? 0 : 1);
        return true;
    }
}

/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.dashclock.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.google.android.apps.dashclock.render.DashClockRenderer;

import net.nurik.roman.dashclock.R;

/**
 * Helper class for working with DashClock appearance settings.
 */
public class AppearanceConfig {
    static final String COMPONENT_TIME = "time";
    static final String COMPONENT_DATE = "date";

    static final String PREF_STYLE_TIME = "pref_style_time";
    static final String PREF_STYLE_DATE = "pref_style_date";

    static final String PREF_HIDE_SETTINGS = "pref_hide_settings"; // deprecated
    static final String PREF_SETTINGS_BUTTON = "pref_settings_button";
    static final String PREF_AGGRESSIVE_CENTERING = "pref_aggressive_centering";

    static final String PREF_SETTINGS_BUTTON_HIDDEN = "hidden";
    static final String PREF_SETTINGS_BUTTON_IN_WIDGET = "inwidget";
    static final String PREF_SETTINGS_BUTTON_IN_LAUNCHER = "inlauncher";

    static final String PREF_HOMESCREEN_FOREGROUND_COLOR = "pref_homescreen_foreground_color";
    static final String PREF_HOMESCREEN_BACKGROUND_OPACITY = "pref_homescreen_background_opacity";

    static final String PREF_COLLAPSE_CONDITIONS = "pref_collapse_conditions";
    static final int EXPAND_DEFAULT = -1;
    static final int EXPAND_ALWAYS = 0;

    static final String PREF_HOMESCREEN_HIDE_CLOCK = "pref_homescreen_hide_clock";
    static final String PREF_HOMESCREEN_SHOW_SEPARATOR = "pref_homescreen_show_separator";

    static final String PREF_LOCKSCREEN_FOREGROUND_COLOR = "pref_lockscreen_foreground_color";
    static final String PREF_LOCKSCREEN_BACKGROUND_OPACITY = "pref_lockscreen_background_opacity";
    static final String PREF_LOCKSCREEN_HIDE_CLOCK = "pref_lockscreen_hide_clock";
    static final String PREF_LOCKSCREEN_SHOW_SEPARATOR = "pref_lockscreen_show_separator";

    // Font preferences
    static final String PREF_FONT = "pref_font";
    public static final String PREF_FONT_LIGHT = "light";
    public static final String PREF_FONT_CONDENSED = "condensed";

    public static final String PREF_TEXT_DENSITY = "pref_text_density";

    public static final int DEFAULT_WIDGET_FOREGROUND_COLOR = Color.WHITE;
    public static final int DEFAULT_WIDGET_BACKGROUND_COLOR = Color.TRANSPARENT;


    static String[] TIME_STYLE_NAMES = new String[]{
            "default",
            "light",
            "alpha",
            "stock",
            "condensed",
            "big_small",
            "alpha_condensed",
            "analog1",
            "analog2",
    };

    static String[] DATE_STYLE_NAMES = new String[]{
            "default",
            "simple",
            "condensed_bold",
            "default_condensed",
    };

    public static int getCurrentTimeLayout(Context context, int foregroundColor) {
        String currentTimeStyleName = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_STYLE_TIME, TIME_STYLE_NAMES[0]);
        if (currentTimeStyleName.contains("analog")) {
            if (foregroundColor == Color.BLACK) {
                currentTimeStyleName += "_black";
            } else {
                currentTimeStyleName += "_white";
            }
        }
        return getLayoutByStyleName(context, COMPONENT_TIME, currentTimeStyleName);
    }

    public static int getCurrentDateLayout(Context context) {
        String currentDateStyleName = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_STYLE_DATE, DATE_STYLE_NAMES[0]);
        return getLayoutByStyleName(context, COMPONENT_DATE, currentDateStyleName);
    }

    public static int getLayoutByStyleName(Context context, String component, String name) {
        return context.getResources().getIdentifier(
                "widget_include_" + component + "_style_" + name,
                "layout", context.getPackageName());
    }

    public static boolean isSettingsButtonHidden(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String pref = sp.getString(PREF_SETTINGS_BUTTON, null);
        if (pref == null) {
            // Check older preference
            return sp.getBoolean(PREF_HIDE_SETTINGS, false);
        }

        return !PREF_SETTINGS_BUTTON_IN_WIDGET.equals(pref);
    }

    public static boolean isClockHiddenOnHomeScreen(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_HOMESCREEN_HIDE_CLOCK, false);
    }

    public static boolean isAggressiveCenteringEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_AGGRESSIVE_CENTERING, true);
    }

    public static boolean isClockHiddenOnLockScreen(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_LOCKSCREEN_HIDE_CLOCK, false);
    }

    public static boolean isForceExpandEnabled(Context context,
        boolean isExpanded, int visibleExtensions) {
        int expandCollapseRules = Integer.parseInt(PreferenceManager.
            getDefaultSharedPreferences(context)
            .getString(PREF_COLLAPSE_CONDITIONS, "-1"));

        switch (expandCollapseRules) {
            case EXPAND_DEFAULT:
                return isExpanded;
            case EXPAND_ALWAYS:
                return true;
            default:
                if (visibleExtensions <= expandCollapseRules)
                    return true;
        }
        return false;
    }

    public static boolean getShowSeparator(Context context, int target) {
        if (target == DashClockRenderer.Options.TARGET_HOME_SCREEN) {
            return PreferenceManager.getDefaultSharedPreferences(context)
                                    .getBoolean(PREF_HOMESCREEN_SHOW_SEPARATOR, false);
        } else if (target == DashClockRenderer.Options.TARGET_LOCK_SCREEN) {
            return PreferenceManager.getDefaultSharedPreferences(context)
                                    .getBoolean(PREF_LOCKSCREEN_SHOW_SEPARATOR, false);
        }
        return false;
    }

    public static int getForegroundColor(Context context, int target) {
        if (target == DashClockRenderer.Options.TARGET_HOME_SCREEN) {
            return PreferenceManager.getDefaultSharedPreferences(context)
                    .getInt(PREF_HOMESCREEN_FOREGROUND_COLOR, Color.WHITE);
        } else if (target == DashClockRenderer.Options.TARGET_LOCK_SCREEN) {
            return PreferenceManager.getDefaultSharedPreferences(context)
                    .getInt(PREF_LOCKSCREEN_FOREGROUND_COLOR, Color.WHITE);
        }
        return DEFAULT_WIDGET_FOREGROUND_COLOR;
    }

    public static int getBackgroundColor(Context context, int target) {
        int foregroundColor = getForegroundColor(context, target);
        int opacity = 0;
        try {
            if (target == DashClockRenderer.Options.TARGET_HOME_SCREEN) {
                opacity = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(PREF_HOMESCREEN_BACKGROUND_OPACITY, "50"));
            } else if (target == DashClockRenderer.Options.TARGET_LOCK_SCREEN) {
                opacity = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(PREF_LOCKSCREEN_BACKGROUND_OPACITY, "0"));
            }
        } catch (NumberFormatException ignored) {
        }

        int backgroundColor = (foregroundColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
        return (backgroundColor & 0xffffff) | ((opacity * 255 / 100) << 24);
    }

    public static String getFont(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                                .getString(PREF_FONT, PREF_FONT_LIGHT);
    }

    public static int getTextDensity(Context context) {
        return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_TEXT_DENSITY, TextDensity.DEFAULT_DENSITY_STR));
    }


    /**
     * Class with text size metrics for the different text densities
     */
    public static class TextDensity {
        // Display density
        public static final int DEFAULT_DENSITY = 0;
        public static final String DEFAULT_DENSITY_STR = "0";

        // Text sizes
        private static final int[] sExtensionTitleTextSizeResId = new int[] {
                R.dimen.extension_expanded_title_text_size_comfortable,
                R.dimen.extension_expanded_title_text_size_cozy,
                R.dimen.extension_expanded_title_text_size_compact
        };
        private static final int[] sExtensionBodyTextSizeResId = new int[] {
                R.dimen.extension_expanded_body_text_size_comfortable,
                R.dimen.extension_expanded_body_text_size_cozy,
                R.dimen.extension_expanded_body_text_size_compact
        };
        private static final int[] sClockTextSizeLargeResId = new int[] {
                R.dimen.clock_text_size_large_comfortable,
                R.dimen.clock_text_size_large_cozy,
                R.dimen.clock_text_size_large_compact
        };
        private static final int[] sClockTextSizeSmallResId = new int[] {
                R.dimen.clock_text_size_small_comfortable,
                R.dimen.clock_text_size_small_cozy,
                R.dimen.clock_text_size_small_compact
        };
        private static final int[] sClockDateTextSizeResId = new int[] {
                R.dimen.clock_date_text_size_comfortable,
                R.dimen.clock_date_text_size_cozy,
                R.dimen.clock_date_text_size_compact
        };

        // Padding
        private static final int[] sRowPaddingTopResId = new int[] {
                R.dimen.list_item_row_padding_top_comfortable,
                R.dimen.list_item_row_padding_top_cozy,
                R.dimen.list_item_row_padding_top_compact
        };
        private static final int[] sRowPaddingBottomResId = new int[] {
                R.dimen.list_item_row_padding_bottom_comfortable,
                R.dimen.list_item_row_padding_bottom_cozy,
                R.dimen.list_item_row_padding_bottom_compact
        };

        private static final int[] sRowPaddingSideResId = new int[] {
                R.dimen.list_item_row_padding_side_comfortable,
                R.dimen.list_item_row_padding_side_cozy,
                R.dimen.list_item_row_padding_side_compact
        };

        // Static values, so we don't always hit Resources
        private static int[] sTitleTextSizeCache = new int [] {-1, -1, -1};
        private static int[] sBodyTextSizeCache = new int [] {-1, -1, -1};
        private static int[] sRowPaddingTopCache = new int [] {-1, -1, -1};
        private static int[] sRowPaddingBottomCache = new int [] {-1, -1, -1};
        private static int[] sRowPaddingSideCache = new int [] {-1, -1, -1};

        private static int[] sClockTextSizeLargeCache = new int [] {-1, -1, -1};
        private static int[] sClockTextSizeSmallCache = new int [] {-1, -1, -1};
        private static int[] sClockDateTextSizeCache = new int [] {-1, -1, -1};

        // Results in PX
        public static int getExtensionTitleTextSize(Context context, int textDensity) {
            if (sTitleTextSizeCache[textDensity] == -1)
                sTitleTextSizeCache[textDensity] = context.getResources()
                        .getDimensionPixelSize(sExtensionTitleTextSizeResId[textDensity]);
            return sTitleTextSizeCache[textDensity];
        }

        // Results in PX
        public static int getExtensionBodyTextSize(Context context, int textDensity) {
            if (sBodyTextSizeCache[textDensity] == -1)
                sBodyTextSizeCache[textDensity] = context.getResources()
                        .getDimensionPixelSize(sExtensionBodyTextSizeResId[textDensity]);
            return sBodyTextSizeCache[textDensity];
        }

        // Results in PX
        public static int getClockTextSizeLarge(Context context, int textDensity) {
            if (sClockTextSizeLargeCache[textDensity] == -1)
                sClockTextSizeLargeCache[textDensity] = context.getResources()
                        .getDimensionPixelSize(sClockTextSizeLargeResId[textDensity]);
            return sClockTextSizeLargeCache[textDensity];
        }

        // Results in PX
        public static int getClockTextSizeSmall(Context context, int textDensity) {
            if (sClockTextSizeSmallCache[textDensity] == -1)
                sClockTextSizeSmallCache[textDensity] = context.getResources()
                        .getDimensionPixelSize(sClockTextSizeSmallResId[textDensity]);
            return sClockTextSizeSmallCache[textDensity];
        }

        // Results in PX
        public static int getClockDateTextSize(Context context, int textDensity) {
            if (sClockDateTextSizeCache[textDensity] == -1)
                sClockDateTextSizeCache[textDensity] = context.getResources()
                        .getDimensionPixelSize(sClockDateTextSizeResId[textDensity]);
            return sClockDateTextSizeCache[textDensity];
        }

        // Results in PX
        public static int getRowPaddingTop(Context context, int textDensity) {
            if (sRowPaddingTopCache[textDensity] == -1)
                sRowPaddingTopCache[textDensity] = context.getResources()
                        .getDimensionPixelSize(sRowPaddingTopResId[textDensity]);
            return sRowPaddingTopCache[textDensity];
        }

        // Results in PX
        public static int getRowPaddingBottom(Context context, int textDensity) {
            if (sRowPaddingBottomCache[textDensity] == -1)
                sRowPaddingBottomCache[textDensity] = context.getResources()
                        .getDimensionPixelSize(sRowPaddingBottomResId[textDensity]);
            return sRowPaddingBottomCache[textDensity];
        }

        // Results in PX
        public static int getRowPaddingSide(Context context, int textDensity) {
            if (sRowPaddingSideCache[textDensity] == -1)
                sRowPaddingSideCache[textDensity] = context.getResources()
                        .getDimensionPixelSize(sRowPaddingSideResId[textDensity]);
            return sRowPaddingSideCache[textDensity];
        }

    }
}

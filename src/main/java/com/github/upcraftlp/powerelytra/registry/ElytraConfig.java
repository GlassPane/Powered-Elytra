package com.github.upcraftlp.powerelytra.registry;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

public class ElytraConfig {

    @SerializedName("id")
    String name;

    @SerializedName("can_boost_rockets")
    boolean canUseRockets = true;

    @SerializedName("can_boost_rf")
    boolean canUseRFBoost = true;

    @SerializedName("battery_capacity")
    int capacity = 0;

    @SerializedName("rf_used_per_tick")
    int costPerFlightTick = 0;

    @SerializedName("rf_used_per_boost")
    int costPerBoosterRocket = 0;

    @Nullable
    @SerializedName("texture")
    String textureName = null;
}

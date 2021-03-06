package org.kilocraft.essentials.api.event;

import net.minecraft.util.math.Vec3d;

public interface Locatable extends Event {

    /**
     * Gets the location of this event
     *
     * @return A Vec3d of the location
     */
    Vec3d getLocation();
}

package io.github.kgriff0n.event;

import io.github.kgriff0n.Config;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class ServerStop implements ServerLifecycleEvents.ServerStopped {
    @Override
    public void onServerStopped(MinecraftServer server) {
        Config.save();
    }
}

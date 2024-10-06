package net.soulsweaponry.networking.packets.S2C;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.soulsweaponry.client.entitydata.ClientFreyrSwordSummonData;

import java.util.UUID;
import java.util.function.Supplier;

public class FreyrSwordSummonDataSyncS2C {

    private final UUID uuid;

    public FreyrSwordSummonDataSyncS2C(UUID uuid) {
        this.uuid = uuid;
    }

    // Same as encode
    public void toBytes(PacketByteBuf buf) {
        buf.writeUuid(this.uuid);
    }

    //Same as decode/fromBytes
    public FreyrSwordSummonDataSyncS2C(PacketByteBuf buf) {
        this.uuid = buf.readUuid();
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientWorld world = MinecraftClient.getInstance().world;
            this.handlePacket(world, this);
        }));
        context.setPacketHandled(true);
    }

    private void handlePacket(ClientWorld world, FreyrSwordSummonDataSyncS2C packet) {
        ClientFreyrSwordSummonData.setUUID(packet.getUuid());
    }
}
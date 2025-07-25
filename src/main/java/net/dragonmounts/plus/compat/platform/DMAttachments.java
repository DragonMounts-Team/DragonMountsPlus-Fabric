package net.dragonmounts.plus.compat.platform;

import net.dragonmounts.plus.common.capability.FluteHolder;
import net.dragonmounts.plus.common.inventory.FluteHolderImpl;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.world.item.ItemStack;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

@SuppressWarnings("UnstableApiUsage")
public class DMAttachments {
    public static final AttachmentType<FluteHolder> FLUTE_HOLDER = AttachmentRegistry.create(
            makeId("flute_holder"),
            builder -> builder.copyOnDeath()
                    .initializer(FluteHolderImpl::new)
                    .persistent(ItemStack.OPTIONAL_CODEC.xmap(FluteHolderImpl::of, FluteHolder::getFlute))
    );

    public static <T> boolean has(AttachmentTarget host, AttachmentType<T> type) {
        return host.hasAttached(type);
    }

    public static <T> T get(AttachmentTarget host, AttachmentType<T> type) {
        return host.getAttached(type);
    }

    public static <T> T getOrCreate(AttachmentTarget host, AttachmentType<T> type) {
        return host.getAttachedOrCreate(type);
    }

    public static void init() {}
}

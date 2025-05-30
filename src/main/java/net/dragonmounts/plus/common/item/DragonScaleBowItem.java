package net.dragonmounts.plus.common.item;

import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import org.jetbrains.annotations.Nullable;

import static net.dragonmounts.plus.common.DragonMountsShared.ITEM_TRANSLATION_KEY_PREFIX;

public class DragonScaleBowItem extends BowItem implements DragonTypified {
    public static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_bow";
    public final DragonType type;

    public DragonScaleBowItem(DragonType type, Properties props) {
        super(props.component(DMDataComponents.DRAGON_TYPE, type)
                .durability(type.tier.durability() >> 1)
                .enchantable(type.tier.enchantmentValue())
                .repairable(type.tier.repairItems())
        );
        this.type = type;
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        super.shootProjectile(shooter, projectile, index, velocity * 1.25F, inaccuracy * 0.75F, angle, target);
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}

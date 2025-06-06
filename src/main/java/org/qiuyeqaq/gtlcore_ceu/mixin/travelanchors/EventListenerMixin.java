package org.qiuyeqaq.gtlcore_ceu.mixin.travelanchors;

import org.qiuyeqaq.gtlcore_ceu.config.ConfigHolder;

import de.castcrafter.travelanchors.EventListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EventListener.class)
public class EventListenerMixin {

    @ModifyConstant(method = "onRightClick", remap = false, constant = @Constant(intValue = 30))
    private int modifyConsume1(int constant) {
        return ConfigHolder.INSTANCE.travelStaffCD;
    }
}

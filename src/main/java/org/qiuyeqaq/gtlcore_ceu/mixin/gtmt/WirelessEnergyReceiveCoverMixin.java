package org.qiuyeqaq.gtlcore_ceu.mixin.gtmt;

import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.cover.CoverBehavior;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.hepdd.gtmthings.common.cover.WirelessEnergyReceiveCover;
import net.minecraft.core.Direction;
import org.qiuyeqaq.gtlcore_ceu.common.machine.multiblock.part.NeutronAcceleratorPartMachine;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WirelessEnergyReceiveCover.class)
public abstract class WirelessEnergyReceiveCoverMixin extends CoverBehavior {

    @Shadow(remap = false)
    @Final
    private int tier;

    public WirelessEnergyReceiveCoverMixin(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide) {
        super(definition, coverHolder, attachedSide);
    }

    @Inject(method = "canAttach", at = @At("HEAD"), remap = false, cancellable = true)
    public void canAttach(CallbackInfoReturnable<Boolean> cir) {
        if (MetaMachine.getMachine(coverHolder.getLevel(), coverHolder.getPos()) instanceof NeutronAcceleratorPartMachine neutronAcceleratorPartMachine) {
            cir.setReturnValue(neutronAcceleratorPartMachine.getTier() >= this.tier);
        }
    }
}

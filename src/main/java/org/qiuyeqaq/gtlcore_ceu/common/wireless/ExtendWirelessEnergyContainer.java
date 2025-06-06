package org.qiuyeqaq.gtlcore_ceu.common.wireless;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.hepdd.gtmthings.api.misc.WirelessEnergyContainer;
import com.hepdd.gtmthings.data.WirelessEnergySavaedData;
import com.hepdd.gtmthings.utils.BigIntegerUtils;
import lombok.Getter;
import net.minecraft.core.GlobalPos;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.UUID;

@Getter
public class ExtendWirelessEnergyContainer  extends WirelessEnergyContainer {

    private BigInteger capacity;

    private int loss;

    public ExtendWirelessEnergyContainer(UUID uuid, BigInteger storage, long rate, GlobalPos bindPos, BigInteger capacity, int loss) {
        super(uuid, storage, rate, bindPos);
        this.capacity = capacity;
        this.loss = loss;
    }

    public ExtendWirelessEnergyContainer(UUID uuid) {
        super(uuid, BigInteger.ZERO, 0, null);
        this.capacity = BigInteger.ZERO;
    }

    @Override
    public long addEnergy(long energy, @Nullable MetaMachine machine) {
        long change = Math.min(BigIntegerUtils.getLongValue(capacity.subtract(getStorage())), Math.min(getRate(), energy));
        if (change <= 0) return 0;
        long loss = change * this.loss / 1000;
        long actualChange = change - loss;
        setStorage(getStorage().add(BigInteger.valueOf(actualChange)));
        WirelessEnergySavaedData.INSTANCE.setDirty(true);
        if (observed && machine != null) {
            TRANSFER_DATA.put(machine, new ExtendTransferData(getUuid(), actualChange, loss, machine));
        }
        return change;
    }

    public long unrestrictedAddEnergy(long energy) {
        long change = Math.min(BigIntegerUtils.getLongValue(capacity.subtract(getStorage())), energy);
        if (change <= 0) return 0;
        setStorage(getStorage().add(BigInteger.valueOf(change - (change * this.loss / 1000))));
        WirelessEnergySavaedData.INSTANCE.setDirty(true);
        return change;
    }

    public long unrestrictedRemoveEnergy(long energy) {
        long change = Math.min(BigIntegerUtils.getLongValue(getStorage()), energy);
        if (change <= 0) return 0;
        setStorage(getStorage().subtract(BigInteger.valueOf(change)));
        WirelessEnergySavaedData.INSTANCE.setDirty(true);
        return change;
    }

    public void setCapacity(BigInteger capacity) {
        this.capacity = capacity;
        WirelessEnergySavaedData.INSTANCE.setDirty(true);
    }

    public void setLoss(int loss) {
        this.loss = loss;
        WirelessEnergySavaedData.INSTANCE.setDirty(true);
    }
}

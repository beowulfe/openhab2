package org.openhab.io.homekit.internal.accessories;

import java.util.concurrent.CompletableFuture;

import org.eclipse.smarthome.core.items.Item;
import org.eclipse.smarthome.core.items.Metadata;
import org.eclipse.smarthome.core.library.items.RollershutterItem;
import org.eclipse.smarthome.core.library.types.PercentType;
import org.openhab.io.homekit.internal.HomekitAccessoryUpdater;
import org.openhab.io.homekit.internal.OpenhabHomekitBridge;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.WindowCovering;
import com.beowulfe.hap.accessories.properties.WindowCoveringPositionState;

public class HomekitWindowCovering extends AbstractHomekitAccessoryImpl<RollershutterItem> implements WindowCovering {

    public HomekitWindowCovering(Item item, Metadata metadata, HomekitAccessoryUpdater updater,
            OpenhabHomekitBridge bridge) {
        super(item, metadata, updater, bridge, RollershutterItem.class);
    }

    @Override
    public CompletableFuture<Integer> getCurrentPosition() {
        PercentType value = getItem().getStateAs(PercentType.class);
        if (value == null) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(value.intValue());
    }

    @Override
    public CompletableFuture<Boolean> getObstructionDetected() {
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<WindowCoveringPositionState> getPositionState() {
        return CompletableFuture.completedFuture(WindowCoveringPositionState.STOPPED);
    }

    @Override
    public CompletableFuture<Integer> getTargetPosition() {
        return getCurrentPosition();
    }

    @Override
    public CompletableFuture<Void> setHoldPosition(boolean value) throws Exception {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> setTargetPosition(int value) throws Exception {
        getItem().send(new PercentType(value));
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void subscribeCurrentPosition(HomekitCharacteristicChangeCallback callback) {
        getUpdater().subscribe(getItem(), callback);
    }

    @Override
    public void subscribeObstructionDetected(HomekitCharacteristicChangeCallback callback) {
        // Not implemented
    }

    @Override
    public void subscribePositionState(HomekitCharacteristicChangeCallback callback) {
        // Not implemented
    }

    @Override
    public void subscribeTargetPosition(HomekitCharacteristicChangeCallback callback) {
        getUpdater().subscribe(getItem(), "targetPosition", callback);
    }

    @Override
    public void unsubscribeCurrentPosition() {
        getUpdater().unsubscribe(getItem());
    }

    @Override
    public void unsubscribeObstructionDetected() {
        // Not implemented
    }

    @Override
    public void unsubscribePositionState() {
        // Not implemented
    }

    @Override
    public void unsubscribeTargetPosition() {
        getUpdater().unsubscribe(getItem(), "targetPosition");
    }

}

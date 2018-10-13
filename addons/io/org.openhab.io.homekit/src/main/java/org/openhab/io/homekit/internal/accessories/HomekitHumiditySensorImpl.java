/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.io.homekit.internal.accessories;

import java.util.concurrent.CompletableFuture;

import org.eclipse.smarthome.core.items.Item;
import org.eclipse.smarthome.core.items.Metadata;
import org.eclipse.smarthome.core.library.items.NumberItem;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.openhab.io.homekit.internal.HomekitAccessoryUpdater;
import org.openhab.io.homekit.internal.OpenhabHomekitBridge;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.HumiditySensor;

/**
 *
 * @author Andy Lintner - Initial implementation
 */
public class HomekitHumiditySensorImpl extends AbstractHomekitAccessoryImpl<NumberItem> implements HumiditySensor {

    public HomekitHumiditySensorImpl(Item item, Metadata metadata, HomekitAccessoryUpdater updater,
            OpenhabHomekitBridge bridge) {
        super(item, metadata, updater, bridge, NumberItem.class);
    }

    @Override
    public CompletableFuture<Double> getCurrentRelativeHumidity() {
        DecimalType state = getItem().getStateAs(DecimalType.class);
        if (state == null) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(state.doubleValue());
    }

    @Override
    public void subscribeCurrentRelativeHumidity(HomekitCharacteristicChangeCallback callback) {
        getUpdater().subscribe(getItem(), callback);
    }

    @Override
    public void unsubscribeCurrentRelativeHumidity() {
        getUpdater().unsubscribe(getItem());
    }

}

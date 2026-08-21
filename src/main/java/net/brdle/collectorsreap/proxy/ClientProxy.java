package net.brdle.collectorsreap.proxy;

import net.brdle.collectorsreap.client.ClientEvents;
import net.neoforged.bus.api.IEventBus;

public class ClientProxy extends CommonProxy {
	@Override
	public void start(IEventBus modBus) {
		super.start(modBus);
		modBus.register(new ClientEvents());
	}
}

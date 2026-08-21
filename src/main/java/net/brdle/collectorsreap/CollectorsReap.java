package net.brdle.collectorsreap;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.brdle.collectorsreap.common.config.CRConfig;
import net.brdle.collectorsreap.proxy.ClientProxy;
import net.brdle.collectorsreap.proxy.CommonProxy;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(CollectorsReap.MODID)
public class CollectorsReap {
	public static final String MODID = "collectorsreap";
	public static final RegistryHelper REGISTRY_HELPER = RegistryHelper.create(MODID, helper -> {
	});
	public static CommonProxy proxy;

	public CollectorsReap(IEventBus modBus, ModContainer container) {
		REGISTRY_HELPER.register(modBus);

		proxy = FMLEnvironment.dist.isClient() ? new ClientProxy() : new CommonProxy();
		proxy.start(modBus);

		container.registerConfig(ModConfig.Type.COMMON, CRConfig.COMMON);
	}
}

package micdoodle8.mods.galacticraft.planets.venus.client;

import com.google.common.collect.Maps;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import micdoodle8.mods.galacticraft.planets.venus.ConfigManagerVenus;
import micdoodle8.mods.galacticraft.planets.venus.dimension.WorldProviderVenus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class TickHandlerClientVenus
{
    private Map<BlockPos, Integer> lightning = Maps.newHashMap();

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent event)
    {
        final Minecraft minecraft = FMLClientHandler.instance().getClient();
        final EntityPlayerSP player = minecraft.thePlayer;
        final EntityPlayerSP playerBaseClient = PlayerUtil.getPlayerBaseClientFromPlayer(player, false);

        if (event.phase == Phase.END)
        {
        }
    }

    @SubscribeEvent
    public void renderLightning(ClientProxyCore.EventSpecialRender event)
    {
        final Minecraft minecraft = FMLClientHandler.instance().getClient();
        final EntityPlayerSP player = minecraft.thePlayer;
        if (player != null && !ConfigManagerVenus.disableAmbientLightning)
        {
            Iterator<Map.Entry<BlockPos, Integer>> it = lightning.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry<BlockPos, Integer> entry = it.next();
                long seed = entry.getValue() / 10 + entry.getKey().getX() + entry.getKey().getZ();
                FakeLightningBoltRenderer.renderBolt(seed, entry.getKey().getX() - ClientProxyCore.playerPosX, entry.getKey().getY() - ClientProxyCore.playerPosY, entry.getKey().getZ() - ClientProxyCore.playerPosZ);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        final Minecraft minecraft = FMLClientHandler.instance().getClient();

        final WorldClient world = minecraft.theWorld;

        if (world != null)
        {
            if (world.provider instanceof WorldProviderVenus)
            {
                if (world.provider.getSkyRenderer() == null)
                {
                    world.provider.setSkyRenderer(new SkyProviderVenus((IGalacticraftWorldProvider) world.provider));
                }

                if (world.provider.getCloudRenderer() == null)
                {
                    world.provider.setCloudRenderer(new CloudRenderer());
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        final Minecraft minecraft = FMLClientHandler.instance().getClient();
        final EntityPlayerSP player = minecraft.thePlayer;

        if (player == event.player)
        {
            if (!ConfigManagerVenus.disableAmbientLightning)
            {
                Iterator<Map.Entry<BlockPos, Integer>> it = lightning.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry<BlockPos, Integer> entry = it.next();
                    int val = entry.getValue();
                    if (val - 1 <= 0)
                    {
                        it.remove();
                    }
                    else
                    {
                        entry.setValue(val - 1);
                    }
                }

                if (player.getRNG().nextInt(500) == 0 && minecraft.theWorld.provider instanceof WorldProviderVenus)
                {
                    double freq = player.getRNG().nextDouble() * Math.PI * 2.0F;
                    double dist = 180.0F;
                    double dX = dist * Math.cos(freq);
                    double dZ = dist * Math.sin(freq);
                    double posX = player.posX + dX;
                    double posY = 70;
                    double posZ = player.posZ + dZ;
                    minecraft.theWorld.playSound(posX, posY, posZ, "ambient.weather.thunder", 1000.0F, 1.0F + player.getRNG().nextFloat() * 0.2F, false);
                    lightning.put(new BlockPos(posX, posY, posZ), 20);
                }
            }
        }
    }
}

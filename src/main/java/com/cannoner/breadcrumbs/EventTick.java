package com.cannoner.breadcrumbs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class EventTick
{
    @SubscribeEvent
    public void clientEventTick(ClientTickEvent e)
    {
        WorldClient world = null;

        try
        {
            world = Breadcrumbs.getInstance().getMinecraft().theWorld;
        }
        catch (Exception e1)
        {

        }

        if (world != null)
        {
            Breadcrumbs.getInstance().getHandler().update(world);
        }

    }

    @SubscribeEvent
    public void eventRenderLast(RenderWorldLastEvent e)
    {
        EntityPlayerSP player = null;

        try
        {
            player = Minecraft.getMinecraft().thePlayer;
        }
        catch (Exception e2)
        {

        }

        if (player != null)
        {
            Breadcrumbs.getInstance().getHandler().render(player);
        }

    }
}

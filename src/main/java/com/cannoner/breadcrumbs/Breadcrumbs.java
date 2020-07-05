package com.cannoner.breadcrumbs;

import com.cannoner.breadcrumbs.commands.BreadcrumbsCommand;
import com.cannoner.breadcrumbs.utilities.Config;
import com.cannoner.breadcrumbs.utilities.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = Breadcrumbs.MODID, name = Breadcrumbs.MODNAME, version = Breadcrumbs.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "[1.8.9]")
public class Breadcrumbs
{
    private static Breadcrumbs instance;

    public static final String MODID = "bettercrumbs";
    public static final String MODNAME = "Bettercrumbs";
    public static final String VERSION = "1.0.1";

    private Minecraft mc;
    private CrumbHandler handler = new CrumbHandler();
    private Utils utils = new Utils();
    private Config config;

    private boolean tntCrumbs;
    private boolean sandCrumbs;
    private long timeout;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        instance = this;
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register(new EventTick());
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new BreadcrumbsCommand());
    }

    public static Breadcrumbs getInstance() { return instance; }
    public Minecraft getMinecraft() { return mc; }
    public Utils getUtils()
    {
        return utils;
    }
    public Config getConfig()
    {
        return config;
    }
    public CrumbHandler getHandler() { return handler; }

    public boolean getTntCrumbs() { return tntCrumbs; }
    public boolean getSandCrumbs() { return sandCrumbs; }
    public long getTimeout() { return timeout; }

    public void setTntCrumbs(boolean toggle) { tntCrumbs = toggle; }
    public void setSandCrumbs(boolean toggle) { sandCrumbs = toggle; }
    public void setTimeout(long length) { timeout = length; }
}

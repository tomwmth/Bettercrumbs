package com.cannoner.breadcrumbs.utilities;

import com.cannoner.breadcrumbs.Breadcrumbs;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Utils
{
    private static final String MESSAGE_PREFIX = "&7[&bBC&7]&r ";

    public void sendMessage(String text, boolean attachPrefix)
    {
        /*ClientChatReceivedEvent event = new ClientChatReceivedEvent((byte) 1, new ChatComponentText((attachPrefix ? MESSAGE_PREFIX : "") + text));
        MinecraftForge.EVENT_BUS.post(event);*/

        String message = (attachPrefix ? MESSAGE_PREFIX : "") + text;
        Breadcrumbs.getInstance().getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatColour.translateAlternateColorCodes('&', message)));
    }
}

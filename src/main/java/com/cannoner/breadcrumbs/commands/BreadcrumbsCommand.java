package com.cannoner.breadcrumbs.commands;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cannoner.breadcrumbs.Breadcrumbs;
import com.cannoner.breadcrumbs.utilities.Config;
import com.cannoner.breadcrumbs.utilities.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class BreadcrumbsCommand extends CommandBase
{
    @Override
    public String getCommandUsage(ICommandSender sender)
    { return
            "&7&m---------------&7[&b&l Bettercrumbs &7]&7&m---------------\n" +
                    "&b/bc &7- Displays this help message\n" +
                    "&b/bc toggle [sand,tnt] &7- Toggle crumbs.\n" +
                    "&b/bc time <time> &7- Edit how long crumbs last.\n" +
                    "&b/bc config &7- View your current config.\n" +
                    "&7&m-----------------------------------------------";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length > 0)
        {
            switch (args[0].toLowerCase())
            {
                case "toggle":
                    if (args.length > 1)
                    {
                        switch (args[1].toLowerCase())
                        {
                            case "tnt":
                                Breadcrumbs.getInstance().setTntCrumbs(!Breadcrumbs.getInstance().getTntCrumbs());
                                Breadcrumbs.getInstance().getUtils().sendMessage("&fYour TNT crumbs are now " + getBoolString(Breadcrumbs.getInstance().getTntCrumbs()) +"&f.", true);
                                break;
                            case "sand":
                                Breadcrumbs.getInstance().setSandCrumbs(!Breadcrumbs.getInstance().getSandCrumbs());
                                Breadcrumbs.getInstance().getUtils().sendMessage("&fYour sand crumbs are now " + getBoolString(Breadcrumbs.getInstance().getSandCrumbs()) +"&f.", true);
                                break;
                        }
                    }
                    else
                    {
                        Breadcrumbs.getInstance().setTntCrumbs(!Breadcrumbs.getInstance().getTntCrumbs());
                        Breadcrumbs.getInstance().setSandCrumbs(!Breadcrumbs.getInstance().getSandCrumbs());
                        Breadcrumbs.getInstance().getUtils().sendMessage("&fYour TNT crumbs are now " + getBoolString(Breadcrumbs.getInstance().getTntCrumbs()) +"&f.", true);
                        Breadcrumbs.getInstance().getUtils().sendMessage("&fYour sand crumbs are now " + getBoolString(Breadcrumbs.getInstance().getSandCrumbs()) +"&f.", true);
                    }
                    break;
                case "time":
                    if (args.length > 1)
                    {
                        Long time = null;
                        try
                        {
                            time = (long)Integer.parseInt(args[1]) * 1000L;
                        }
                        catch (NumberFormatException e)
                        {
                            Breadcrumbs.getInstance().getUtils().sendMessage("&cPlease provide a time in seconds. (e.g. /bc time 5)", true);
                            return;
                        }
                        if (time < 1000L || time > 300000)
                        {
                            Breadcrumbs.getInstance().getUtils().sendMessage("&cInvalid length of time.", true);
                            return;
                        }
                        Breadcrumbs.getInstance().setTimeout(time);
                        Breadcrumbs.getInstance().getUtils().sendMessage("&fYour crumbs now last for &b" + Breadcrumbs.getInstance().getTimeout()/1000L + " seconds&f.", true);
                    }
                    else
                    {
                        Breadcrumbs.getInstance().getUtils().sendMessage("&cPlease provide a time in seconds. (e.g. /bc time 5)", true);
                        return;
                    }
                    break;
                case "config":
                    Breadcrumbs.getInstance().getUtils().sendMessage(getConfig(), false);
                    break;
                default:
                    Breadcrumbs.getInstance().getUtils().sendMessage("&cInvalid command.", true);
                    break;
            }

        }
        else
        {
            Breadcrumbs.getInstance().getUtils().sendMessage(getCommandUsage(sender), false);
        }
        Breadcrumbs.getInstance().getConfig().save();
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender obj)
    {
        return true;
    }

    @Override
    public List<String> getCommandAliases()
    {
        return Stream.of("bc", "crumbs").collect(Collectors.toList());
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i)
    {
        return false;
    }

    @Override
    public int compareTo(ICommand obj)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "breadcrumbs";
    }

    private String getBoolString(Boolean enabled)
    {
        String text = "";
        if (enabled)
        {
            text = "&aON";
        }
        else if (!enabled)
        {
            text = "&cOFF";
        }
        return text;
    }

    private String getConfig()
    {
        String tntCrumbs = getBoolString(Breadcrumbs.getInstance().getTntCrumbs());
        String sandCrumbs = getBoolString(Breadcrumbs.getInstance().getSandCrumbs());
        Long timeout = Breadcrumbs.getInstance().getTimeout()/1000L;
        return
                        "&7&m------------&7[&b&l Bettercrumbs Config &7]&7&m-----------\n" +
                        "&bTNT crumbs &7- " + tntCrumbs + "\n" +
                        "&bSand crumbs &7- " + sandCrumbs + "\n" +
                        "&bTimeout &7- " + timeout.toString() + " seconds\n" +
                        "&7&m-----------------------------------------------";
    }
}


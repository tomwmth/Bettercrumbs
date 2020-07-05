package com.cannoner.breadcrumbs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.cannoner.breadcrumbs.enums.CrumbType;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class CrumbHandler
{
    public List<Crumb> crumbs = new ArrayList();

    public void update(World world)
    {
        List<Crumb> remove = new ArrayList();

        int i;
        for(i = 0; i < this.crumbs.size(); ++i)
        {
            if (((Crumb)this.crumbs.get(i)).a())
            {
                remove.add(this.crumbs.get(i));
            }
        }

        for(i = 0; i < remove.size(); ++i)
        {
            this.crumbs.remove(remove.get(i));
        }

        HashSet<Entity> renderable = new HashSet();
        List<Entity> entities = world.loadedEntityList;

        for(i = 0; i < entities.size(); ++i)
        {
            Entity c = (Entity)entities.get(i);
            if (c instanceof EntityTNTPrimed || c instanceof EntityFallingBlock)
            {
                renderable.add(c);
            }
        }

        Iterator entityIterator = renderable.iterator();

        while(entityIterator.hasNext()) {
            Entity a = (Entity)entityIterator.next();
            int id = a.getEntityId();
            int index = 0;
            boolean in = false;

            for(i = 0; i < this.crumbs.size(); ++i)
            {
                if (((Crumb)this.crumbs.get(i)).id == id)
                {
                    in = true;
                    index = i;
                    break;
                }
            }

            if (in)
            {
                ((Crumb)this.crumbs.get(index)).addLocation(a.posX, a.posY, a.posZ);
            }
            else
            {
                this.crumbs.add(new Crumb(a instanceof EntityTNTPrimed ? CrumbType.TNT : CrumbType.SAND, id, a.posX, a.posY, a.posZ));
            }
        }

    }

    public void render(EntityPlayerSP player)
    {
        if (Breadcrumbs.getInstance().getSandCrumbs() || Breadcrumbs.getInstance().getTntCrumbs())
        {
            Double posX = player.posX;
            Double posY = player.posY;
            Double posZ = player.posZ;

            for(int i = 0; i < this.crumbs.size(); ++i)
            {
                Crumb crumb = (Crumb)this.crumbs.get(i);
                if (crumb.type.equals(CrumbType.TNT) && Breadcrumbs.getInstance().getTntCrumbs() || crumb.type.equals(CrumbType.SAND) && Breadcrumbs.getInstance().getSandCrumbs())
                {
                    List<CrumbLocation> l = crumb.locations;
                    GL11.glPushMatrix();
                    GL11.glEnable(3042);
                    GL11.glEnable(2848);
                    GL11.glDisable(2929);
                    GL11.glDisable(3553);
                    GL11.glBlendFunc(770, 771);
                    GL11.glEnable(3042);
                    GL11.glLineWidth(6.0F);
                    if (crumb.type == CrumbType.TNT)
                    {
                        GL11.glColor4f(1.0F, 0.3F, 0.3F, 1.0F);
                    }
                    else
                    {
                        GL11.glColor4f(1.0F, 0.6F, 0.0F, 1.0F);
                    }

                    GL11.glBegin(1);

                    for(int j = 0; j < l.size(); ++j)
                    {
                        CrumbLocation b = (CrumbLocation)l.get(j);
                        GL11.glVertex3d(b.x - posX, b.y - posY, b.z - posZ);
                    }

                    GL11.glEnd();
                    GL11.glDisable(3042);
                    GL11.glEnable(3553);
                    GL11.glEnable(2929);
                    GL11.glDisable(2848);
                    GL11.glDisable(3042);
                    GL11.glPopMatrix();
                }
            }
        }

    }
}

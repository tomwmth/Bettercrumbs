package com.cannoner.breadcrumbs.utilities;

import com.cannoner.breadcrumbs.Breadcrumbs;
import com.google.gson.*;
import java.io.*;

public class Config
{
    private File configFile;

    public Config(File configFile) {
        this.configFile = configFile;
        load();
    }

    public void load() {
        if (configFile.exists()) {
            try {
                FileReader fr = new FileReader(configFile);
                BufferedReader br = new BufferedReader(fr);
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                String s = builder.toString();
                JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
                Breadcrumbs.getInstance().setTntCrumbs(asJsonObject.has("tntCrumbs") ? asJsonObject.get("tntCrumbs").getAsBoolean() : false);
                Breadcrumbs.getInstance().setSandCrumbs(asJsonObject.has("sandCrumbs") ? asJsonObject.get("sandCrumbs").getAsBoolean() : false);
                Breadcrumbs.getInstance().setTimeout(asJsonObject.has("timeout") ? asJsonObject.get("timeout").getAsLong() : 5000);
            } catch (Exception e) {

            }
        } else {
            Breadcrumbs.getInstance().setTntCrumbs(false);
            Breadcrumbs.getInstance().setSandCrumbs(false);
            Breadcrumbs.getInstance().setTimeout(5000L);
            save();
        }
        save();
    }

    public void save() {
        try {
            configFile.createNewFile();
            FileWriter fw = new FileWriter(configFile);
            BufferedWriter bw = new BufferedWriter(fw);
            JsonObject object = new JsonObject();
            object.addProperty("tntCrumbs", Breadcrumbs.getInstance().getTntCrumbs());
            object.addProperty("sandCrumbs", Breadcrumbs.getInstance().getSandCrumbs());
            object.addProperty("timeout", Breadcrumbs.getInstance().getTimeout());

            bw.write(object.toString());
            bw.close();
            fw.close();
        } catch (Exception e) {

        }
    }
}
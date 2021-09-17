package me.croabeast.sir.handlers;

import me.croabeast.sir.SIR;
import me.croabeast.sir.interfaces.ActionBar;
import me.croabeast.sir.interfaces.Reflection;
import me.croabeast.sir.utils.LangUtils;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class ActBarOld implements ActionBar, Reflection {

    private final LangUtils langUtils;

    public ActBarOld(SIR main) {
        this.langUtils = main.getLangUtils();
    }

    private void actionBar(Player player, String text) {
        try {
            Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
            Object icbc = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, "{\"text\":\"" + text + "\"}");
            Object packet = constructor.newInstance(icbc, (byte) 2);
            Object entityPlayer = player.getClass().getMethod("getHandle", new Class[0]).invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(Player player, String message) {
        actionBar(player, langUtils.parsePAPI(player, message));
    }
}
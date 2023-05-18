package com.kedior.plugin.tools;

import com.kedior.plugin.annotations.Init;
import com.kedior.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class UsingListener implements Listener {
  static HashMap<Material, ArrayList<Usable>> registeredUsableList = new HashMap<>();

  @Init
  public static void loadAllUsables() {
    Set<Class<? extends Usable>> allUsable = Main.reflections.getSubTypesOf(Usable.class);
    for (Class<? extends Usable> usable : allUsable) {
      try {
        if (Modifier.isAbstract(usable.getModifiers())) continue;
        Constructor<?>[] constructors = usable.getConstructors();
        if (constructors.length == 0) continue;
        Bukkit.getConsoleSender().sendMessage("load usable " + usable.getName());
        Usable u = usable.newInstance();
        UsingListener.registerUsable(u);
      } catch (Exception e) {
        Bukkit.getConsoleSender().sendMessage("failed by" + e.getMessage());
      }
    }
  }

  public static void registerUsable(Usable usable) {
    ArrayList<Usable> usableList = registeredUsableList.computeIfAbsent(usable.getType(), k -> new ArrayList<>());
    usableList.add(usable);
  }

  @EventHandler
  public void listenUse(PlayerInteractEvent event) {
    Action action = event.getAction();
    if (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) {
      Player player = event.getPlayer();
      PlayerInventory inventory = player.getInventory();
      ItemStack mainHand = inventory.getItemInMainHand();
      ItemStack offHand = inventory.getItemInOffHand();
      Usable usable = judgeUsable(mainHand);
      if (usable != null) {
        usable.onUse(event);
        event.setCancelled(true);
      }
      usable = judgeUsable(offHand);
      if (usable != null) {
        usable.onUse(event);
        event.setCancelled(true);
      }
    }
  }

  public Usable judgeUsable(ItemStack itemStack) {
    Material type = itemStack.getType();
    if (registeredUsableList.containsKey(type)) {
      ArrayList<Usable> usableList = registeredUsableList.get(type);
      for (Usable usable : usableList) {
        if (usable.identify(itemStack)) return usable;
      }
    }
    return null;
  }
}

package com.kedior.plugin.tools;

import com.kedior.plugin.annotations.Init;
import com.kedior.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Set;

public class DamageListener implements Listener {
  static HashMap<String, Damager> damageMap = new HashMap<>();

  @Init
  public static void loadAllDamagers() {
    Set<Class<? extends Damager>> allDamager = Main.reflections.getSubTypesOf(Damager.class);
    for (Class<? extends Damager> damager : allDamager) {
      try {
        if (Modifier.isAbstract(damager.getModifiers())) continue;
        Constructor<?>[] constructors = damager.getConstructors();
        if (constructors.length == 0) continue;
        Bukkit.getConsoleSender().sendMessage("load damager " + damager.getName());
        Damager d = damager.newInstance();
        DamageListener.registerDamage(d);
      } catch (Exception e) {
        Bukkit.getConsoleSender().sendMessage("failed by" + e.getMessage());
      }
    }
  }
  public static void registerDamage(Damager damage) {
    damageMap.put(damage.getName(), damage);
  }

  @EventHandler
  public void damage(EntityDamageByEntityEvent event) {
    Entity damagerEnity = event.getDamager();
    if (damageMap.containsKey(damagerEnity.getCustomName())) {
      Damager damager = damageMap.get(damagerEnity.getCustomName());
      float damage = damager.getDamage();
      event.setDamage(damage);
      damager.onDamage(event);
    }
  }
}

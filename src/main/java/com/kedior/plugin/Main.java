package com.kedior.plugin;

import com.kedior.plugin.annotations.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Method;
import java.util.Set;

public final class Main extends JavaPlugin {
  public static Main instance;
  public static Reflections reflections;

  @Override
  public void onEnable() {
    if (instance == null) instance = this;
    reflections = new Reflections("com.kedior.plugin", Scanners.values());
    Set<Method> initMethods = reflections.getMethodsAnnotatedWith(Init.class);
    for (Method initMethod : initMethods) {
      Bukkit.getConsoleSender().sendMessage("load initMethod " + initMethod.getName());
      try {
        initMethod.invoke(null);
      } catch (Exception e) {
        Bukkit.getConsoleSender().sendMessage("failed by" + e.getMessage());
      }
    }
  }

  @Override
  public void onDisable() {
    Bukkit.resetRecipes();
    instance = null;
  }
}

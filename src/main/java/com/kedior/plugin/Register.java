package com.kedior.plugin;

import com.kedior.plugin.annotations.Init;
import com.kedior.plugin.annotations.NewRecipe;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Method;
import java.util.Set;

public class Register {
  @Init
  public static void registerAllListener() {
    Set<Class<? extends Listener>> listeners = Main.reflections.getSubTypesOf(Listener.class);
    PluginManager pluginManager = Bukkit.getPluginManager();
    for (Class<? extends Listener> listener : listeners) {
      Bukkit.getConsoleSender().sendMessage("load listener " + listener.getName());
      try {
        pluginManager.registerEvents(listener.newInstance(), Main.instance);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Init
  public static void registerAllRecipes() {
    Set<Method> initMethods = Main.reflections.getMethodsAnnotatedWith(NewRecipe.class);
    for (Method initMethod : initMethods) {
      Bukkit.getConsoleSender().sendMessage("load recipe " + initMethod.getName());
      try {
        Recipe recipe = (Recipe)initMethod.invoke(null);
        Bukkit.addRecipe(recipe);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}

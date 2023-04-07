package com.cobblemonmasters.zennyel.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockBreak implements Listener {

    private FileConfiguration configuration;
    private Random random;
    List<String> materials;
    List<ItemStack> itemStacks;

    public BlockBreak(FileConfiguration configuration) {
        this.configuration = configuration;
        this.random = new Random();
        itemStacks = new ArrayList<>();
         materials = configuration.getStringList("Drops");
         if(materials.size() == 0){
             Bukkit.getConsoleSender().sendMessage("Drops null!");
         }
        for(String material : materials){
            Material m = Material.matchMaterial(material.toUpperCase());
            itemStacks.add(new ItemStack(m));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();


        if (event.getBlock().getType().equals(Material.STONE)) {
            List<ItemStack> drops = new ArrayList<>();
            List<String> dropsConfig = configuration.getStringList("Drops");

            for (String drop : dropsConfig) {
                Material material = Material.getMaterial(drop.toUpperCase().replace(" ", "_"));
                if (material != null) {
                    ItemStack itemStack = new ItemStack(material, 1);
                    drops.add(itemStack);
                }
            }

            if (!drops.isEmpty()) {
                double chance = configuration.getDouble("Chance", 0.5);

                if (random.nextDouble() < chance) {
                    int index = random.nextInt(drops.size());
                    ItemStack drop = drops.get(index);
                    player.getInventory().addItem(drop);
                    event.setDropItems(false);
                } else {
                    ItemStack stone = new ItemStack(Material.getMaterial(configuration.getString("Default")));
                    event.setDropItems(false);
                    player.getInventory().addItem(stone);
                }
            }
        }
    }



}

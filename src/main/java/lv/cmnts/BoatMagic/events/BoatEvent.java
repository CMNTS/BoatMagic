package lv.cmnts.BoatMagic.events;

import lv.cmnts.BoatMagic.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class BoatEvent implements Listener {
    public Main plugin;

    public BoatEvent(Main plugin) {
        this.plugin = plugin;
    }

    public HashMap<UUID, TreeSpecies> tips = new HashMap<>();

    @EventHandler
    public void onBoatExit(VehicleExitEvent e) {
        World w = e.getExited().getWorld();
        Location l = e.getExited().getLocation();

        if (e.getExited() instanceof Player) {
            if (e.getVehicle().getType() == EntityType.BOAT) {
                ItemStack laiva = new ItemStack(plugin.getBoatType(tips.get(e.getExited().getUniqueId())), 1);

                if (e.getVehicle().getPassengers().size() <= 1) {
                    e.getVehicle().remove();
                    w.dropItemNaturally(l, laiva);
                    tips.remove(e.getExited().getUniqueId());
                } else tips.remove(e.getExited().getUniqueId());
            }
        }
    }

    @EventHandler
    public void onBoatEnter(VehicleEnterEvent e) {
        if (e.getEntered() instanceof Player) {
            if (e.getVehicle().getType() == EntityType.BOAT) {
                Boat b = (Boat) e.getVehicle();
                tips.put(e.getEntered().getUniqueId(), b.getWoodType());
            }
        }
    }
}

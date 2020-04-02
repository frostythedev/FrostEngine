package me.frostythedev.developement.npc.goals;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;

public class PathfinderGoalWalkToLoc extends PathfinderGoal {

    private double speed;

    private EntityInsentient entity;

    private Location loc;

    private NavigationAbstract navigation;

    public PathfinderGoalWalkToLoc(EntityInsentient entity, Location loc, double speed) {
        this.entity = entity;
        this.loc = loc;
        this.navigation = this.entity.getNavigation();
        this.speed = speed;
    }

    public void c() {
        PathEntity pathEntity = this.navigation.a(loc.getX(), loc.getY(), loc.getZ());
        this.navigation.a(pathEntity, speed);
    }

    @Override
    public boolean a() {
        return true;
    }
}

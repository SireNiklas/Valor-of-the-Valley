package me.sirniklas.valorofthevalley.Game.Commands;

import me.sirniklas.valorofthevalley.Economy.ItemCreator;
import me.sirniklas.valorofthevalley.Economy.VotvEconomy;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.ArenaCommands.JoinCommand;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.ArenaCommands.LeaveCommand;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.ArenaCommands.ShopCommand;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommands.AdminCommands.EconomyCommands.*;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.EconomyCommands.BalanceCommand;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.EconomyCommands.PayCommand;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommands.AdminCommands.UtilityCommands.ReloadCommand;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommands.AdminCommands.UtilityCommands.VersionCommand;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandExecutor, TabExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    VotvEconomy votvEconomy;
    ItemCreator itemCreator;
    Permission permission;

    public CommandManager(VotvEconomy votvEconomy, ItemCreator itemCreator, Permission permission) {

        this.votvEconomy = votvEconomy;
        this.itemCreator = itemCreator;
        this.permission = permission;

        // Utility
        subcommands.add(new VersionCommand());
        subcommands.add(new ReloadCommand());

        // Arena General Use
        subcommands.add(new JoinCommand());
        subcommands.add(new LeaveCommand());
        subcommands.add(new ShopCommand(itemCreator));

        // Economy
            // Admin
        subcommands.add(new SetCommand(votvEconomy));
        subcommands.add(new ClearCommand(votvEconomy));
        subcommands.add(new GiveCommand(votvEconomy));
        subcommands.add(new TakeCommand(votvEconomy));
            // Player
        subcommands.add(new PayCommand(votvEconomy));
        subcommands.add(new BalanceCommand(votvEconomy));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(p, args);
                    }
                }
            }else if(args.length == 0){
                p.sendMessage("--------------------------------");
                for (int i = 0; i < getSubcommands().size(); i++){
                    p.sendMessage(getSubcommands().get(i).getSyntax() + " - " + getSubcommands().get(i).getDescription());
                }
                p.sendMessage("--------------------------------");
            }

        }


        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("votv.admin")) {
            return Arrays.asList("version", "join", "leave", "shop", "set");
        } else {
            return Arrays.asList("join", "leave", "balance", "pay");
        }
    }

}

package tntrun.eventhandler;

import net.serble.serblenetworkplugin.API.Schemas.WarpEvent;
import net.serble.serblenetworkplugin.API.Schemas.WarpEventListener;
import tntrun.TNTRun;
import tntrun.arena.Arena;
import tntrun.messages.Messages;

public class SerblePartyWarpHandler implements WarpEventListener {

    @Override
    public boolean onWarpEvent(WarpEvent warpEvent) {
        Arena arena = TNTRun.getInstance().amanager.getPlayerArena(warpEvent.getPartyLeader().getName());
        if (arena == null) {
            return false;
        }

        if (arena.getStatusManager().isArenaRunning()) {
            Messages.sendMessage(warpEvent.getTarget(), Messages.arenarunning);
            return true;  // Don't let Serble handle it
        }

        if (arena.getPlayersManager().getPlayersCount() == arena.getStructureManager().getMaxPlayers()) {
            Messages.sendMessage(warpEvent.getTarget(), Messages.limitreached);
            return true;
        }

        arena.getPlayerHandler().spawnPlayer(warpEvent.getTarget(), Messages.playerjoinedtoothers);
        return true;
    }

}

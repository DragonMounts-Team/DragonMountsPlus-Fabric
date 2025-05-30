package net.dragonmounts.plus.common.api;

import net.dragonmounts.plus.common.component.ScoreboardInfo;
import net.minecraft.world.scores.ScoreHolder;

import java.util.List;

public interface ScoreboardAccessor {
    ScoreboardInfo dragonmounts$getInfo(ScoreHolder holder);

    void dragonmounts$preventRemoval(ScoreHolder holder);

    void dragonmounts$addPlayerToTeam(String name, String team);

    void dragonmounts$loadEntries(ScoreHolder holder, List<ScoreboardInfo.Entry> entries);
}

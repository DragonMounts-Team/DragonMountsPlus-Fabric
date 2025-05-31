package net.dragonmounts.plus.common.api;

import net.dragonmounts.plus.common.component.ScoreboardInfo;
import net.minecraft.world.scores.ScoreHolder;

import java.util.List;

public interface ScoreboardAccessor {
    ScoreboardInfo dragonmounts$plus$getInfo(ScoreHolder holder);

    void dragonmounts$plus$preventRemoval(ScoreHolder holder);

    void dragonmounts$plus$addPlayerToTeam(String name, String team);

    void dragonmounts$plus$loadEntries(ScoreHolder holder, List<ScoreboardInfo.Entry> entries);
}

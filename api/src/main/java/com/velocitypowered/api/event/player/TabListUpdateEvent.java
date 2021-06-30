/*
 * Copyright (C) 2018 Velocity Contributors
 *
 * The Velocity API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the api top-level directory.
 */

package com.velocitypowered.api.event.player;

import com.google.common.base.Preconditions;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.player.TabList;

import java.util.UUID;

/**
 * This event is called when a tablist entry
 * update is received from the backend server.
 */
public class TabListUpdateEvent implements
    ResultedEvent<ResultedEvent.GenericResult> {

  private final UUID entry;
  private final Player player;
  private final TabListAction action;
  private GenericResult result;

  /**
   * Creates a new instance.
   * @param entry the player which was modified
   * @param player the player, which tablist was modified
   * @param action the action which was used on the entry
   */
  public TabListUpdateEvent(UUID entry, Player player, TabListAction action) {
    this.entry = Preconditions.checkNotNull(entry, "entry");
    this.player = Preconditions.checkNotNull(player, "player");
    this.action = Preconditions.checkNotNull(action, "action");
    this.result = GenericResult.allowed();
  }

  public UUID getEntry() {
    return entry;
  }

  public Player getPlayer() {
    return player;
  }

  public TabListAction getAction() {
    return action;
  }

  public TabList getTabList() {
    return getPlayer().getTabList();
  }

  @Override
  public GenericResult getResult() {
    return result;
  }

  @Override
  public void setResult(GenericResult result) {
    this.result = result;
  }

  public enum TabListAction {

    ADD_PLAYER, UPDATE_GAMEMODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER;

    private static final TabListAction[] VALUES = values();

    public static TabListAction byId(int id) {
      return VALUES[id];
    }
  }
}